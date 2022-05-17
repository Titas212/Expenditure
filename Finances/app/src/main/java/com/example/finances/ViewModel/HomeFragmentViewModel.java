package com.example.finances.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.finances.Model.Date;
import com.example.finances.Model.Limit;
import com.example.finances.data.UserRepository;

public class HomeFragmentViewModel extends AndroidViewModel
{
    private UserRepository userRepository;

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
    }

    public void addExpensesLimit(double limit, Date start, Date end)
    {
        userRepository.addExpensesLimit(limit, start, end);
    }

    public void increaseLimit(double increase, Limit limit)
    {
        userRepository.increaseLimit(increase, limit);
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

    public void addCurrentMoney(double money)
    {
        userRepository.addCurrentMoney(money);
    }

    public void increaseBudget(double increase,double currentMoney)
    {
        userRepository.increaseBudget(increase, currentMoney);
    }

    public void getBudgetFromDb()
    {
        userRepository.getBudgetFromDb();
    }

    public void signOut() {
        userRepository.signOut();
    }
}
