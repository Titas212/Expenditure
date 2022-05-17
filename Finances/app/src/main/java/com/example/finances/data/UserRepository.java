package com.example.finances.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.finances.Model.Date;
import com.example.finances.Model.Expense;
import com.example.finances.Model.Limit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRepository {
    private static UserRepository instance;
    private UserDAO userDAO;

    public UserRepository()
    {
        userDAO = UserDAO.getInstance();
    }

    public static UserRepository getInstance() {
        if(instance == null)
        {
            instance = new UserRepository();
        }
        return instance;
    }

    public void register(String email, String password, String fullName, String age)
    {
        userDAO.register(email, password, fullName, age);
    }

    public MutableLiveData<String> getAuthenticationMessage() {
        return userDAO.getAuthenticationMessage();
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return userDAO.getProgressBar();
    }

    public MutableLiveData<Boolean> getCompleted() {
        return userDAO.getCompleted();
    }

    public MutableLiveData<String> getEmail() {return userDAO.getEmail();}

    public void resetPassword(String email)
    {
        userDAO.resetPassword(email);
    }

    public MutableLiveData<String> getAge() {
        return userDAO.getAge();
    }

    public MutableLiveData<String> getFullName() {
        return userDAO.getFullName();
    }

    public void getProfile()
    {
        userDAO.getProfile();
    }

    public void login(String email, String password)
    {
        userDAO.login(email, password);
    }

    public void addExpense(Expense expense)
    {
        userDAO.addExpense(expense);
    }

    public void getExpenses()
    {
        userDAO.getExpenses();
    }

    public MutableLiveData<ArrayList<Expense>> getExpenseList()
    {
        return userDAO.getExpenseList();
    }

    public MutableLiveData<Double> getSumDate(){return userDAO.getSumDate();}

    public void getExpensesPeriod(Date start, Date end){userDAO.getExpensesPeriod(start, end);}

    public void getExpensesCategory(String category)
    {
        userDAO.getExpensesCategory(category);
    }

    public void getExpensesCategoryDate(String category, Date start, Date end)
    {
        userDAO.getExpensesCategoryDate(category, start, end);
    }

    public void addExpensesLimit(double limit, Date start, Date end)
    {
        userDAO.addExpensesLimit(limit, start, end);
    }

    public void increaseLimit(double increase, Limit limit)
    {
        userDAO.increaseLimit(increase, limit);
    }

    public MutableLiveData<Limit> getLimit()
    {
        return userDAO.getLimit();
    }

    public void getLimitFromDb()
    {
        userDAO.getLimitFromDb();
    }

    public void decreaseBudget(double decrease, Limit limitToDecrease, Date date, double currentMoney)
    {
        userDAO.decreaseBudget(decrease, limitToDecrease, date, currentMoney);
    }

    public MutableLiveData<Double> getCurrentBudget() {
        return userDAO.getCurrentBudget();
    }

    public void addCurrentMoney(double money)
    {
        userDAO.addCurrentMoney(money);
    }

    public void increaseBudget(double increase,double currentMoney)
    {
        userDAO.increaseBudget(increase, currentMoney);
    }

    public void getBudgetFromDb()
    {
        userDAO.getBudgetFromDb();
    }

    public void signOut() {
        userDAO.signOut();
    }

}
