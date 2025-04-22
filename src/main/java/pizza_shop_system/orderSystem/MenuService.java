package pizza_shop_system.orderSystem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

public class MenuService {
    private final JSONUtil jsonUtil = new JSONUtil();

    private JSONObject loadMenuItems() throws IOException {
        String MENU_ITEMS_FILE_PATH = "src/main/java/main/menuItems.json";
        return jsonUtil.loadJSONObject(MENU_ITEMS_FILE_PATH);
    }

    public void displayMenu(String category) {
        System.out.println();
        System.out.println("===========Menu Items=========="); // Console display

        try {
            JSONObject data = loadMenuItems(); // contains items and nextId
            JSONObject items = data.getJSONObject("items");

            if (items.has(category)) {
                JSONArray categoryItems = items.getJSONArray(category);
                for (int i = 0; i < categoryItems.length(); i++) {
                    JSONObject item = categoryItems.getJSONObject(i);
                    System.out.println("ID: " + item.getInt("menuItemId") + " Name: " + item.getString("name"));
                }
            } else { // If category does not exist then default to display all menu items
                for (String key : items.keySet()) {
                    JSONArray arr = items.getJSONArray(key);
                    JSONArray categoryItems = items.getJSONArray(category);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject item = arr.getJSONObject(i);
                        System.out.println("ID: " + item.getInt("menuItemId") + " Name: " + item.getString("name"));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("==============================="); // Console display
    }

    public JSONObject getMenuItemById(int menuItemId) throws IOException {
        JSONObject menuData = loadMenuItems();
        JSONObject menuItems = menuData.getJSONObject("items");

        for (String key : menuItems.keySet()) {
            JSONArray arr = menuItems.getJSONArray(key);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject menuItem = arr.getJSONObject(i);
                if (menuItem.getInt("menuItemId") == menuItemId) {
                    return menuItem; // return menu item JSONObject
                }
            }
        }

        return null; // Menu Item with specified menuItemId does not exist
    }

    // for testing
    public static void main(String[] args) {
        System.out.println("[MENU SYSTEM]");
        MenuService menuService = new MenuService();
        OrderService orderService = new OrderService();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Browse Menu Options
            System.out.println("""
            OPTIONS: 
            1 = All 
            2 = Pizzas 
            3 = Beverages 
            4 = Select Menu Item
            """);
            int option = scanner.nextInt();
            if (option == 1) {
                menuService.displayMenu("all");
            } else if (option == 2) {
                menuService.displayMenu("pizza");
            } else if (option == 3) {
                menuService.displayMenu("beverage");

            } else if (option == 4) { // Select Menu Item
                System.out.print("Enter menu item id to select: ");
                int selectedMenuItemId = scanner.nextInt();
                System.out.println("You selected: " + selectedMenuItemId + ". 1 = Add To Order, 2 = Customize, Else = Cancel");
                int selection = scanner.nextInt();



                if (selection == 1) { // Add to Order
                    try {
                        JSONObject menuItem = menuService.getMenuItemById(selectedMenuItemId);

                        if (menuItem != null) {
                            JSONObject orderItem = new JSONObject(menuItem.toString()); // Clone default properties of menu item into a new order item, so we can customize it as we please
                            orderService.addOrderItem(orderItem); // Add order item to the current order
                        } else {
                            System.out.println("Menu item not found");
                            continue;
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else if (selection == 2) { // Customize before adding to order

                }

                if (selection == 1) {
                    //menuService.addToOrder(id);
                    System.out.println("Item added to order");
                } else if (selection == 2) {
                    System.out.println("Customizing item");
                } else if (selection == 3) {
                    System.out.println("Canceling selection");
                }


            } else {
                System.out.println("Invalid option");
            }



        }
    }
}
