package libman.entity;

import libman.dto.ReaderDTO;
import java.util.Date;

public class Reader7 extends User7 {
    private String readerCode;
    private String faculty;
    private String readerType;

    public Reader7() {
        super();
        setRole("READER");
    }

    public Reader7(ReaderDTO info) {
        super();
        setRole("READER");
        if (info != null) {
            setUsername(info.getUsername());
            setPassword(info.getPassword());
            setFullName(info.getFullName());
            setDateOfBirth(info.getDateOfBirth());
            setAddress(info.getAddress());
            setEmail(info.getEmail());
            setPhone(info.getPhone());
            this.readerCode = info.getReaderCode();
            this.faculty = info.getFaculty();
            this.readerType = info.getReaderType();
        }
    }

    public Reader7(int userId, String username, String password, String fullName, Date dateOfBirth,
                   String address, String email, String phone, String readerCode, String faculty, String readerType) {
        super(userId, username, password, fullName, dateOfBirth, address, email, phone, "READER");
        this.readerCode = readerCode;
        this.faculty = faculty;
        this.readerType = readerType;
    }

    public boolean validateFormat() {
        if (readerCode == null || readerCode.trim().isEmpty()) return false;
        if (getUsername() == null || getUsername().trim().isEmpty()) return false;
        if (getPassword() == null || getPassword().trim().isEmpty()) return false;
        if (getFullName() == null || getFullName().trim().isEmpty()) return false;
        if (getEmail() != null && !getEmail().trim().isEmpty() && !getEmail().contains("@")) return false;
        return true;
    }

    public String getReaderCode() { return readerCode; }
    public void setReaderCode(String readerCode) { this.readerCode = readerCode; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getReaderType() { return readerType; }
    public void setReaderType(String readerType) { this.readerType = readerType; }
}

