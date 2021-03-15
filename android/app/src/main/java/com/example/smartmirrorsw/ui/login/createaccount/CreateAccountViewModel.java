package com.example.smartmirrorsw.ui.login.createaccount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateAccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateAccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is create account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}
