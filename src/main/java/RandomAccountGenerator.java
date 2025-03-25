

public class RandomAccountGenerator {
    public RandomAccountGenerator() {
    }

    public static void main(String[] args) {
        RandomAccount account = new RandomAccount();
        System.out.println("Name: " + account.name);
        System.out.println("Email: " + account.email);
        System.out.println("Phone Number: " + account.phoneNumber);
        System.out.println("Address: " + account.address);
        System.out.println("Password: " + account.password);
        System.out.println("Account Type: " + account.accountType);
    }
}