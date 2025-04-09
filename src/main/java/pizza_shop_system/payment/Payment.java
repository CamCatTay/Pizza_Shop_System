package pizza_shop_system.payment;

import pizza_shop_system.order.MenuItem;
import pizza_shop_system.order.Order;
import pizza_shop_system.order.OrderStatus;
import pizza_shop_system.account.User;

import java.util.ArrayList;

public class Payment {

    public double amount;
    public String paymentType;
    public boolean isProcessed;

    public Payment(){
        this.amount = 0;
        this.paymentType = "DEFAULT";
        this.isProcessed = false;
    }

    public Payment(double amount, String paymentType, boolean isProcessed){
        this.amount = amount;
        this.paymentType = paymentType;
        this.isProcessed = isProcessed;
    }

    //Getters
    public double getAmount(){ return this.amount; }
    public String getPaymentType(){ return this.paymentType; }
    public boolean getIsProcessed(){ return this.isProcessed; }

    //Setters
    public void setAmount(double newAmount){ this.amount = newAmount; }
    public void setPaymentType(String newPaymentType){ this.paymentType = newPaymentType; }
    public void setProcessed(boolean newProcessed){ this.isProcessed = newProcessed; }

    public void processCheck(Order order, double checkAmount){
        try {
            if (order == null){
                System.err.println("Error: Order is null. Payment cannot be processed!");
                return;
            }

            ArrayList<MenuItem> items = order.getOrderedItems();
            if (items == null || items.isEmpty()){
                System.err.println("Error: no items in the cart. Cannot process payment!");
                setProcessed(false);
                order.setStatus(OrderStatus.INCOMPLETE);
                return;
            }

            if (checkAmount < 0){
                System.err.println("Error: Check amount cannot be negative");
                setProcessed(false);
                order.setStatus(OrderStatus.INCOMPLETE);
                return;
            }

            int orderId = order.getOrderID();
            System.out.println("Processing payment for order ID: " + orderId);
            setPaymentType("Check");

            //Set this amount to 0 for payment check
            this.amount = 0;

            for (MenuItem item : items) {
                if (item.getPrice() < 0) {
                    System.out.println("Error: Item price cannot be negative! Please check the menu.");
                    setProcessed(false);
                    order.setStatus(OrderStatus.INCOMPLETE);
                    return;
                }
                this.amount += item.getPrice();
            }

            if (this.amount > 0 && checkAmount >= this.amount) {
                setProcessed(true);
                order.setStatus(OrderStatus.IN_PROGRESS);
                System.out.println("Payment processed successfully");
            } else {
                setProcessed(false);
                order.setStatus(OrderStatus.INCOMPLETE);

                if (checkAmount < this.amount) {
                    System.err.println("Payment processing failed: Insufficient funds.");
                } else {
                    System.err.println("Payment processing failed: Order amount is 0 or negative.");
                }
            }

        } catch (ArithmeticException e) {
            System.err.println("Error: ArithmeticException: occurred during payment processing");
            e.printStackTrace();
            setProcessed(false);
            if (order != null) {
                order.setStatus(OrderStatus.INCOMPLETE);
            }
        } catch (Exception e) {
            System.err.println("An error occurred, check the stack trace");
            e.printStackTrace();
            setProcessed(false);
            if (order != null) {
                order.setStatus(OrderStatus.INCOMPLETE);
            }
        }

    }

    public void processCash(Order order, double cash) {
        try {
            if (order == null){
                System.err.println("Error: Order is null. Payment cannot be processed!");
                return;
            }

            ArrayList<MenuItem> items = order.getOrderedItems();
            if (items == null || items.isEmpty()){
                System.err.println("Error: no items in the cart. Cannot process payment!");
                setProcessed(false);
                order.setStatus(OrderStatus.INCOMPLETE);
                return;
            }

            if (cash < 0){
                System.err.println("Error: Cash amount cannot be negative");
                setProcessed(false);
                order.setStatus(OrderStatus.INCOMPLETE);
                return;
            }

            int orderId = order.getOrderID();
            System.out.println("Processing payment for order ID: " + orderId);
            setPaymentType("Cash");

            //Set this amount to 0 for payment check
            this.amount = 0;

            for (MenuItem item : items) {
                if (item.getPrice() < 0) {
                    System.err.println("Error: Item price cannot be negative! Please check the menu.");
                    setProcessed(false);
                    order.setStatus(OrderStatus.INCOMPLETE);
                    return;
                }
                this.amount += item.getPrice();
            }

            if (this.amount > 0 && cash >= this.amount) {

                double change = cash - this.amount;

                //More accurate rounding
                change = Math.round(change * 100.0) / 100.0;

                setProcessed(true);
                order.setStatus(OrderStatus.IN_PROGRESS);
                System.out.println("Payment processed successfully");
                System.out.println("Amount to give in change: $" + String.format("%.2f", change));
            } else {
                setProcessed(false);
                order.setStatus(OrderStatus.INCOMPLETE);

                if (cash < this.amount) {
                    System.err.println("Payment processing failed: Insufficient funds.");
                } else {
                    System.err.println("Payment processing failed: Order amount is 0 or negative.");
                }
            }

        } catch (ArithmeticException e) {
            System.err.println("Error: ArithmeticException: occurred during payment processing");
            e.printStackTrace();
            setProcessed(false);
            if (order != null) {
                order.setStatus(OrderStatus.INCOMPLETE);
            }
        } catch (Exception e) {
            System.err.println("An error occurred, check the stack trace");
            e.printStackTrace();
            setProcessed(false);
            if (order != null) {
                order.setStatus(OrderStatus.INCOMPLETE);
            }
        }

    }

    public void processCard(Order order, User account){
        try {
            if(order == null){
                System.err.println("Error: Order is null. Payment cannot be processed!");
                return;
            }

            if(account == null || account.getCreditCard() == null){
                System.err.println("Error: Account or Credit Card is null. Payment cannot be processed!");
                return;
            }

            if (account.getCreditCard().verifyCard(account)) {

                int orderId = order.getOrderID();
                System.out.println("Processing payment for order ID: " + orderId);

                setPaymentType("Card");

                //Set this amount to 0 for payment check
                this.amount = 0;

                ArrayList<MenuItem> items = order.getOrderedItems();
                for (MenuItem item : items) {
                    this.amount += item.getPrice();
                }

                if (this.amount > 0) {
                    setProcessed(true);
                    order.setStatus(OrderStatus.IN_PROGRESS);
                    System.out.println("Payment processed successfully");
                } else {
                    setProcessed(false);
                    order.setStatus(OrderStatus.INCOMPLETE);
                    System.err.println("Payment processing failed");
                }
            } else {
                setProcessed(false);
                order.setStatus(OrderStatus.INCOMPLETE);
                System.err.println("Payment processing failed");
            }
        } catch (ArithmeticException e) {
            System.err.println("Error: ArithmeticException: occurred during payment processing");
            e.printStackTrace();
            setProcessed(false);
            if (order != null) {
                order.setStatus(OrderStatus.INCOMPLETE);
            }
        } catch (Exception e) {
            System.err.println("An error occurred, check the stack trace");
            e.printStackTrace();
            setProcessed(false);
            if (order != null) {
                order.setStatus(OrderStatus.INCOMPLETE);
            }
        }
    }

}