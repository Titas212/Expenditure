package com.example.finances.Fragments;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finances.Model.Expense;
import com.example.finances.R;
import com.example.finances.ViewModel.MainActivityViewModel;
import com.example.finances.ViewModel.MyAdapterViewModel;

import java.util.ArrayList;

public class ExpensesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private MyAdapterViewModel viewModel;
    private ArrayList<Expense> expenses;
    private Application app;
    public ExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        app = (Application) getActivity().getApplication();
        expenses = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.expenseListRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        myAdapter = new MyAdapter(requireContext(), expenses);
        recyclerView.setAdapter(myAdapter);
        viewModel = new ViewModelProvider(this).get(MyAdapterViewModel.class);
        viewModel.getExpenseList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Expense>>() {
            @Override
            public void onChanged(ArrayList<Expense> expenses) {
                myAdapter.setList(expenses);
            }
        });
        viewModel.getExpenses();
        return view;
    }
}