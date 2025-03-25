package pizza_shop_system;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class RandomOrder{

    static Random random = new Random();

    //Generation of type and status
    String[] orderTypes = {"Dine in", "Takeout", "Delivery"};
    String randomOrderType = orderTypes[random.nextInt(orderTypes.length)];

    String[] statuses = {"PENDING", "COMPLETE", "CANCELLED"};
    String randomStatus = statuses[random.nextInt(statuses.length)];

    //Random Date generation for orders

    public static LocalDateTime generateRandomDate() {

        Random random = new Random();
        LocalDate today = LocalDate.now();

        int randomDays = random.nextInt(365 * 5); //Gen Random days up to 5 years
        LocalDate randomDate = today.minusDays(randomDays);

        return randomDate.atTime(random.nextInt(24), random.nextInt(60));
    }

    public static void main(String[] args){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd : HH:mm:ss");

        for(int i = 0; i < 100; i++){
            System.out.println("Random Date: " + generateRandomDate().format(formatter));
        }

    }



}
