package pizza_shop_system.account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ActiveUser {
    private static ActiveUser instance; // Ensures only one active user exists across entire system
    private String currentUser;

    private ActiveUser() {}

    public static ActiveUser getInstance() {
        if (instance == null) {
            instance = new ActiveUser();
        }
        return instance;
    }

    // Sets current user to userID. This can be changed later to use an actual user object if needed
    public void setCurrentUser(String userID) {
        this.currentUser = userID;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    // Returns the users.txt data of a user based off userID
    private String getUserDataById(String userId) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data_files/users.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    try {
                        String id = parts[0].trim();
                        if (id.equals(userId)) {
                            return line; // Return the matching user data
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid user ID format: " + parts[0]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return null; // Return null if user not found
    }

    public String getActiveUserData() {
        return getUserDataById(currentUser);
    }
}
