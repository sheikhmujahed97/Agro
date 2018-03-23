package in.ac.vnrvjiet.convergence.models;

/**
 * Created by venkat on 1/1/18.
 */

public class OfflineParticipation {

    String convergeneEventName;
    String time;

    public String getConvergeneEventName() {
        return convergeneEventName;
    }

    public String getTime() {
        return time;
    }

    public OfflineParticipation(String convergeneEventName, String time) {
        this.convergeneEventName = convergeneEventName;
        this.time = time;
    }
}
