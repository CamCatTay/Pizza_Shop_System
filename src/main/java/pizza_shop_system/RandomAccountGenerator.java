package pizza_shop_system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomAccountGenerator {

    private static Random random = new Random();

    public RandomAccountGenerator() {
    }

    public static void main(String[] args) {

        int numAccountsToGen = 1000;
        boolean isManagement;
        //boolean creditCard;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("data_files/random_accounts.txt"))) {

            for(int i =0; i < numAccountsToGen; i++){

                //SETUP FOR CREDIT CARD TESTS NEED GEN RANDOM CC INFO FIRST
                //creditCard = random.nextBoolean();
                //if(creditCard) {
                //    RandomAccount account = new RandomAccount();
                //    writer.write("ID: " + account.getId() + ", NAME: " + account.getName() + ", EMAIL: " + account.getEmail() +
                //            ", PHONE NUMBER: " + account.getPhoneNumber() + ", PASSWORD: " + account.getPassword() + ", ACCOUNT TYPE: "
                //            + account.getAccountType() + ", CREDIT CARD: " + account.getCreditCard());
                //} else {
                //    RandomAccount account = new RandomAccount();
                //    writer.write("ID: " + account.getId() + ", NAME: " + account.getName() + ", EMAIL: " + account.getEmail() +
                //            ", PHONE NUMBER: " + account.getPhoneNumber() + ", PASSWORD: " + account.getPassword() + ", ACCOUNT TYPE: "
                //            + account.getAccountType());
                //}

                //Toggle for management account
                isManagement = random.nextBoolean();
                RandomAccount account = new RandomAccount();
                if(isManagement){
                    account.setAccountType("Management");
                }
                writer.write("ID: " + account.getId() + ", NAME: " + account.getName() + ", EMAIL: " + account.getEmail() +
                        ", PHONE NUMBER: " + account.getPhoneNumber() + ", PASSWORD: " + account.getPassword() + ", ACCOUNT TYPE: "
                        + account.getAccountType() + "\n");
            }
            System.out.println("1000 random accounts have been created for testing and have been saved to random_accounts.txt");
            writer.close();

        } catch (IOException e){
            System.err.println("FILE IOException");
            e.printStackTrace();
        } catch (Exception e){
            System.err.println("Error: Check the trace.");
            e.printStackTrace();
        }

    }
}