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
        JSONArray users;

        if (!file.exists()) {
            users = new JSONArray();
            JSONObject metaData = new JSONObject();
            metaData.put("nextUserId", 1);
            users.put(metaData);

            saveUsers(users);
        } else {
            String content = new String(Files.readAllBytes(file.toPath()));
            users = new JSONArray(content);
        }

        return users;
    }


    // Save users back to file
    public void saveUsers(JSONArray users) throws IOException {
        try (FileWriter fileWriter = new FileWriter(DATA_FILE)) {
            fileWriter.write(users.toString(4)); // Indent JSON for readability
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

        // Implement validation for empty fields
        if (email.isEmpty() || password.isEmpty() || verifyPassword.isEmpty() || name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
            return "InvalidInput";
        }

        // Check if email already exists
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.optString("email").equalsIgnoreCase(email)) {
                return "DuplicateEmail";
            }
        }

        // Check if passwords match
        if (!password.equals(verifyPassword)) {
            return "PasswordsDoNotMatch";
        }

        // Create a new user with the unique ID
        JSONObject newUser = new JSONObject();
        newUser.put("email", email);
        newUser.put("password", password);
        newUser.put("account_type", determineAccountType(email));
        newUser.put("name", name);
        newUser.put("address", address);
        newUser.put("phone_number", phoneNumber);

        int userId = users.getJSONObject(0).getInt("nextUserId");
        int nextUserId = userId + 1;
        newUser.put("userId", nextUserId);
        users.getJSONObject(0).put("nextUserId", nextUserId);

        users.put(newUser);

        saveUsers(users);

        return "Success";
    }

    // Log in an existing user
    public String login(String email, String password) throws IOException {
        JSONArray users = loadUsers();

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.optString("email").equalsIgnoreCase(email)) {
                if (user.optString("password").equals(password)) {
                    activeUserId = user.getInt("userId");
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

            if (user.getInt("userId") == userId) {
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

            if (user.getInt("userId") == userId) {
                // Check the old password
                if (!user.optString("password").equals(oldPassword)) {
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

            if (user.getInt("userId") == userId) {
                users.remove(i);

                // Save updated users
                saveUsers(users);
                return "User deleted successfully.";
            }
        }

        return "User not found.";
    }
}
