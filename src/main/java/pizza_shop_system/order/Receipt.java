package pizza_shop_system.order;

import java.util.List;

public class Receipt {
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhone;
    private String orderNumber;
    private String timestamp;
    private String customerName;
    private List<MenuItem> items;
    private String paymentMethod;
    private double totalAmount;
    private double tax;
    private double tip;

    public Receipt(String restaurantName, String restaurantAddress, String restaurantPhone,
                   String orderNumber, String timestamp, String customerName, List<MenuItem> items,
                   String paymentMethod, double totalAmount, double tax, double tip) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantPhone = restaurantPhone;
        this.orderNumber = orderNumber;
        this.timestamp = timestamp;
        this.customerName = customerName;
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.tax = tax;
        this.tip = tip;
    }

    public String generateReceipt() {
        StringBuilder receiptBuilder = new StringBuilder();
        receiptBuilder.append("******** Receipt **********\n");
        receiptBuilder.append(restaurantName).append("\n");
        receiptBuilder.append(restaurantAddress).append("\n");
        receiptBuilder.append("Phone: ").append(restaurantPhone).append("\n");
        receiptBuilder.append("Order Number: ").append(orderNumber).append("\n");
        receiptBuilder.append("Date/Time: ").append(timestamp).append("\n");
        receiptBuilder.append("Customer: ").append(customerName).append("\n");
        receiptBuilder.append("-----------------------------\n");

        for (MenuItem item : items) {
            receiptBuilder.append(item.getName()).append(" x").append(1) // Assuming quantity is always 1 for now
                    .append(" - $").append(item.getPrice()).append("\n"); // Adjust as necessary
        }

        receiptBuilder.append("-----------------------------\n");
        receiptBuilder.append("Subtotal: $").append(totalAmount - tax - tip).append("\n");
        receiptBuilder.append("Tax: $").append(tax).append("\n");
        receiptBuilder.append("Tip: $").append(tip).append("\n");
        receiptBuilder.append("Total Amount: $").append(totalAmount).append("\n");
        receiptBuilder.append("Payment Method: ").append(paymentMethod).append("\n");
        receiptBuilder.append("*****************************\n");
        return receiptBuilder.toString();
    }
}
