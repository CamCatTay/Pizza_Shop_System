package pizza_shop_system.order;

import pizza_shop_system.account.User;
import pizza_shop_system.menu.MenuItem;

import java.time.LocalDate;
import java.util.ArrayList;

public class CurrentOrder {
    private static CurrentOrder instance;
    private static int nextOrderID = 1; // Keep track globally

    private ArrayList<MenuItem> items;
    private int orderID;
    private User account;
    private String orderType;
    private String paymentMethod;
    private OrderStatus status;
    private LocalDate datePlaced;


    private CurrentOrder() {
        this.items = new ArrayList<>();
        this.orderID = nextOrderID++;
    }

    public static CurrentOrder getInstance() {
        if (instance == null) {
            instance = new CurrentOrder();
        }
        return instance;
    }

    public static void reset() {
        instance = null; // New instance with new orderID
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    public ArrayList<MenuItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getOrderID() {
        return orderID;
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    public void loadFrom(Order order) {
        this.account = order.getAccount();
        this.orderID = order.getOrderID();
        this.items = new ArrayList<>(order.getOrderedItems());
        this.orderType = order.getOrderType();
        this.paymentMethod = order.getPaymentMethod();
        this.status = order.getStatus();
        this.datePlaced = order.getDatePlaced();
    }


    public Order toOrder() {
        return new Order(paymentMethod, orderID, new ArrayList<>(items), orderType, status, account);
    }

}
