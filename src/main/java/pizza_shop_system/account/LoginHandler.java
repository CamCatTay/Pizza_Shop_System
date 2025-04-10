package pizza_shop_system.account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class LoginHandler {
    public boolean AttemptLogin(String submittedEmail, String submittedPassword) {
        String usersFilePath = "data_files/users.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(usersFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] loginData = line.split(",");
                String user_id = loginData[0].trim();
                String user_email = loginData[2].trim();
                String user_password = loginData[3].trim();

                // Validate login credentials
                if (user_email.equals(submittedEmail)) {
                    if (user_password.equals(submittedPassword)) {
                        // If credentials are valid then login succeeds, set ActiveUser userID to current user
                        ActiveUser.getInstance().setCurrentUser(user_id);
                        return true;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading the users.txt file: " + e.getMessage());
        } // Possibly implement finally block to warn user that there was an error validating their credentials

        return false; // Either user does not exist or email and password were incorrect.
    }

}
