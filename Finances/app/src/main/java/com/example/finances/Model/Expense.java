package com.example.finances.Model;

public class Expense
{
    private double spent;
    private String category;

    public Expense()
    {
        
    }

    public Expense(double spent, String category)
    {
        this.spent = spent;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public double getSpent()
    {
        return spent;
    }
}
