package pizza_shop_system.account;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AccountHandler {
    private static int nextID = 0;

    // Writes the data for a new account into the users file
    private void CreateAccount(String submittedEmail, String submittedPassword, String submittedName, String submittedAddress, String submittedPhoneNumber) {
        int id = nextID;
        nextID++;

        String newAccountData = String.valueOf(id) + "," + submittedEmail + "," + submittedPassword + "," + submittedName + "," + submittedAddress + "," + submittedPhoneNumber;

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("data_files/users.txt"))) {
            bufferedWriter.write(newAccountData);
            bufferedWriter.newLine(); // Skip a line before writing (for now) will need to implement finding next empty line
            System.out.println("New account data has been written to the user file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the user file: " + e.getMessage());
        }
    }

}
