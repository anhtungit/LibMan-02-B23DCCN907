package libman.entity;

import java.util.Date;

public abstract class User7 {
    private int userId;
    private String username;
    private String password;
    private String fullName;
    private Date dateOfBirth;
    private String address;
    private String email;
    private String phone;
    private String role;

    public User7() {}

    public User7(int userId, String username, String password, String fullName, Date dateOfBirth, String address, String email, String phone, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public boolean login(String username, String password) {
        return this.username != null && this.username.equals(username) &&
               this.password != null && this.password.equals(password);
    }

    public void logout() {}

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

