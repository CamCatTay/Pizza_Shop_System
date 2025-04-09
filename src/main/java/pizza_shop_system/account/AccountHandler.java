package pizza_shop_system.account;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class AccountHandler {

    // Returns the type of account the user is attempting to create via the email domain
    public String DetermineAccountType(String submittedEmail) {
        String pizzaShopOrganization = "@pizzashop.org";
        if (submittedEmail.endsWith(pizzaShopOrganization)) {
            return "Manager";
        } else {
            return "Customer";
        }
    }

    // Writes the data for a new account into the users file
    public void CreateAccount(String submittedEmail, String submittedPassword, String submittedName, String submittedAddress, String submittedPhoneNumber) {
        int highestId = 0; // Initialize user ID tracking
        File file = new File("data_files/users.txt");

        try {
            // Read file to find the highest user ID and locate the first empty line
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder fileContent = new StringBuilder();
            boolean foundEmptyLine = false;
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length > 0) {
                        try {
                            int id = Integer.parseInt(parts[0].trim());
                            highestId = Math.max(highestId, id); // Track the highest user ID
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping invalid user ID format: " + parts[0]);
                        }
                    }
                } else if (!foundEmptyLine) {
                    foundEmptyLine = true;
                }
                fileContent.append(line).append("\n"); // Preserve existing content
            }
            bufferedReader.close();

            // Increment user ID for new account
            int newUserId = highestId + 1;
            String accountType = DetermineAccountType(submittedEmail);
            String newAccountData = newUserId + "," + accountType + "," + submittedEmail + "," + submittedPassword + "," + submittedName + "," + submittedAddress + "," + submittedPhoneNumber;

            // If an empty line exists, insert the new account there; otherwise, append it at the end
            if (foundEmptyLine) {
                fileContent.append(newAccountData).append("\n");
            } else {
                fileContent.append(newAccountData).append("\n");
            }

            // Write the updated content back to the file
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(fileContent.toString());
            bufferedWriter.close();

            System.out.println("New account created with ID " + newUserId);
        } catch (IOException e) {
            System.err.println("An error occurred while processing the user file: " + e.getMessage());
        }
    }

}
