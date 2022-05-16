package com.example.finances.Model;

public class Expense
{
    private double spent;
    private String category;
    private Date date;
    private String name;

    public Expense()
    {
        
    }

    public Expense(double spent, String category, Date date, String name)
    {
        this.spent = spent;
        this.category = category;
        this.date = date;
        this.name = name;
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

    public String getName() {
        return name;
    }
}
