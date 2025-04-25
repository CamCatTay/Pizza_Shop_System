package pizza_shop_system.account.services;

import org.json.JSONArray;
import org.json.JSONObject;
import pizza_shop_system.account.entities.User;
import pizza_shop_system.gui.account.AccountMenuController;
import pizza_shop_system.gui.account.ManageAccountsController;
import pizza_shop_system.gui.authentication.LoginController;
import pizza_shop_system.order.entities.CreditCard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

public class AccountService {
    private static final String DATA_FILE = "data_files/Users.json";
    private static int activeUserId = 0;
    private static AccountMenuController accountMenuController;
    private static ManageAccountsController manageAccountsController;

    // Load user data from the file
    public JSONArray loadUsers() throws IOException {
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            JSONArray users = new JSONArray();
            JSONObject metaData = new JSONObject();
            metaData.put("nextUserId", 1);
            users.put(metaData);

            saveUsers(users);
            return users;
        } else {
            String content = new String(Files.readAllBytes(file.toPath()));
            JSONObject root = new JSONObject(content);
            return root.getJSONArray("users");
        }
    }

    public User getActiveUser() throws IOException {
        JSONArray users = loadUsers();

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if(user.has("user_id") && user.getInt("user_id") == activeUserId){
                User userObj = new User(
                        user.getInt("user_id"),
                        user.getString("account_type"),
                        user.getString("email"),
                        user.getString("password"),
                        user.getString("name"),
                        user.getString("address"),
                        user.getString("phone_number")
                );

                // Load credit card if it exists
                if (user.has("credit_card")) {
                    JSONObject cc = user.getJSONObject("credit_card");
                    LocalDate expDate = LocalDate.parse(cc.getString("exp_date"));

                    if (!expDate.isBefore(LocalDate.now())) {  // Card is valid
                        CreditCard card = new CreditCard(
                                cc.getString("type"),
                                cc.getString("number"),
                                cc.getString("holder_name"),
                                expDate,
                                cc.getInt("cvc_num")
                        );
                        userObj.setCreditCard(card);
                    } else {
                        System.out.println("Expired credit card found for user ID " + activeUserId);
                    }
                }

                return userObj;
            }
        }

        return null;
    }

    // Save users back to file
    public void saveUsers(JSONArray users) throws IOException {
        JSONObject root = new JSONObject();
        root.put("users", users);

        try (FileWriter fileWriter = new FileWriter(DATA_FILE)) {
            fileWriter.write(root.toString(4)); // Indent JSON for readability
        }
    }

    public void saveCardForActiveUser(CreditCard card) throws IOException {
        JSONArray users = loadUsers();

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.has("user_id") && user.getInt("user_id") == activeUserId) {
                JSONObject cc = new JSONObject();
                cc.put("type", card.getType());
                cc.put("number", card.getCardNumber());
                cc.put("holder_name", card.getHolderName());
                cc.put("exp_date", card.getExpDate().toString()); // Save as ISO date
                cc.put("cvc_num", card.getCvcNum());

                user.put("credit_card", cc);

                saveUsers(users);
                break;
            }
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
        newUser.put("user_id", nextUserId);
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
                    activeUserId = user.getInt("user_id");

                    // If manager logs in then show the manager menu buttons
                    if (user.getString("account_type").equals("manager")) {
                        accountMenuController.setManagerMenuVisible(true);
                        manageAccountsController.updateAccountsDisplay(); // When manager logs in update the manage accounts display
                    } else {
                        accountMenuController.setManagerMenuVisible(false);
                    }
                    accountMenuController.updateAccountInformationDisplay();

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

    // Remove a user account
    public String removeUser(int userId) throws IOException {
        JSONArray users = loadUsers();

        // Find and remove user by ID
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);

            if (user.getInt("user_id") == userId) {
                users.remove(i);

                // Save updated users
                saveUsers(users);
                return "User removed successfully.";
            }
        }

        return "User not found.";
    }

    public void setAccountMenuController(AccountMenuController accountMenuController) {
        AccountService.accountMenuController = accountMenuController;
    }

    public void setManageAccountsController(ManageAccountsController manageAccountsController) {
        AccountService.manageAccountsController = manageAccountsController;
    }
}
