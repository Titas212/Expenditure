package com.example.finances.Model;

public class Date
{
    private int year;
    private int month;
    private int day;

    public Date()
    {

    }

    public Date(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
