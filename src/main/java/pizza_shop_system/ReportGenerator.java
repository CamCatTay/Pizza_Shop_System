
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ReportGenerator {

    private LocalDate date;
    private String customerName;
    private double amount;
    private int orderNumber;

    public static void generateDailyReport(LocalDate specifiedDate){

        //CHANGE AS NEEDED FOR DATABASE FILE
        //for(Order order: orders){
        //    if(Objects.equals(order.getDatePlaced(), specifiedDate)) {
        //        System.out.println(order.getDatePlaced());
        //        System.out.println("______________________");
        //        System.out.println(order.getAccount().name + " " + order.getOrderID());
        //        System.out.println(order.getOrderedItems());
        //
        //        //NEED SUBTOTAL, TAX, TIP, & TOTAL
        //        List<MenuItem> items = order.getOrderedItems();
        //        double subtotal = 0.0;
        //        for(MenuItem item: items){
        //            subtotal += item.getPrice();
        //        }
        //        System.out.println("Subtotal: " + String.format("%.2d", subtotal));
        //        System.out.println("Tax: " + String.format("%.2d", order.calcTax(order)));
        //        System.out.println("Tip: " + String.format("%.2d", order.calcTip(order)));
        //
        //        double total = subtotal + order.calcTax(order) + order.calcTip(order);
        //        System.out.println("Total: " + String.format("%.2d", total));
        //
        //        System.out.println(order.getStatus());
        //        String orderType = order.getOrderType();
        //        if (orderType.equalsIgnoreCase("DELIVERY")) {
        //            System.out.println(order.getAccount().getAddress());
        //        }
        //    }
        //}

    }

    public static void main(String[] args){







    }

}
