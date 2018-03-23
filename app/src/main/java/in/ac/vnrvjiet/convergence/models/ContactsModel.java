package in.ac.vnrvjiet.convergence.models;


/**
 * Created by pinna on 12/30/2017.
 */

public class ContactsModel {
    String committee;
    String contactNumber;
    String personName;

    public ContactsModel(String committee, String contactNumber, String personName) {
        this.committee = committee;
        this.contactNumber = contactNumber;
        this.personName = personName;
    }

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
