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

    public int getId() { return userID; }
    public void setId(int id) { this.userID = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String fn) { this.firstName = fn; }

    public String getLastName() { return lastName; }
    public void setLastName(String ln) { this.lastName = ln; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String pw) { this.password = pw; }

    @Override
    public String toString() {
        return userID + " - " + firstName + " " + lastName;
    }

    public boolean login(String inputEmail, String inputPass) {
        return email.equals(inputEmail) && password.equals(inputPass);
    }

    public void logout() {
        System.out.println(firstName + " logged out.");
    }
}
