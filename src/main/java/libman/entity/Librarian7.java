package libman.entity;

import java.util.Date;

public class Librarian7 extends User7 {
    private String staffCode;
    private String position;

    public Librarian7() {
        super();
        setRole("LIBRARIAN");
    }

    public Librarian7(int userId, String username, String password, String fullName, Date dateOfBirth,
                      String address, String email, String phone, String staffCode, String position) {
        super(userId, username, password, fullName, dateOfBirth, address, email, phone, "LIBRARIAN");
        this.staffCode = staffCode;
        this.position = position;
    }

    public String getStaffCode() { return staffCode; }
    public void setStaffCode(String staffCode) { this.staffCode = staffCode; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}

