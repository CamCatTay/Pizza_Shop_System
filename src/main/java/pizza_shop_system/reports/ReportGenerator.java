package pizza_shop_system.reports;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    public static List<JSONObject> readOrders(LocalDate startDate, LocalDate endDate) throws IOException {
        List<JSONObject> matchingOrders = new ArrayList<>();
        String content = Files.readString(Paths.get("data_files/orders.json"));
        JSONObject root = new JSONObject(content);
        JSONArray ordersArray = root.getJSONArray("orders");

        for (int i = 0; i < ordersArray.length(); i++) {
            JSONObject order = ordersArray.getJSONObject(i);

            // Skip if no date present
            if (!order.has("date")) {
                System.err.println("Skipping order without date: " + order.optInt("orderId", -1));
                continue;
            }

            String dateStr = order.getString("date");
            LocalDate orderDate = ZonedDateTime.parse(dateStr).toLocalDate();

            if (!orderDate.isBefore(startDate) && !orderDate.isAfter(endDate)) {
                matchingOrders.add(order);
            }
        }

        return matchingOrders;
    }


    public static String generateDailyReport(LocalDate specifiedDate) throws IOException {
        StringBuilder report = new StringBuilder();
        List<JSONObject> orders = readOrders(specifiedDate, specifiedDate);

        if (orders.isEmpty()) {
            return "There are no orders for this date!";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        report.append(specifiedDate.format(formatter)).append("\n");
        report.append("__________________________\n");

        for (JSONObject order : orders) {
            int orderId = order.getInt("orderId");
            double subtotal = order.getDouble("subtotal");
            double tax = order.getDouble("tax");
            double total = order.getDouble("total");
            JSONObject orderInfo = order.getJSONObject("orderInformation");
            JSONObject paymentInfo = order.getJSONObject("paymentInformation");

            report.append("Order #").append(orderId).append("\n");

            JSONArray items = order.getJSONArray("orderItems");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                report.append(item.getString("name"))
                        .append("\t$").append(String.format("%.2f", item.getDouble("price"))).append("\n");
            }

            report.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n");
            report.append("Tax: $").append(String.format("%.2f", tax)).append("\n");
            report.append("Total: $").append(String.format("%.2f", total)).append("\n");

            report.append("Payment Method: ").append(paymentInfo.getString("paymentMethod")).append("\n");
            report.append("Order Type: ").append(orderInfo.getString("orderType")).append("\n");
            report.append("__________________________________\n");
        }

        return report.toString();
    }

    public static String generateWeeklyReport(LocalDate startDate, LocalDate endDate) throws IOException {
        StringBuilder report = new StringBuilder();
        List<JSONObject> orders = readOrders(startDate, endDate);

        if (orders.isEmpty()) {
            return "There are no orders from "
                    + startDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
                    + " to "
                    + endDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
                    + "!";
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        report.append("Weekly Report\n")
                .append("From: ").append(startDate.format(fmt))
                .append(" To: ").append(endDate.format(fmt))
                .append("\n__________________________\n");

        double totalSales = 0;
        int totalOrders = 0;

        for (JSONObject order : orders) {
            int orderId = order.getInt("orderId");
            double subtotal = order.optDouble("subtotal", 0.0);
            double tax      = order.optDouble("tax",      0.0);
            double total    = order.optDouble("total",    subtotal + tax);

            // Some orders may not have these; use safe defaults
            JSONObject info = order.optJSONObject("orderInformation");
            JSONObject pay  = order.optJSONObject("paymentInformation");

            report.append("Order #").append(orderId).append("\n");

            JSONArray items = order.getJSONArray("orderItems");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String name = item.getString("name");
                double price = item.optDouble("price", 0.0);
                report.append(name)
                        .append("\t$").append(String.format("%.2f", price))
                        .append("\n");
            }

            report.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n")
                    .append("Tax: $").append(String.format("%.2f", tax)).append("\n")
                    .append("Total: $").append(String.format("%.2f", total)).append("\n");

            if (pay != null) {
                report.append("Payment Method: ").append(pay.optString("paymentMethod", "N/A")).append("\n");
            }
            if (info != null) {
                report.append("Order Type: ").append(info.optString("orderType", "N/A")).append("\n");
            }
            report.append("__________________________________\n");

            totalSales += total;
            totalOrders++;
        }

        report.append("\nTotal Orders: ").append(totalOrders).append("\n")
                .append("Total Weekly Sales: $").append(String.format("%.2f", totalSales)).append("\n");

        return report.toString();
    }


}
