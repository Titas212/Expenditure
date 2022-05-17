package com.example.finances.Model;

import java.util.ArrayList;

public class User
{
    public String fullName, age, email;
    public Limit spendingLimit;
    public double currentMoney;
    public User()
    {

    }

    public User(String fullName, String age, String email, Limit spendingLimit, double currentMoney)
    {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.spendingLimit = spendingLimit;
        this.currentMoney = currentMoney;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Limit getSpendingLimit() {
        return spendingLimit;
    }

    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSpendingLimit(Limit spendingLimit) {
        this.spendingLimit = spendingLimit;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }
}
