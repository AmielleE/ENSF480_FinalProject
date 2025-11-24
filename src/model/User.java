package model;

public abstract class User {
    protected int userID;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected String role;

    public User(int userID, String firstName, String lastName, String email, String password, String role) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String firstName, String lastName, String email, String password, String role) {
        this(0, firstName, lastName, email, password, role);
    }

    public int getUserID()          {return userID;}
    public String getFirstName()    {return firstName;}
    public String getLastName()     {return lastName;}
    public String getEmail()        {return email;}
    public String getPassword()     {return password;}
    public String getRole()         {return role;}

    public void setUserID(int id)       {this.userID = id;}
    public void setRole(String role)    {this.role = role;}

    public boolean login(String inputEmail, String inputPass) {
        return email.equals(inputEmail) && password.equals(inputPass);
    }

    public void logout() {
        System.out.println(firstName + " logged out.");
    }
}
