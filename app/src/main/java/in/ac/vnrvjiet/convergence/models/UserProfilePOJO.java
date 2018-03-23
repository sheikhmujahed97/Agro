package in.ac.vnrvjiet.convergence.models;

/**
 * Created by venkat on 1/1/18.
 */

public class UserProfilePOJO {
    String emailId;
    String name;
    String phoneNumber;
    String profilePicUrl;

    public String getEmailId() {
        return emailId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public UserProfilePOJO(String emailId, String name, String phoneNumber, String profilePicUrl) {

        this.emailId = emailId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePicUrl = profilePicUrl;
    }
}
