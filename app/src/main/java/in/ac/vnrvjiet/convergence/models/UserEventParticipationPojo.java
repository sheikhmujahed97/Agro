package in.ac.vnrvjiet.convergence.models;

/**
 * Created by venkat on 2/1/18.
 */

public class UserEventParticipationPojo {
    String convergeneEventName;
    String time;
    int round;

    public String getConvergeneEventName() {
        return convergeneEventName;
    }

    public String getTime() {
        return time;
    }

    public int getRound() {
        return round;
    }

    public UserEventParticipationPojo(String convergeneEventName, String time, int round) {

        this.convergeneEventName = convergeneEventName;
        this.time = time;
        this.round = round;
    }

}
