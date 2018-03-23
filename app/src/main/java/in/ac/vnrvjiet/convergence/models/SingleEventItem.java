package in.ac.vnrvjiet.convergence.models;

/**
 * Created by venkat on 14/1/18.
 */

public class SingleEventItem {

    private String eventName;
    private String roomNumber;
    private String roundCount;

    public SingleEventItem(String eventName, String roomNumber, String roundCount) {
        setEventName(eventName);
        setRoomNumber(roomNumber);
        setRoundCount(roundCount);
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getRoomNumber() {
        return "Room No : " + roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoundCount() {
        return "No.of rounds : " + roundCount;
    }

    public void setRoundCount(String roundCount) {
        this.roundCount = roundCount;
    }


}
