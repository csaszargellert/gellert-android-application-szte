package com.app.nailappointment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SettingsViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> isEditModeOn;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
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
