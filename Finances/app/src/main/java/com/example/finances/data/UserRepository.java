package com.example.finances.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.finances.Model.Date;
import com.example.finances.Model.Expense;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

}
