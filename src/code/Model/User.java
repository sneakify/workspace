package code.Model;

public class User {


    private int userID;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String createdDate;
    private double purchasingPower;


    public User(int userID,
                String lastName, String firstName,
                String userName,
                String email,
                String password,
                String createdDate,
                double purchasingPower) {
        this.userID = userID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.purchasingPower = purchasingPower;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public double getPurchasing_power() {
        return purchasingPower;
    }

    public void setPurchasing_power(double purchasing_power) {
        this.purchasingPower = purchasing_power;
    }
}
