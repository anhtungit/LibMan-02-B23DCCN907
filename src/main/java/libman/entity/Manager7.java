package libman.entity;

import java.util.Date;

public class Manager7 extends User7 {
    private String managerCode;

    public Manager7() {
        super();
        setRole("MANAGER");
    }

    public Manager7(int userId, String username, String password, String fullName, Date dateOfBirth,
                    String address, String email, String phone, String managerCode) {
        super(userId, username, password, fullName, dateOfBirth, address, email, phone, "MANAGER");
        this.managerCode = managerCode;
    }

    public String getManagerCode() { return managerCode; }
    public void setManagerCode(String managerCode) { this.managerCode = managerCode; }
}

