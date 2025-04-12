package pizza_shop_system.menu;

public class MenuItem {
    private String itemID;
    private String category;
    private double price;
    private String name;
    private String description;

    public MenuItem(String itemID, String category, double price, String name, String description) {
        this.itemID = itemID;
        this.category = category;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public String getCategory() { return category; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
}
