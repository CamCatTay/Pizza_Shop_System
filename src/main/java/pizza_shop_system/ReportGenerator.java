package pizza_shop_system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReportGenerator {

    private LocalDate date;
    private String customerName;
    private double amount;
    private int orderNumber;

    public static List<String> readOrders(int orderID) throws IOException{

        List<String> orders = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("data_files/orders.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");
                int checkOrderID = Integer.parseInt(fields[0].trim());

                if (checkOrderID == orderID) {
                    String customerName = fields[1].trim();
                    int customerID = Integer.parseInt(fields[2]);
                    LocalDate date = LocalDate.parse(fields[3].trim());
                    String orderType = fields[4].trim();
                    String status = fields[5].trim();
                    String paymentMethod = fields[6].trim();
                    List<String> orderedItems = readOrderItems(orderID);
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

    public static List<String> readOrderItems(int orderID) throws IOException{

        List<String> items = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("data_files/order_items.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (Integer.parseInt(fields[0]) == orderID) {
                    items.addAll(Arrays.asList(fields).subList(1, fields.length));
                }
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

    //METHOD TO READ users.txt and store account info
    //public static Account readAccounts(String customerName, int customerID) throws IOException{

    //}

    public static void generateDailyReport(List<Order> Orders, LocalDate specifiedDate){


        //CHANGE AS NEEDED FOR DATABASE FILE
        //for(Order order: orders){
        //    if(Objects.equals(order.getDatePlaced(), specifiedDate)) {
        //        System.out.println(order.getDatePlaced());
        //        System.out.println("______________________");
        //        System.out.println(order.getAccount().name + " " + order.getOrderID());
        //        System.out.println(order.getOrderedItems());
        //
        //        //SUBTOTAL, TAX, TIP, & TOTAL
        //        List<MenuItem> items = order.getOrderedItems();
        //        double subtotal = 0.0;
        //        for(MenuItem item: items){
        //            subtotal += item.getPrice();
        //        }
        //        System.out.println("Subtotal: " + String.format("%.2d", subtotal));
        //        System.out.println("Tax: " + String.format("%.2d", order.calcTax(order, 0.07)));
        //        System.out.println("Tip: " + String.format("%.2d", order.calcTip(order, 0.15)));
        //
        //        double total = subtotal + order.calcTax(order, 0.07) + order.calcTip(order, 0.15);
        //        System.out.println("Total: " + String.format("%.2d", total));
        //
        //        System.out.println(order.getStatus());
        //        String orderType = order.getOrderType();
        //        if (orderType.equalsIgnoreCase("DELIVERY")) {
        //            System.out.println(order.getAccount().getAddress());
        //        }
        //    }
        //}

    }

    //Orders will have orderID, userID, orderType, status, deliveryAddress, driverID

    public static void main(String[] args){







    }

}
