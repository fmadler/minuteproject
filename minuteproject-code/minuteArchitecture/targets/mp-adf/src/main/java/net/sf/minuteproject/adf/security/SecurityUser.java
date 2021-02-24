package net.sf.minuteproject.adf.security;

public class SecurityUser {

    private String userId, firstName, lastName, departmentNumber, email;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getDepartmentNumber() {
        return departmentNumber;
    }
    
    public String toString() {
        return "(userId="+userId+", firstName="+firstName+", lastName="+lastName+", email="+email+", departmentNumber="+departmentNumber+")";
    }
}
