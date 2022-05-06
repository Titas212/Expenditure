package com.example.finances.data;

import androidx.lifecycle.MutableLiveData;

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
}
