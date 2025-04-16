package pizza_shop_system.reports;

import pizza_shop_system.order.*;
import pizza_shop_system.account.*;
import pizza_shop_system.menu.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class ReportGenerator {

    private LocalDate date;
    private String customerName;
    private double amount;
    private int orderNumber;

    public static List<Order> readOrders(LocalDate startDate, LocalDate endDate) throws IOException {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data_files/orders.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");
                LocalDate checkDate = LocalDate.parse(fields[3].trim());

                // Check if the order date is within the range
                if (!checkDate.isBefore(startDate) && !checkDate.isAfter(endDate)) {
                    int orderID = Integer.parseInt(fields[0].trim());
                    String customerName = fields[1].trim();
                    int customerID = Integer.parseInt(fields[2]);
                    LocalDate date = LocalDate.parse(fields[3].trim());
                    String orderType = fields[4].trim();
                    OrderStatus status = OrderStatus.valueOf(fields[5].trim());
                    String paymentMethod = fields[6].trim();
                    ArrayList<MenuItem> orderedItems = readOrderItems(orderID);

                    User account = new User(customerName, customerID);
                    Order order = new Order(paymentMethod, orderID, orderedItems, orderType, status, account);
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            System.err.println("IOException: Unable to read file " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }

    //May be issue reading multiple types
    public static ArrayList<MenuItem> readOrderItems(int orderID) throws IOException{

        ArrayList<MenuItem> items = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("data_files/order_items.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                String itemID = fields[0].trim();
                String category = fields[1].trim();
                Double price = Double.parseDouble(fields[2].trim());
                String name = fields[3].trim();
                String description = fields[4].trim();

                MenuItem menuItem = new MenuItem(itemID, category, price, name, description);
                items.add(menuItem);

                // OLD CODE
                //String name = fields[1].trim();
                //double price = Double.parseDouble(fields[2]);
                //int amount = Integer.parseInt(fields[3]);
                //String category = fields[4].trim();
                //ArrayList<String> toppings = new ArrayList<>();
                //for(int i = 4; i < fields.length; i++){
                //    toppings.add(fields[i]);
                //}
                //MenuItem menuItem = new MenuItem(name, price, amount, category, toppings);
                //items.add(menuItem);
            }
        } catch (IOException e) {
            System.err.println("IOException: Unable to read file " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        //Check if list is populated if not return empty list
        return items.isEmpty() ? new ArrayList<>() : items;
    }

    public static User readAccounts(String customerName, int customerID) throws IOException{

        User user = null;

        try(BufferedReader reader = new BufferedReader(new FileReader("data_files/users.txt"))){
            String line;

            while ((line = reader.readLine()) != null){
                if(line.isEmpty()){
                    continue;
                }

                String[] fields = line.split(",");
                int userID = Integer.parseInt(fields[0]);
                String email = fields[1].trim();
                String password = fields[2].trim();
                String accountType = fields[3].trim();
                String name = fields[4].trim();
                String address = fields[5].trim();
                String phoneNumber = fields[6].trim();

                if(userID == customerID && name.equalsIgnoreCase(customerName)){
                    user = new User(userID, name, email, address, phoneNumber, password, accountType);
                    break;
                }
            }

        } catch (IOException e){
            System.err.println("IOException: Unable to read file " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return user;

    }

    public static List<String> countItems(List<MenuItem> order){

        List<String> uniqueItems = new ArrayList<>();

        for(MenuItem item: order){
            String name = item.getName();
            uniqueItems.add(name);
        }

        List<String> result = new ArrayList<>();
        result.addAll(uniqueItems);

        return result;

    }

    public static String generateDailyReport(LocalDate specifiedDate) throws IOException {
        StringBuilder report = new StringBuilder();

        try {
            List<Order> orders = readOrders(specifiedDate, specifiedDate);

            if (orders.isEmpty()) {
                report.append("There are no orders for this date!");
                return report.toString();
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

            report.append(specifiedDate.format(formatter)).append("\n");
            report.append("__________________________\n");

            for (Order order : orders) {
                report.append(order.getAccount().getName())
                        .append(" Order #").append(order.getOrderID()).append("\n");
                List<MenuItem> orderItems = readOrderItems(order.getOrderID());
                double subtotal = 0.0;

                for (MenuItem item : orderItems) {
                    report.append(item.getName()).append("\t\t\t$").append(String.format("%.2f", item.getPrice())).append("\n");

                    subtotal += item.getPrice();
                }

                double tax = order.calcTax(0.08);
                double tip = order.calcTip(0.1);
                double total = subtotal + tip + tax;

                report.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n");
                report.append("Tax: $").append(String.format("%.2f", tax)).append("\n");
                report.append("Tip: $").append(String.format("%.2f", tip)).append("\n");
                report.append("___________\n");
                report.append("Total: $").append(String.format("%.2f", total)).append("\n");

                report.append("Order Status: ").append(order.getStatus()).append("\n");
                String orderType = order.getOrderType();
                if (orderType.equalsIgnoreCase("DELIVERY")) {
                    report.append("Pickup Option: DELIVERY\n");
                    if (order.getAccount().getAddress() != null) {
                        report.append("Address: ").append(order.getAccount().getAddress()).append("\n");
                    } else {
                        report.append("No address found!\n");
                    }
                } else {
                    report.append("Pickup Option: IN STORE\n");
                }

                report.append("__________________________________\n");
            }

        } catch (Exception e) {
            report.append("Error: ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }

        return report.toString();
    }


    public static String generateWeeklyReport(LocalDate startDate, LocalDate endDate) throws IOException {
        StringBuilder report = new StringBuilder();
        List<Order> orders = new ArrayList<>();
        for (Order order : readOrders(startDate, endDate)) {
            orders.add(order);
        }

        try {
            if (orders == null || orders.isEmpty()) {
                report.append("There are no orders for this date range!");
                return report.toString();
            }

            LocalDate currentDate = startDate;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMMM dd, yyyy");

            while (!currentDate.isAfter(endDate)) {
                String formattedDate = currentDate.format(formatter);
                report.append(formattedDate).append("\n");
                report.append("—----------------------\n");

                double subtotal = 0.0;
                List<String> itemNames = new ArrayList<>();
                List<Integer> itemQuantities = new ArrayList<>();
                List<Double> itemPrices = new ArrayList<>();

                for (Order order : orders) {
                    LocalDate orderDate = LocalDate.from(order.getDatePlaced());
                    if (orderDate.equals(currentDate)) {
                        List<MenuItem> orderItems = readOrderItems(order.getOrderID());

                        for (MenuItem item : orderItems) {
                            String itemName = item.getName();
                            double price = item.getPrice();
                            int index = itemNames.indexOf(itemName);

                            if (index >= 0) {
                                itemQuantities.set(index, itemQuantities.get(index));
                            } else {
                                itemNames.add(itemName);
                                itemPrices.add(price);
                            }

                            subtotal += price;
                        }
                    }
                }

                boolean hasItems = false;
                for (int i = 0; i < itemNames.size(); i++) {
                    String itemName = itemNames.get(i);
                    int quantity = itemQuantities.get(i);
                    double price = itemPrices.get(i);
                    double totalItemPrice = price * quantity;
                    report.append(String.format("%dx %s $%.2f\n", quantity, itemName, totalItemPrice));
                    hasItems = true;
                }

                if (!hasItems) {
                    report.append("No orders for this day.\n");
                }

                double tax = subtotal * 0.08;  // assuming 8% tax rate
                double total = subtotal + tax;

                report.append("—-------\n");
                report.append(String.format("Subtotal: $%.2f\n", subtotal));
                report.append(String.format("Tax: $%.2f\n", tax));
                report.append("—-------\n");
                report.append(String.format("Total: $%.2f\n", total));

                currentDate = currentDate.plusDays(1);  // Move to the next day
            }
        } catch (IOException e) {
            report.append("IOException: Unable to read file ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        } catch (Exception e) {
            report.append("Error: ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }

        return report.toString();
    }


    //Orders will have orderID, userID, orderType, status, deliveryAddress, driverID

    public static void main(String[] args){


    }
}

