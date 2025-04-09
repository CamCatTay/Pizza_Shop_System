package pizza_shop_system.account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class LoginHandler {

    public String AttemptLogin(String submittedEmail, String submittedPassword) {
        String usersFilePath = "data_files/users.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(usersFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] loginData = line.split(",");
                String user_id = loginData[0].trim();
                String user_email = loginData[1].trim();
                String user_password = loginData[2].trim();

                // Validate login credentials
                if (user_email.equals(submittedEmail)) {
                    if (user_password.equals(submittedPassword)) {
                        // If credentials are valid then login succeeds and return user_id
                        return user_id;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading the users.txt file: " + e.getMessage());
        } // Possibly implement finally block to warn user that there was an error validating their credentials

        return null; // Either user does not exist or email and password were incorrect.
    }

}
