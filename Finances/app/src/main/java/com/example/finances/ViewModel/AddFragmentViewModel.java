package com.example.finances.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.finances.Model.Date;
import com.example.finances.Model.Expense;
import com.example.finances.Model.Limit;
import com.example.finances.data.UserRepository;

public class AddFragmentViewModel extends AndroidViewModel
{
    private UserRepository userRepository;
    public AddFragmentViewModel(@NonNull Application application)
    {
        super(application);
        userRepository = UserRepository.getInstance();
    }

    public void addExpense(Expense expense)
    {
        userRepository.addExpense(expense);
    }

    public MutableLiveData<String> getAuthenticationMessage() {
        return userRepository.getAuthenticationMessage();
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return userRepository.getProgressBar();
    }

    public MutableLiveData<Boolean> getCompleted() {
        return userRepository.getCompleted();
    }

    public MutableLiveData<Limit> getLimit()
    {
        return userRepository.getLimit();
    }

    public void getLimitFromDb()
    {
        userRepository.getLimitFromDb();
    }

    public MutableLiveData<Double> getCurrentBudget() {
        return userRepository.getCurrentBudget();
    }

    public void decreaseBudget(double decrease, Limit limitToDecrease, Date date, double currentMoney)
    {
        userRepository.decreaseBudget(decrease, limitToDecrease, date, currentMoney);
    }
}
