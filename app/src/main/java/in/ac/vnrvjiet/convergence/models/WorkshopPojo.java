package in.ac.vnrvjiet.convergence.models;

/**
 * Created by venkat on 16/1/18.
 */

public class WorkshopPojo {

    String workshopName;
    String noOfDays;
    String registrationFees;
    String venue;

    public WorkshopPojo(String workshopName, String noOfDays, String registrationFees, String venue) {
        this.workshopName = workshopName;
        this.noOfDays = noOfDays;
        this.registrationFees = registrationFees;
        this.venue = venue;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getRegistrationFees() {
        return registrationFees;
    }

    public void setRegistrationFees(String registrationFees) {
        this.registrationFees = registrationFees;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
