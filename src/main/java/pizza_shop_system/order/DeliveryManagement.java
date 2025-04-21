package pizza_shop_system.order;
/*
import pizza_shop_system.users.Employee;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DeliveryManagement {

    public Employee[] Workers = {new Employee("Jack", false), new Employee("Jill", false), new Employee("Max", false), new Employee("Anya", false), new Employee("Ernest", false), new Employee("Christina", false)};
    public Random random = new Random();
    public int deliveryTime;

    public void AssignDriver()
    {
        for (int i=0; i < Workers.length; i++)
        {
            if (!Workers[i].onDelivery)
            {
                System.out.println(Workers[i].getEmployeeName() + "is Delivering Your Order.");
                Workers[i].setOnDelivery(true);
                break;
            }
        }
    }

    public static void startTimer(int seconds) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int timeLeft = seconds;

            @Override
            public void run() {
                if (timeLeft > 0) {
                    if (timeLeft == 5) {
                        System.out.println("Order will be at destination in " + timeLeft + " minutes");
                    }
                    timeLeft--;
                } else {
                    System.out.println("Order has reached Destination");
                    timer.cancel(); // Stop the timer
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void TrackOrder()
    {
        deliveryTime = random.nextInt(15, 46);
        System.out.println("Order will be Delivered in " + deliveryTime + " minutes.");
        startTimer(deliveryTime);
    }
}

 */