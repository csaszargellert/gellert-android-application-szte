package com.app.nailappointment.utils.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isEditModeOn;

    public SettingsViewModel() {
        isEditModeOn = new MutableLiveData<>();
        setIsEditModeOn(false);
    }

    public MutableLiveData<Boolean> getIsEditModeOn() {
        return isEditModeOn;
    }

    public void setIsEditModeOn(boolean bool) {
        isEditModeOn.setValue(bool);
    }
}
