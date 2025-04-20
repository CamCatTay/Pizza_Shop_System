package pizza_shop_system.order;

import pizza_shop_system.menu.MenuItem;

import java.util.ArrayList;

public class currentOrder {
    private static currentOrder instance;
    private ArrayList<MenuItem> items;
    private int orderID; // Optional, if needed to track

    private currentOrder() {
        items = new ArrayList<>();
    }

    public static currentOrder getInstance() {
        if (instance == null) {
            instance = new currentOrder();
        }
        return instance;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    public ArrayList<MenuItem> getItems() {
        return new ArrayList<>(items); // Return a copy for safety
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void setOrderID(int id) {
        this.orderID = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    public Order toOrder(String paymentMethod, String orderType) {
        return new Order(paymentMethod, orderID, new ArrayList<>(items), orderType, OrderStatus.INCOMPLETE, null);
    }
}
