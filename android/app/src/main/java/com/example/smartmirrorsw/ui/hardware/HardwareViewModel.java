package com.example.smartmirrorsw.ui.hardware;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HardwareViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HardwareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hardware Settings");
    }

    public LiveData<String> getText() {
        return mText;
    }
}