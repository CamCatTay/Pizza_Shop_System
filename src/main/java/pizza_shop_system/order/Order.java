package pizza_shop_system.order;

import pizza_shop_system.account.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private String paymentMethod;
    private int orderID;
    private static int nextID = 0;
    private ArrayList<MenuItem> orderedItems;
    private String orderType;
    private OrderStatus status;
    private LocalDate datePlaced;
    private User account;


    //DEFAULT CONSTRUCTOR
    public Order(String paymentMethod){
        this.paymentMethod = paymentMethod;
        this.orderID = nextID++;
        this.orderedItems = new ArrayList<>();
        this.orderType = "DEFAULT";
        this.status = OrderStatus.INCOMPLETE;
        this.datePlaced = LocalDate.now();
        this.account = null;
    }

    public Order(String paymentMethod, int orderID, ArrayList<MenuItem> orderedItems, String orderType, OrderStatus status, User account){
        this.paymentMethod = paymentMethod;
        this.orderID = orderID;
        this.orderedItems = orderedItems;
        this.orderType = orderType;
        this.status = status;
        this.datePlaced = LocalDate.now();
        this.account = account;
    }

    //Getters
    public int getOrderID(){ return this.orderID; }
    public ArrayList<MenuItem> getOrderedItems(){ return this.orderedItems; }
    public String getOrderType(){ return this.orderType; }
    public OrderStatus getStatus(){ return this.status; }
    public LocalDate getDatePlaced(){ return this.datePlaced; }
    public User getAccount(){ return this.account; }

    //Setters
    //NO Setter for ID so it is immutable

    public void setOrderedItems(ArrayList<MenuItem> newItems){
        this.orderedItems = newItems;
    }

    public void setOrderType(String newType){
        this.orderType = newType;
    }

    public void setStatus(OrderStatus newStatus){
        this.status = newStatus;
    }

    public void setDatePlaced(LocalDate newDate){ this.datePlaced = newDate; }

    public void setPaymentMethod(String newPaymentMethod)
    {
        this.paymentMethod = newPaymentMethod;
    }

    public void setAccount(User newAccount){ this.account = newAccount; }

    public double calcTax(Order order, double taxAmount){

        List<MenuItem> items = order.getOrderedItems();
        double totalAmount = 0.0;

        for(MenuItem item: items){
            totalAmount += item.getPrice();
        }

        return totalAmount * taxAmount;
    }

    public double calcTip(Order order, double tipAmount){

        List<MenuItem> items = order.getOrderedItems();
        double totalAmount = 0.0;

        for(MenuItem item: items){
            totalAmount += item.getPrice();
        }

        return totalAmount * tipAmount;
    }

    public void generateAndDisplayReceipt(Order order, String customerName, String paymentMethod) {
            // Assuming you have access to the restaurant's details
            String restaurantName = "Bob's Pizza Place";
            String restaurantAddress = "123 Pizza Lane, Pizza City, PC 12345";
            String restaurantPhone = "(123) 456-7890";
            String orderNumber = String.valueOf(order.getOrderID());
            String timestamp = order.getDatePlaced().toString(); // Format this as needed
            List<MenuItem> items = order.getOrderedItems();
            double totalAmount = 0.0;

            // Calculate total amount based on items
            for (MenuItem item : items) {
                totalAmount += item.getPrice();
            }

            // Assuming tax and tip are fixed for now
            double tax = totalAmount * 0.07; // Example 7% tax
            double tip = totalAmount * 0.15; // Example 15% tip

            // Create a new receipt
            Receipt receipt = new Receipt(restaurantName, restaurantAddress, restaurantPhone,
                    orderNumber, timestamp, customerName, items,
                    paymentMethod, totalAmount + tax + tip, tax, tip);

            // Generate receipt text
            String receiptText = receipt.generateReceipt();
            // Display the receipt (for example, in a console or a TextArea in GUI)
            System.out.println(receiptText);

            // Optionally, save the receipt to a database or file if needed
            saveReceiptToDatabase(receipt); // Implement this method as required
        }

        private void saveReceiptToDatabase(Receipt receipt) {
            // Logic to save the receipt details in the database
        }
    }
