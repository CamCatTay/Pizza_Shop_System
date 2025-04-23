package pizza_shop_system.reports;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    // Helper method to get user's name by accountId
    private static String getUserNameByAccountId(int accountId) throws IOException {
        String accountsContent = Files.readString(Paths.get("data_files/users.json"));
        JSONObject accountsRoot = new JSONObject(accountsContent);
        JSONArray usersArray = accountsRoot.getJSONArray("users");

        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject user = usersArray.getJSONObject(i);
            if (user.getInt("user_id") == accountId) {
                return user.getString("name");
            }
        }

        return "Unknown User"; // In case no matching account is found
    }

    // Read orders within a date range
    public static List<JSONObject> readOrders(LocalDate startDate, LocalDate endDate) throws IOException {
        List<JSONObject> matchingOrders = new ArrayList<>();
        String content = Files.readString(Paths.get("data_files/orders.json"));
        JSONObject root = new JSONObject(content);
        JSONArray ordersArray = root.getJSONArray("orders");

        for (int i = 0; i < ordersArray.length(); i++) {
            JSONObject order = ordersArray.getJSONObject(i);

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

    // Generate daily report
    public static String generateDailyReport(LocalDate specifiedDate) throws IOException {
        List<JSONObject> orders = readOrders(specifiedDate, specifiedDate);

        if (orders.isEmpty()) {
            return "There are no orders for this date!";
        }

        double totalSales = 0;
        double totalTax = 0;
        double totalSubtotal = 0;
        int totalOrders = 0;

        StringBuilder report = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        report.append("Daily Report for ").append(specifiedDate.format(formatter)).append("\n")
                .append("Total Orders: ").append(totalOrders).append("\n")
                .append("Subtotal: $").append(String.format("%.2f", totalSubtotal)).append("\n")
                .append("Tax: $").append(String.format("%.2f", totalTax)).append("\n")
                .append("Total Sales: $").append(String.format("%.2f", totalSales)).append("\n")
                .append("__________________________\n");

        for (JSONObject order : orders) {
            int orderId = order.getInt("orderId");
            double subtotal = order.getDouble("subtotal");
            double tax = order.getDouble("tax");
            double total = order.getDouble("total");
            JSONObject orderInfo = order.getJSONObject("orderInformation");
            JSONObject paymentInfo = order.getJSONObject("paymentInformation");

            int accountId = order.getInt("accountId");
            String userName = getUserNameByAccountId(accountId);

            report.append(userName).append(" Order #").append(orderId).append("\n");

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

    // Generate weekly report
    public static String generateWeeklyReport(LocalDate startDate) throws IOException {
        LocalDate endDate = startDate.plusDays(6); // Get the end date of the week (7 days from start)

        List<JSONObject> orders = readOrders(startDate, endDate);

        if (orders.isEmpty()) {
            return "There are no orders for this week!";
        }

        double totalSales = 0;
        double totalTax = 0;
        double totalSubtotal = 0;
        int totalOrders = 0;

        StringBuilder report = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        report.append("Weekly Report for ").append(startDate.format(formatter))
                .append(" to ").append(endDate.format(formatter)).append("\n")
                .append("Total Orders: ").append(totalOrders).append("\n")
                .append("Subtotal: $").append(String.format("%.2f", totalSubtotal)).append("\n")
                .append("Tax: $").append(String.format("%.2f", totalTax)).append("\n")
                .append("Total Sales: $").append(String.format("%.2f", totalSales)).append("\n")
                .append("__________________________\n");

        for (JSONObject order : orders) {
            int orderId = order.getInt("orderId");
            double subtotal = order.getDouble("subtotal");
            double tax = order.getDouble("tax");
            double total = order.getDouble("total");
            JSONObject orderInfo = order.getJSONObject("orderInformation");
            JSONObject paymentInfo = order.getJSONObject("paymentInformation");

            int accountId = order.getInt("accountId");
            String userName = getUserNameByAccountId(accountId);

            report.append(userName).append(" Order #").append(orderId).append("\n");

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

}
