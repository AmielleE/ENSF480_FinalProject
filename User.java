public abstract class User {
    protected int userID;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;

    public User(int userID, String firstName, String lastName, String email, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public boolean login(String inputEmail, String inputPass) {
        return email.equals(inputEmail) && password.equals(inputPass);
    }

    public void logout() {
        System.out.println(firstName + " logged out.");
    }
}
