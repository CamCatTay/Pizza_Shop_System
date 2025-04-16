package pizza_shop_system.account;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class AccountService {
    private static final String DATA_FILE = "data_files/users.json";
    private static int activeUserId = 0;

    // Load user data from the file
    public JSONArray loadUsers() throws IOException {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new JSONArray();
        }

        // Read file content
        String content = Files.readString(file.toPath());
        return new JSONObject(content).getJSONArray("users");
    }

    // Save users back to file
    public void saveUsers(JSONArray users) throws IOException {
        JSONObject data = new JSONObject();
        data.put("users", users);

        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            writer.write(data.toString(4));
        }
    }

    private String determineAccountType(String email) {
        String pizzaShopOrganization = "@pizzashop.org";
        if (email.endsWith(pizzaShopOrganization)) {
            return "manager";
        } else {
            return "customer";
        }
    }

    // Sign up a new user
    public String signUp(String email, String password, String verifyPassword, String name, String address, String phoneNumber) throws IOException {
        JSONArray users = loadUsers();

        // I don't feel like adding it right now but implement method that makes sure all of these fields are not empty. Empty fields are invalid

        // Check if email already exists
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("email").equalsIgnoreCase(email)) {
                return "DuplicateEmail";
            }
        }

        // Check if passwords are the same
        if (!password.equals(verifyPassword)) {
            return "PasswordsDoNotMatch";
        }

        // Create a new user
        int newUserId = users.length() + 1; // Generate the next user ID
        JSONObject newUser = new JSONObject();
        newUser.put("user_id", newUserId);
        newUser.put("email", email);
        newUser.put("password", password);
        newUser.put("account_type", determineAccountType(email));
        newUser.put("name", name);
        newUser.put("address", address);
        newUser.put("phone_number", phoneNumber);

        // Add to users array and save
        users.put(newUser);
        saveUsers(users);

        return "Success";
    }

    // Log in an existing user
    public String login(String email, String password) throws IOException {
        JSONArray users = loadUsers();

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("email").equalsIgnoreCase(email)) {
                if (user.getString("password").equals(password)) {
                    activeUserId = user.getInt("user_id");
                    return "Success";
                } else {
                    return "IncorrectPassword";
                }
            }
        }

        return "EmailDoesNotExist";
    }

    public void logout() {
        activeUserId = 0;
    }

    public int getActiveUserId() {
        return activeUserId;
    }

    // Methods for modifying an account

    // Update user by ID
    public String updateUser(int userId, String field, String newValue) throws IOException {
        JSONArray users = loadUsers();

        // Find user by ID
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);

            if (user.getInt("user_id") == userId) {
                // Update the given field
                if (user.has(field)) {
                    user.put(field, newValue);

                    // Save updated users
                    saveUsers(users);
                    return "User " + field + " updated successfully.";
                } else {
                    return "Invalid field: " + field;
                }
            }
        }

        return "User not found.";
    }

    // Change the password for a user, user needs to verify old password first
    public String changePassword(int userId, String oldPassword, String newPassword) throws IOException {
        JSONArray users = loadUsers();

        // Find user by ID
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);

            if (user.getInt("user_id") == userId) {
                // Check the old password
                if (!user.getString("password").equals(oldPassword)) {
                    return "Incorrect old password.";
                }

                // Update the password
                user.put("password", newPassword);

                // Save updated users
                saveUsers(users);
                return "Password updated successfully.";
            }
        }

        return "User not found.";
    }

    // Delete a user account
    public String deleteUser(int userId) throws IOException {
        JSONArray users = loadUsers();

        // Find and remove user by ID
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);

            if (user.getInt("user_id") == userId) {
                users.remove(i);

                // Save updated users
                saveUsers(users);
                return "User deleted successfully.";
            }
        }

        return "User not found.";
    }
}
