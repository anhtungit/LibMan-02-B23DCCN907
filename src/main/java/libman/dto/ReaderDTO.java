package libman.dto;

import java.util.Date;

public class ReaderDTO {
    private String username;
    private String password;
    private String fullName;
    private Date dateOfBirth;
    private String address;
    private String email;
    private String phone;
    private String readerCode;
    private String faculty;
    private String readerType;

    public ReaderDTO() {}

    public ReaderDTO(String username, String password, String fullName, Date dateOfBirth,
                     String address, String email, String phone, String readerCode,
                     String faculty, String readerType) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.readerCode = readerCode;
        this.faculty = faculty;
        this.readerType = readerType;
    }

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

    public String getReaderCode() { return readerCode; }
    public void setReaderCode(String readerCode) { this.readerCode = readerCode; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getReaderType() { return readerType; }
    public void setReaderType(String readerType) { this.readerType = readerType; }
}

