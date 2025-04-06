package pizza_shop_system;

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

    public static List<Order> readOrders(LocalDate selectedDate) throws IOException{

        List<Order> orders = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("data_files/orders.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");
                LocalDate checkDate = LocalDate.parse(fields[3].trim());

                if (checkDate == selectedDate) {
                    int orderID = Integer.parseInt(fields[0].trim());
                    String customerName = fields[1].trim();
                    int customerID = Integer.parseInt(fields[2]);
                    LocalDate date = LocalDate.parse(fields[3].trim());
                    String orderType = fields[4].trim();
                    String status = fields[5].trim();
                    String paymentMethod = fields[6].trim();
                    List<MenuItem> orderedItems = readOrderItems(orderID);
                    System.out.println(orders);
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

    public static List<MenuItem> readOrderItems(int orderID) throws IOException{

        List<MenuItem> items = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("data_files/order_items.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String name = fields[1].trim();
                double price = Double.parseDouble(fields[2]);
                String category = fields[3].trim();
                ArrayList<String> toppings = new ArrayList<>();
                for(int i = 4; i < fields.length; i++){
                    toppings.add(fields[i]);
                }

                MenuItem menuItem = new MenuItem(name, price, category, toppings);
                items.add(menuItem);
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
                    user = new User(name, userID, email, address, phoneNumber, password, accountType);
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

    public static void generateDailyReport(LocalDate specifiedDate) throws IOException {

        try {

            List<Order> orders = readOrders(specifiedDate);

            if (orders.isEmpty()) {
                System.out.println("There are no orders for this date!");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM d, yyyy");

            System.out.println(specifiedDate.format(formatter));
            System.out.println("__________________________");

            for (Order order : orders) {
                System.out.println(order.getAccount().getName() + " Order #" + order.getOrderID());
                List<MenuItem> orderItems = readOrderItems(order.getOrderID());
                double subtotal = 0.0;

                for (MenuItem item : orderItems) {
                    System.out.println(item.getName() + "\t\t\t$" + String.format("%.2f", item.getPrice()));

                    if (item.toppings != null && !item.toppings.isEmpty()) {
                        for (String topping : item.toppings) {
                            System.out.println("\t~" + topping);
                        }
                    }
                    subtotal += item.getPrice();

                }

                double tax = order.calcTax(order, 0.08);
                double tip = order.calcTip(order, 0.1);
                double total = subtotal + tip + tax;

                System.out.println("Subtotal: $" + String.format("%.2f", subtotal));
                System.out.println("Tax: $" + String.format("%.2f", tax));
                System.out.println("Tip: $" + String.format("%.2f", tip));
                System.out.println("___________");
                System.out.println("Total: $" + String.format("%.2f", total));

                System.out.println("Order Status: " + order.getStatus());
                String orderType = order.getOrderType();
                if (orderType.equalsIgnoreCase("DELIVERY")) {
                    System.out.println("Pickup Option: DELIVERY");
                    if (order.getAccount().getAddress() != null) {
                        System.out.println("Address: " + order.getAccount().getAddress());
                    } else {
                        System.out.println("No address found!");
                    }
                } else {
                    System.out.println("Pickup Option: IN STORE");
                }

                System.out.println("__________________________________");

            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void generateWeeklyReport(List<Order> orders, LocalDate startDate, LocalDate endDate){

        if (orders == null || orders.isEmpty()){
            System.err.println("There are no orders for this date!");
        }

        LocalDate currentDate = startDate;

        while(!currentDate.isAfter(endDate)){
            System.out.println("Generating weekly report from" + startDate + " to " + endDate);

            for (Order order : orders){
                LocalDate orderDate = order.getDatePlaced();


            }
        }



    }

    //Orders will have orderID, userID, orderType, status, deliveryAddress, driverID

    public static void main(String[] args){


    }

}
