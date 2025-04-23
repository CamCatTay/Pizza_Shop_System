package pizza_shop_system.menu.services;

import pizza_shop_system.menu.entities.MenuItem;

import java.io.*;
import java.util.*;

public class MenuLoader {
    private HashMap<String, List<MenuItem>> categorizedItems = new HashMap<>();

    public void loadMenu(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    MenuItem item = new MenuItem(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3], parts[4]);

                    categorizedItems.computeIfAbsent(parts[1], k -> new ArrayList<>()).add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("Menu data failed to load. Ensure menu items file exists. " + e.getMessage());
        }
    }

    public List<MenuItem> getItemsByCategory(String category) {
        return categorizedItems.getOrDefault(category, new ArrayList<>());
    }
}
