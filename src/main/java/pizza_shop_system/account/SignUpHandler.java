package pizza_shop_system.account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SignUpHandler {
    private boolean NotNullNotEmpty(String string) {
        if (string != null) {
            return !string.isEmpty();
        }
        return false;
    }

    public boolean IsEmailValid(String submittedEmail) {
        // Implement check if email is in correct format: xxx@domain
        // Possibly implement mock 2FA screen to confirm email account belongs to user

        String usersFilePath = "data_files/users.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(usersFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] loginData = line.split(",");
                String user_email = loginData[1].trim();

                // Cancel account creation because user already exists
                if (NotNullNotEmpty(submittedEmail) && user_email.equals(submittedEmail)) {
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
        if (NotNullNotEmpty(password) && NotNullNotEmpty(verifyPassword)) {
            return password.equals(verifyPassword);
        }
        return false;
    }

    public boolean IsNameValid(String name) {
        // Implement check if name is in correct format: firstName lastName
        return NotNullNotEmpty(name);
    }

    public boolean IsAddressValid(String address) {
        // Implement check if address is in correct format: number streetName streetType
        return NotNullNotEmpty(address);
    }

    public boolean IsPhoneNumberValid(String phoneNumber) {
        // Implement check if phone number is in correct format: ###-###-####
        return NotNullNotEmpty(phoneNumber);
    }

    // Attempts to create a new user. Returns true if successful, false otherwise for GUI feedback.
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

        // If all sign up conditions are valid nothing is returned otherwise an array list of invalid conditions are returned.
        if (!invalidConditions.isEmpty()) {
            return invalidConditions;
        } else {
            return null;
        }
    }
}
