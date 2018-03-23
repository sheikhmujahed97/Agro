package in.ac.vnrvjiet.convergence.models;

/**
 * Created by pinna on 12/30/2017.
 */

public class UserModel {
    String email;
    String phoneNumber;
    Boolean hasPaidMoney;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Boolean getHasPaidMoney() {
        return hasPaidMoney;
    }

    public void setHasPaidMoney(Boolean hasPaidMoney) {
        this.hasPaidMoney = hasPaidMoney;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserModel(String email, String phoneNumber, Boolean hasPaidMoney) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hasPaidMoney = hasPaidMoney;
    }
}
