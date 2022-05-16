package com.example.finances.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.finances.Model.Expense;
import com.example.finances.data.UserRepository;

import java.util.ArrayList;

public class MyAdapterViewModel extends AndroidViewModel
{
    private UserRepository userRepository;

    public MyAdapterViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
    }

    public void getExpenses()
    {
        userRepository.getExpenses();
    }

    public MutableLiveData<ArrayList<Expense>> getExpenseList()
    {
        return userRepository.getExpenseList();
    }

}
