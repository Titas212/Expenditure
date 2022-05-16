package com.example.finances.Model;

public class Limit
{
    private double spendingLimit;
    private Date startDate;
    private Date endDate;

    public Limit()
    {

    }

    public Limit(double spendingLimit, Date startDate, Date endDate)
    {
        this.spendingLimit=spendingLimit;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public double getSpendingLimit() {
        return spendingLimit;
    }
}
