package pizza_shop_system.order;

import org.json.JSONArray;
import org.json.JSONObject;
import pizza_shop_system.account.User;
import pizza_shop_system.menu.MenuItem;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

public class Order {

    private String paymentMethod;
    private int orderID;
    private static int nextID = 0;
    private ArrayList<MenuItem> orderedItems;
    private String orderType;
    private OrderStatus status;
    private LocalDate datePlaced;
    private User account;

    private static final String DATA_FILE = "data_files/orders.json";

    // DEFAULT CONSTRUCTOR
    public Order() {
        this.paymentMethod = "DEFAULT PAYMENT METHOD";
        this.orderID = nextID++;
        this.orderedItems = new ArrayList<>();
        this.orderType = "DEFAULT";
        this.status = OrderStatus.INCOMPLETE;
        this.datePlaced = LocalDate.now();
        this.account = null;
    }

    public Order(String paymentMethod, int orderID, ArrayList<MenuItem> orderedItems, String orderType, OrderStatus status, User account) {
        this.paymentMethod = paymentMethod;
        this.orderID = orderID;
        this.orderedItems = orderedItems;
        this.orderType = orderType;
        this.status = status;
        this.datePlaced = LocalDate.now();
        this.account = account;
    }

    // Getters
    public int getOrderID() { return this.orderID; }
    public ArrayList<MenuItem> getOrderedItems() { return this.orderedItems; }
    public String getOrderType() { return this.orderType; }
    public OrderStatus getStatus() { return this.status; }
    public LocalDate getDatePlaced() { return this.datePlaced; }
    public User getAccount() { return this.account; }

    // Setters
    public void setOrderedItems(ArrayList<MenuItem> newItems) { this.orderedItems = newItems; }
    public void setOrderType(String newType) { this.orderType = newType; }
    public void setStatus(OrderStatus newStatus) { this.status = newStatus; }
    public void setDatePlaced(LocalDate newDate) { this.datePlaced = newDate; }
    public void setPaymentMethod(String newPaymentMethod) { this.paymentMethod = newPaymentMethod; }
    public void setAccount(User newAccount) { this.account = newAccount; }

    public double calcTax(double taxRate) {
        double total = orderedItems.stream().mapToDouble(MenuItem::getPrice).sum();
        return total * taxRate;
    }

    public double calcTip(double tipRate) {
        double total = orderedItems.stream().mapToDouble(MenuItem::getPrice).sum();
        return total * tipRate;
    }

    public void addItem(MenuItem item) {
        if (item != null) {
            orderedItems.add(item);
        }
    }


    public void generateAndDisplayReceipt(String customerName) {
        String restaurantName = "Bob's Pizza Place";
        String restaurantAddress = "123 Pizza Lane, Pizza City, PC 12345";
        String restaurantPhone = "(123) 456-7890";
        String orderNumber = String.valueOf(this.getOrderID());
        String timestamp = this.getDatePlaced().toString();
        double subtotal = orderedItems.stream().mapToDouble(MenuItem::getPrice).sum();
        double tax = subtotal * 0.07;
        double tip = subtotal * 0.15;
        double total = subtotal + tax + tip;

        Receipt receipt = new Receipt(restaurantName, restaurantAddress, restaurantPhone,
                orderNumber, timestamp, customerName, orderedItems,
                paymentMethod, total, tax, tip);

        System.out.println(receipt.generateReceipt());
        saveReceiptToDatabase(receipt); // Stub method
    }

    private void saveReceiptToDatabase(Receipt receipt) {
        // Placeholder: connect and save to DB or file if required
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("orderID", orderID);
        json.put("paymentMethod", paymentMethod);
        json.put("orderType", orderType);
        json.put("status", status.toString());
        json.put("datePlaced", datePlaced.toString());

        JSONArray itemsArray = new JSONArray();
        for (MenuItem item : orderedItems) {
            JSONObject itemJson = new JSONObject();
            itemJson.put("itemID", item.getItemID());
            itemJson.put("category", item.getCategory());
            itemJson.put("price", item.getPrice());
            itemJson.put("quantity", item.getQuantity());
            itemJson.put("name", item.getName());
            itemJson.put("description", item.getDescription());
            itemJson.put("toppings", new JSONArray(item.getToppings()));
            itemsArray.put(itemJson);
        }
        json.put("orderedItems", itemsArray);

        if (account != null) {
            JSONObject userJson = new JSONObject();
            userJson.put("id", account.getId());
            userJson.put("name", account.getName());
            userJson.put("email", account.getEmail());
            userJson.put("address", account.getAddress());
            userJson.put("phoneNumber", account.getPhoneNumber());
            userJson.put("accountType", account.getAccountType());
            json.put("account", userJson);
        } else {
            json.put("account", JSONObject.NULL);
        }

        return json;
    }

    public void saveToFile() throws IOException {
        JSONObject currentOrder = this.toJson();
        String path = "data_files/orders.json";

        JSONArray ordersArray;

        // Read existing orders if file exists
        if (Files.exists(Paths.get(path))) {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            ordersArray = new JSONArray(content);
        } else {
            ordersArray = new JSONArray();
        }

        ordersArray.put(currentOrder);

        // Save the updated array back to file
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(ordersArray.toString(4));
        }
    }

    //For removal if needed
    public static void removeOrderById(int targetOrderID) throws IOException {
        ArrayList<Order> orders = loadAllOrders();  // Load all
        boolean removed = orders.removeIf(order -> order.getOrderID() == targetOrderID);

        if (!removed) {
            throw new IllegalArgumentException("Order with ID " + targetOrderID + " not found.");
        }

        // Overwrite the file with remaining orders
        JSONArray updatedArray = new JSONArray();
        for (Order order : orders) {
            updatedArray.put(order.toJson());
        }

        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            writer.write(updatedArray.toString(4));
        }
    }

    //For status updates from management (accpeting order,etc.) Utilizes Enum
    public static void updateOrderStatus(int targetOrderID, OrderStatus newStatus) throws IOException {
        ArrayList<Order> orders = loadAllOrders();
        boolean found = false;

        for (Order order : orders) {
            if (order.getOrderID() == targetOrderID) {
                order.setStatus(newStatus);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Order with ID " + targetOrderID + " not found.");
        }

        // Save updated list
        JSONArray updatedArray = new JSONArray();
        for (Order order : orders) {
            updatedArray.put(order.toJson());
        }

        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            writer.write(updatedArray.toString(4));
        }
    }

    //For just one order
// For just one order
    public static Order fromJsonByOrderID(int targetOrderID) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(DATA_FILE)));
        JSONArray ordersArray = new JSONArray(content);

        for (int i = 0; i < ordersArray.length(); i++) {
            JSONObject json = ordersArray.getJSONObject(i);

            int orderID = json.getInt("orderID");
            if (orderID == targetOrderID) {
                String paymentMethod = json.getString("paymentMethod");
                String orderType = json.getString("orderType");
                OrderStatus status = OrderStatus.valueOf(json.getString("status"));
                LocalDate datePlaced = LocalDate.parse(json.getString("datePlaced"));

                ArrayList<MenuItem> items = new ArrayList<>();
                JSONArray itemsArray = json.getJSONArray("orderedItems");
                for (int j = 0; j < itemsArray.length(); j++) {
                    JSONObject itemJson = itemsArray.getJSONObject(j);

                    ArrayList<String> toppings = new ArrayList<>();
                    JSONArray toppingsJson = itemJson.optJSONArray("toppings");
                    if (toppingsJson != null) {
                        for (int k = 0; k < toppingsJson.length(); k++) {
                            toppings.add(toppingsJson.getString(k));
                        }
                    }

                    items.add(new MenuItem(
                            itemJson.getString("itemID"),
                            itemJson.getString("category"),
                            itemJson.getDouble("price"),
                            itemJson.getInt("quantity"),
                            itemJson.getString("name"),
                            itemJson.getString("description"),
                            toppings
                    ));
                }

                User user = null;
                if (!json.isNull("account")) {
                    JSONObject userJson = json.getJSONObject("account");
                    user = new User(
                            userJson.getInt("id"),
                            userJson.getString("accountType"),
                            userJson.getString("email"),
                            "", // password optional
                            userJson.getString("name"),
                            userJson.getString("address"),
                            userJson.getString("phoneNumber")
                    );
                }

                Order order = new Order(paymentMethod, orderID, items, orderType, status, user);
                order.setDatePlaced(datePlaced);
                return order;
            }
        }

        throw new IllegalArgumentException("Order with ID " + targetOrderID + " not found.");
    }

    public static ArrayList<Order> loadAllOrders() throws IOException {
        ArrayList<Order> orders = new ArrayList<>();
        Path path = Paths.get(DATA_FILE);

        if (!Files.exists(path)) {
            return orders; // return empty list if file doesn't exist
        }

        String content = new String(Files.readAllBytes(path));
        JSONArray ordersArray = new JSONArray(content);

        for (int i = 0; i < ordersArray.length(); i++) {
            JSONObject json = ordersArray.getJSONObject(i);
            int orderID = json.getInt("orderID");

            if (orderID >= nextID) {
                nextID = orderID + 1; // Necessary check to prevent id from resetting to 0 on closing.
            }

            String paymentMethod = json.getString("paymentMethod");
            String orderType = json.getString("orderType");
            OrderStatus status = OrderStatus.valueOf(json.getString("status"));
            LocalDate datePlaced = LocalDate.parse(json.getString("datePlaced"));

            ArrayList<MenuItem> items = new ArrayList<>();
            JSONArray itemsArray = json.getJSONArray("orderedItems");
            for (int j = 0; j < itemsArray.length(); j++) {
                JSONObject itemJson = itemsArray.getJSONObject(j);

                ArrayList<String> toppings = new ArrayList<>();
                JSONArray toppingsJson = itemJson.optJSONArray("toppings");
                if (toppingsJson != null) {
                    for (int k = 0; k < toppingsJson.length(); k++) {
                        toppings.add(toppingsJson.getString(k));
                    }
                }

                items.add(new MenuItem(
                        itemJson.getString("itemID"),
                        itemJson.getString("category"),
                        itemJson.getDouble("price"),
                        itemJson.getInt("quantity"),
                        itemJson.getString("name"),
                        itemJson.getString("description"),
                        toppings
                ));
            }

            User user = null;
            if (!json.isNull("account")) {
                JSONObject userJson = json.getJSONObject("account");
                user = new User(
                        userJson.getInt("id"),
                        userJson.getString("accountType"),
                        userJson.getString("email"),
                        "", // password left blank intentionally
                        userJson.getString("name"),
                        userJson.getString("address"),
                        userJson.getString("phoneNumber")
                );
            }

            Order order = new Order(paymentMethod, orderID, items, orderType, status, user);
            order.setDatePlaced(datePlaced);
            orders.add(order);
        }

        return orders;
    }

}