package pizza_shop_system;

import java.util.ArrayList;

public class MenuItem {

    public String name;
    public double price;
    public int amount;
    public String category;
    public ArrayList<String> toppings;

    //DEFAULT CONSTRUCTOR
    public MenuItem(){
        name = "NAME";
        price = 0;
        amount = 0;
        category = "DEFAULT";
        toppings = new ArrayList<>();
    }

    public MenuItem(String name, double price, int amount, String category, ArrayList<String> toppings){
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.category = category;
        this.toppings = toppings;
    }

    //Getters
    public String getName(){ return this.name; }
    public double getPrice(){ return this.price; }
    public int getAmount(){ return this.amount; }
    public String getCategory(){ return this.category; }
    public ArrayList<String> getToppings(){ return this.toppings; }

    //Setters
    public void setName(String newName){
        name = newName;
    }

    public void setPrice(double newPrice){
        price = newPrice;
    }

    public void setAmount(int newAmount) { amount = newAmount; }

    public void setCategory(String newCategory){
        category = newCategory;
    }

    public void setToppings(ArrayList<String> newToppings){
        toppings = newToppings;
    }


}
