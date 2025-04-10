package pizza_shop_system.account;

public class ActiveUser {
    private static ActiveUser instance; // Ensures only one active user exists across entire system
    private User currentUser;

    private ActiveUser() {}

    public static ActiveUser getInstance() {
        if (instance == null) {
            instance = new ActiveUser();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
