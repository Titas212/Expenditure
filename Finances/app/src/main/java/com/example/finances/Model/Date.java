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
        this.month = month + 1;
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


    private String getMonthFormat(int month)
    {
        switch (month)
        {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
        }
        return "JAN";
    }

    @Override
    public String toString() {
        String date = getMonthFormat(month)+" "+day+" "+year;
        return date;
    }
}
