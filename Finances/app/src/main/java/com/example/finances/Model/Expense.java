package com.example.finances.Model;

public class Expense
{
    private double spent;
    private String category;
    private Date date;

    public Expense()
    {
        
    }

    public Expense(double spent, String category, Date date)
    {
        this.spent = spent;
        this.category = category;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public double getSpent()
    {
        return spent;
    }

    public Date getDate() {
        return date;
    }
}
