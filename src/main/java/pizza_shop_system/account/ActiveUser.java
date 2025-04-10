package pizza_shop_system.account;

public class ActiveUser {
    private static ActiveUser instance; // Ensures only one active user exists across entire system
    private String currentUser;

    private ActiveUser() {}

    public static ActiveUser getInstance() {
        if (instance == null) {
            instance = new ActiveUser();
        }
        return instance;
    }

    // Sets current user to userID. This can be changed later to use an actual user object if needed
    public void setCurrentUser(String userID) {
        this.currentUser = userID;
    }

    public String getCurrentUser() {
        return currentUser;
    }

}
