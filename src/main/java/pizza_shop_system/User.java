package pizza_shop_system;

public class User {
    private String name, email, address, phoneNumber, password, accountType;
    private CreditCard creditCard;
    private static int nextID;
    private int id;

    public User(String name, String email, String address, String phoneNumber, String password, String accountType){
        this.name = name;
        this.id = nextID++;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.accountType = accountType;
    }

    //CONSTRUCTOR W/ ID SETTING
    public User(String name, int id, String email, String address, String phoneNumber, String password, String accountType){
        this.name = name;
        this.id = id;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.accountType = accountType;
    }

    //CONSTRUCTOR W/ CREDIT CARD
    public User(String name, String email, String address, String phoneNumber, String password, String accountType, CreditCard creditCard){
        this.name = name;
        this.id = nextID++;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.accountType = accountType;
        this.creditCard = creditCard;
    }

    //Getters
    public String getName(){ return this.name;}
    public int getId(){ return this.id; }
    public String getEmail(){ return this.email;}
    public String getAddress(){ return this.address;}
    public String getPhoneNumber(){ return this.phoneNumber;}
    public String getPassword(){ return this.password;}
    public String getAccountType(){ return this.accountType;}
    public CreditCard getCreditCard(){ return this.creditCard; }

    //Setters
    public void setName(String newName){
        this.name = newName;
    }

    public void setId(int newID){ this.id = newID; }

    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public void setAddress(String newAddress){
        this.address = newAddress;
    }

    public void setPhoneNumber(String newPhoneNumber){
        this.phoneNumber = newPhoneNumber;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public void setAccountType(String newAccountType){
        this.accountType = newAccountType;
    }

    public void setCreditCard(CreditCard newCard){ this.creditCard = newCard; }

}

