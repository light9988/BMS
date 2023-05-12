package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String userName;
    private String salt;
    private String encryptedPassword;
    private boolean isAdmin;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;

    // Constructor used when creating a new user
    public User(String userName, String encryptedPassword, boolean isAdmin) {
        this.userName = userName;
        this.encryptedPassword = encryptedPassword;
        this.isAdmin = isAdmin;
    }

    // Constructor used when retrieving a user from the database
    public User(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt("ID");
        userName = resultSet.getString("username");
        encryptedPassword = resultSet.getString("password");
        isAdmin = resultSet.getBoolean("isAdmin");
        salt = resultSet.getString("SALT");
        firstName = resultSet.getString("firstName");
        lastName = resultSet.getString("lastName");
        address = resultSet.getString("mailingAddress");
        email = resultSet.getString("Email");
        phone = resultSet.getString("phoneNumber");
    }

    // Getter methods for all instance variables
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }


    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getSalt() {
        return salt;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }


}

