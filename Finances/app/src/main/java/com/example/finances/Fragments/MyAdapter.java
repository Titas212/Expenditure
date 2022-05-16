package com.example.finances.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finances.Model.Expense;
import com.example.finances.R;
import com.example.finances.ViewModel.MainActivityViewModel;
import com.example.finances.ViewModel.MyAdapterViewModel;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    Context context;
    ArrayList<Expense> list;

    public MyAdapter(Context context, ArrayList<Expense> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.expense_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Expense expense = list.get(position);
        holder.name.setText(expense.getName());
        holder.category.setText(expense.getCategory());
        holder.amount.setText(Double.toString(expense.getSpent()));
        holder.date.setText(expense.toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<Expense> expenses)
    {
        this.list = expenses;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, amount, date, category;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amountSpentText);
            date = itemView.findViewById(R.id.date);
            category= itemView.findViewById(R.id.category);

        }
    }
}
