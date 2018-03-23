package in.ac.vnrvjiet.convergence.presentation.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import in.ac.vnrvjiet.convergence.data.local.PersistentDeviceStorage;
import in.ac.vnrvjiet.convergence.presentation.util.SingleLiveEvent;

/**
 * Created by pinna on 12/29/2017.
 */

public class MainViewModel extends AndroidViewModel {

    private String userEmail, userPhoneNumber;


    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    SingleLiveEvent participateInEvent = new SingleLiveEvent();
    SingleLiveEvent syncMyData = new SingleLiveEvent();
    SingleLiveEvent<String> startCall = new SingleLiveEvent<>();
    SingleLiveEvent showTechnicalPresentation = new SingleLiveEvent();
    SingleLiveEvent replaceFragmentBackPress = new SingleLiveEvent();
    SingleLiveEvent homeFragmentBackPress = new SingleLiveEvent();
    SingleLiveEvent showHackathonRules = new SingleLiveEvent();
    SingleLiveEvent showEventsList = new SingleLiveEvent();
    SingleLiveEvent showWorkshopList = new SingleLiveEvent();

    public SingleLiveEvent getReplaceFragmentBackPress() {
        return replaceFragmentBackPress;
    }

    public SingleLiveEvent getHomeFragmentBackPress() {
        return homeFragmentBackPress;
    }

    public SingleLiveEvent getShowTechnicalPresentation() {
        return showTechnicalPresentation;
    }

    public SingleLiveEvent getSyncMyData() {
        return syncMyData;
    }

    public SingleLiveEvent getParticipateInEvent() {
        return participateInEvent;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        userEmail = PersistentDeviceStorage.getInstance(application).getEmail();
        userPhoneNumber = PersistentDeviceStorage.getInstance(application).getPhoneNumber();
    }

    public void startCallWithPhoneNumber(String phoneNumber) {
        startCall.setValue(phoneNumber);
    }

    public SingleLiveEvent<String> getStartCall() {
        return startCall;
    }

    public void participateInEvent() {
        participateInEvent.call();
    }

    public void syncMyData() {
        syncMyData.call();
    }

    public void technicalPresentationClick() {
        showTechnicalPresentation.call();
    }

    public void replaceFragmentOnBackPress() {
        replaceFragmentBackPress.call();
    }

    public void showHackathon() {
        showHackathonRules.call();
    }

    public void showWorkshop() {
        showWorkshopList.call();
    }

    public void showEvents() {
        showEventsList.call();
    }
}
