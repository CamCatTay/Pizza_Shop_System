package pizza_shop_system.order;

import org.json.JSONArray;
import org.json.JSONObject;
import pizza_shop_system.account.AccountService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static final String DATA_FILE = "data_files/orders.json";
    private final AccountService accountService = new AccountService();

    // Load orders from the file and ensure it exists
    public JSONArray loadOrders() throws IOException {
        File file = new File(DATA_FILE);
        JSONArray orders;

        if (!file.exists()) {
            // Create a new JSON array and set the first index to contain nextOrderId
            orders = new JSONArray();
            JSONObject metaData = new JSONObject();
            metaData.put("nextOrderId", 1); // Store nextOrderID inside json file so it exists across program executions
            orders.put(metaData); // Place nextOrderId at index 0

            // Save it to a new file to persist
            saveOrders(orders);
        } else {
            String content = new String(Files.readAllBytes(file.toPath()));
            orders = new JSONArray(content);
        }

        return orders;
    }

    // Save orders back to the file
    public void saveOrders(JSONArray orders) throws IOException {
        try (FileWriter fileWriter = new FileWriter(DATA_FILE)) {
            fileWriter.write(orders.toString(4)); // Indent JSON for readability
        }
    }

    // Add a new order, including an array of orderItemIds
    public int addOrder(JSONObject newOrder) throws IOException {
        if (accountService.getActiveUserId() == 0) {
            System.out.println("An account must be logged in to place an order.");
            return 0;
        } // An account must be logged in to place an order (0 is essentially null)

        JSONArray orders = loadOrders();

        int orderId = orders.getJSONObject(0).getInt("nextOrderId");
        int nextOrderId = orderId + 1;
        orders.getJSONObject(0).put("nextOrderId", nextOrderId);

        newOrder.put("orderId", orderId);
        newOrder.put("accountId", accountService.getActiveUserId());
        orders.put(newOrder);

        saveOrders(orders); // Save orders.json so it includes the new order and updated nextOrderId

        return newOrder.getInt("orderId"); // Return order's ID
    }

    // Delete an order by ID
    public String deleteOrder(int orderId) throws IOException {
        JSONArray orders = loadOrders();

        for (int i = 0; i < orders.length(); i++) {
            JSONObject order = orders.getJSONObject(i);

            if (order.optInt("orderId") != 0 && order.getInt("orderId") == orderId)  {
                orders.remove(i); // Remove the order by its index
                saveOrders(orders);
                return "Order deleted successfully.";
            }
        }

        return "Order with ID " + orderId + " not found.";
    }

    // Update an order's items (e.g., add/remove order items by modifying orderItemIds array)
    public String updateOrderItems(int orderId, List<Integer> newOrderItemIds) throws IOException {
        JSONArray orders = loadOrders();

        for (int i = 0; i < orders.length(); i++) {
            JSONObject order = orders.getJSONObject(i);

            if (order.optInt("orderId") != 0 && order.getInt("orderId") == orderId)  {
                // Replace the 'orderItemIds' array
                JSONArray itemIdsArray = new JSONArray(newOrderItemIds);
                order.put("orderItemIds", itemIdsArray);

                saveOrders(orders);
                return "Order items updated successfully for order ID: " + orderId;
            }
        }

        return "Order with ID " + orderId + " not found.";
    }

    // Get all items (IDs) of a specific order
    public List<Integer> getOrderItems(int orderId) throws IOException {
        JSONObject order = getOrderById(orderId);

        if (order != null) {
            JSONArray itemIdsArray = order.getJSONArray("orderItemIds");
            List<Integer> itemIds = new ArrayList<>();

            for (int i = 0; i < itemIdsArray.length(); i++) {
                itemIds.add(itemIdsArray.getInt(i));
            }

            return itemIds;
        }

        return null; // Return null if the order does not exist
    }

    // Get an order by ID as a JSON object
    public JSONObject getOrderById(int orderId) throws IOException {
        JSONArray orders = loadOrders();

        for (int i = 0; i < orders.length(); i++) {
            JSONObject order = orders.getJSONObject(i);
            if (order.optInt("orderId") != 0 && order.getInt("orderId") == orderId) { // Checks if orderId variable exists (it does not at index 0, and then check if it matches specified Id)
                return order;
            }
        }
        return null; // Return null if the order with the given ID is not found
    }

    // Get all orders with their details
    public List<JSONObject> getAllOrders() throws IOException {
        JSONArray ordersArray = loadOrders();
        List<JSONObject> orders = new ArrayList<>();

        for (int i = 0; i < ordersArray.length(); i++) {
            orders.add(ordersArray.getJSONObject(i));
        }

        return orders;
    }

    // Example main method for testing
    public static void main(String[] args) throws IOException {
        OrderService orderService = new OrderService();
        AccountService accountService = new AccountService();
        int order1 = orderService.addOrder(new JSONObject().put("orderItemIds", new JSONArray().put(1).put(2).put(3)));

    }
}