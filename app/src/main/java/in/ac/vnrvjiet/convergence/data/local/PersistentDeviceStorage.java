package in.ac.vnrvjiet.convergence.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import in.ac.vnrvjiet.convergence.models.OfflineParticipation;
import in.ac.vnrvjiet.convergence.presentation.util.Constants;
import in.ac.vnrvjiet.convergence.presentation.util.ObjectSerializer;
import in.ac.vnrvjiet.convergence.presentation.util.TinyDB;

/**
 * Created by pinna on 12/29/2017.
 */

public class PersistentDeviceStorage {
    private static PersistentDeviceStorage persistStorage = null;
    private static TinyDB tinyDB;
    private SharedPreferences sharedPreferences;
    private static ArrayList<String> eventNameList;
    private static ArrayList<String> eventTimeList;

    public synchronized static PersistentDeviceStorage getInstance(Context context) {
        if (persistStorage == null) {
            persistStorage = new PersistentDeviceStorage(context);
            eventNameList = new ArrayList<>();
            eventTimeList = new ArrayList<>();
        }
        if(tinyDB==null) {
            tinyDB = new TinyDB(context);
        }
        return persistStorage;
    }

    private PersistentDeviceStorage(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getEmail() {
        return sharedPreferences.getString(Constants.EMAIL_ID, "null");
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.EMAIL_ID, email);
        editor.apply();
    }

    public String getPhoneNumber() {
        return sharedPreferences.getString(Constants.PHONE_NUMBER, "null");
    }

    public void setPhoneNumber(String phoneNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PHONE_NUMBER, phoneNumber);
        editor.apply();
    }

    public void setName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.NAME, name);
        editor.apply();
    }

    public String getName() {
        return sharedPreferences.getString(Constants.NAME, "NoName");
    }

    public void setPic(String pic) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PROFILE_PIC, pic);
        editor.apply();
    }

    public void setUserType(String userType) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USER_TYPE, userType);
        editor.apply();
    }

    public String getUserType() {
        return sharedPreferences.getString(Constants.USER_TYPE,"null");
    }

    public String getPic() {
        return sharedPreferences.getString(Constants.PROFILE_PIC, "null");
    }

    public void participateInEvent(String eventName, String time) {
        eventNameList.add(eventName);
        eventTimeList.add(time);
        tinyDB.putListString(Constants.EVENT_NAME_LIST,eventNameList);
        tinyDB.putListString(Constants.EVENT_PARTICIPATION_TIME_LIST,eventTimeList);
    }

    public ArrayList<String> participationEventNames() {
        return tinyDB.getListString(Constants.EVENT_NAME_LIST);
    }

    public ArrayList<String> participationEventTimes() {
        return tinyDB.getListString(Constants.EVENT_PARTICIPATION_TIME_LIST);
    }

    public void updateTinyDB(ArrayList<String> unPushedEventNames,ArrayList<String> unPushedEventTimes) {
        eventNameList = unPushedEventNames;
        eventTimeList = unPushedEventTimes;
        tinyDB.putListString(Constants.EVENT_NAME_LIST,unPushedEventNames);
        tinyDB.putListString(Constants.EVENT_PARTICIPATION_TIME_LIST,unPushedEventTimes);
    }

    public void setHasPaidMoney(Boolean aBoolean) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.HAS_PAID_MONEY, aBoolean);
        editor.apply();
    }

    public Boolean getHasPaidMoney() {
        return sharedPreferences.getBoolean(Constants.HAS_PAID_MONEY,false);
    }

    public void setMyEventName(String myEventName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.MY_EVENT_NAME,myEventName);
        editor.apply();
    }

    public String getMyEventName() {
        return sharedPreferences.getString(Constants.MY_EVENT_NAME,"null");
    }


}
                