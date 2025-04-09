package pizza_shop_system.account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SignUpHandler {
    private final AccountHandler accountHandler = new AccountHandler();
    private boolean NotNullNotEmpty(String string) {
        if (string != null) {
            return !string.isEmpty();
        }
        return false;
    }

    private boolean IsEmailValid(String submittedEmail) {
     return NotNullNotEmpty(submittedEmail);
    }
    private boolean DoesEmailAlreadyExist(String submittedEmail) {
        // Implement check if email is in correct format: xxx@domain
        // Possibly implement mock 2FA screen to confirm email account belongs to user

        String usersFilePath = "data_files/users.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(usersFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] loginData = line.split(",");
                String user_email = loginData[1].trim();

                // Cancel account creation because user already exists

                if (user_email.equals(submittedEmail)) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading the users.txt file: " + e.getMessage());
            return true;
        }

        return false;
    }
    private boolean IsPasswordValid(String password, String verifyPassword) {
        // Implement check if password meets security standards: >= 8 characters, >= 1 number, >= 1 uppercase, >= 1 special character
        if (NotNullNotEmpty(password) && NotNullNotEmpty(verifyPassword)) {
            return password.equals(verifyPassword);
        }
        return false;
    }

    private boolean IsNameValid(String name) {
        // Implement check if name is in correct format: firstName lastName
        return NotNullNotEmpty(name);
    }

    private boolean IsAddressValid(String address) {
        // Implement check if address is in correct format: number streetName streetType
        return NotNullNotEmpty(address);
    }

    private boolean IsPhoneNumberValid(String phoneNumber) {
        // Implement check if phone number is in correct format: ###-###-####
        return NotNullNotEmpty(phoneNumber);
    }

    // Attempts to create a new user. Returns true if successful, false otherwise for GUI feedback.
    public ArrayList<String> AttemptSignUp(String submittedEmail, String submittedPassword, String submittedVerifyPassword, String submittedName, String submittedAddress, String submittedPhoneNumber) {
        ArrayList<String> invalidConditions = new ArrayList<>();

        if (!IsEmailValid(submittedEmail)) {
            invalidConditions.add("InvalidEmail");
        }
        if (DoesEmailAlreadyExist(submittedEmail)) {
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

        // If all sign up conditions are valid nothing is returned and ACCOUNT IS CREATED, otherwise an array list of invalid conditions are returned.
        if (invalidConditions.isEmpty()) {
            // Sign up successful
            // May need to implement a return statement for if CreateAccount fails but that is unlikely so tbd
            accountHandler.CreateAccount(submittedEmail, submittedPassword, submittedName, submittedAddress, submittedPhoneNumber);
            return null;
        } else {
            // Sign up failed
            return invalidConditions;
        }
    }
}
