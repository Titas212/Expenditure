package com.example.finances.Model;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;

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

    public static Comparator<Expense> newToOld= new Comparator<Expense>() {
        @Override
        public int compare(Expense expense, Expense t1) {
            return new CompareToBuilder().append(expense.getDate().getYear(), t1.getDate().getYear()).append(expense.getDate().getMonth(), t1.getDate().getMonth()).append(expense.getDate().getDay(), t1.getDate().getDay()).toComparison();
        }
    };
    public static Comparator<Expense> oldToNew= new Comparator<Expense>() {
        @Override
        public int compare(Expense expense, Expense t1) {
            return new CompareToBuilder().append(t1.getDate().getYear(), expense.getDate().getYear()).append(t1.getDate().getMonth(), expense.getDate().getMonth()).append(t1.getDate().getDay(), expense.getDate().getDay()).toComparison();
        }
    };
}
