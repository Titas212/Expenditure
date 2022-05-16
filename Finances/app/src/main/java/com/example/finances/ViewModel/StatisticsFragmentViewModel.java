package com.example.finances.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finances.Model.Date;
import com.example.finances.data.UserRepository;

public class StatisticsFragmentViewModel extends AndroidViewModel
{
    private UserRepository userRepository;

    public StatisticsFragmentViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
    }

    public MutableLiveData<Double> getSumDate(){return userRepository.getSumDate();}

    public void getExpensesPeriod(Date start, Date end){userRepository.getExpensesPeriod(start, end);}

    public MutableLiveData<Boolean> getCompleted() {
        return userRepository.getCompleted();
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return userRepository.getProgressBar();
    }

    public void getExpensesCategory(String category)
    {
        userRepository.getExpensesCategory(category);
    }

    public void getExpensesCategoryDate(String category, Date start, Date end)
    {
        userRepository.getExpensesCategoryDate(category, start, end);
    }
}
