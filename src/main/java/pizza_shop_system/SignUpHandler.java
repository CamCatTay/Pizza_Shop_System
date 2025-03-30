package pizza_shop_system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SignUpHandler {
    // Returns the type of account the user is attempting to create
    public String DetermineAccountType(String submittedEmail) {
        String pizzaShopOrganization = "@pizzashop.org";
        if (submittedEmail.endsWith(pizzaShopOrganization)) {
            return "Manager";
        } else {
            return "Customer";
        }
    }

    public boolean IsEmailValid(String email) {
        // Implement check if email is in correct format: xxx@domain
        // Possibly implement mock 2FA screen to confirm email account belongs to user

        String usersFilePath = "data_files/users.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(usersFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] loginData = line.split(",");
                String user_email = loginData[1].trim();

                // Cancel account creation because user already exists
                if (user_email.equals(email)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading the users.txt file: " + e.getMessage());
            return true;
        }

        return false;
    }
    public boolean IsPasswordValid(String password, String verifyPassword) {
        // Implement check if password meets security standards: >= 8 characters, >= 1 number, >= 1 uppercase, >= 1 special character
        return password.equals(verifyPassword);
    }

    public boolean IsNameValid(String name) {
        // Implement check if name is in correct format: firstName lastName
        return !name.isEmpty();
    }

    public boolean IsAddressValid(String address) {
        // Implement check if address is in correct format: number streetName streetType
        return !address.isEmpty();
    }
    public boolean IsPhoneNumberValid(String phoneNumber) {
        // Implement check if phone number is in correct format: ###-###-####
        return !phoneNumber.isEmpty();
    }

    // Attempts to create a new user account. Returns true if successful, false otherwise for GUI feedback.
    public ArrayList<String> AttemptSignUp(String submittedEmail, String submittedPassword, String submittedVerifyPassword, String submittedName, String submittedAddress, String submittedPhoneNumber) {
        ArrayList<String> invalidConditions = new ArrayList<>();

        if (!IsEmailValid(submittedEmail)) {
            invalidConditions.add("EmailAlreadyExists");
        }
        if (!IsPasswordValid(submittedPassword, submittedVerifyPassword)) {
            invalidConditions.add("PasswordsDoNotMatch");
        }
        if (!IsNameValid(submittedName)) {
            invalidConditions.add("InvalidName");
        }
        if (!IsAddressValid(submittedAddress)) {
            invalidConditions.add("InvalidAddress");
        }
        if (!IsPhoneNumberValid(submittedPhoneNumber)) {
            invalidConditions.add("InvalidPhoneNumber");
        }

        // If all conditions are valid nothing is returned otherwise an array list of invalid conditions are returned.
        if (!invalidConditions.isEmpty()) {
            return invalidConditions;
        } else {
            return null;
        }
    }
}
