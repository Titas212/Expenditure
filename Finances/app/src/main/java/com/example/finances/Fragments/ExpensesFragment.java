package com.example.finances.Fragments;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finances.Model.Expense;
import com.example.finances.R;
import com.example.finances.ViewModel.MainActivityViewModel;
import com.example.finances.ViewModel.MyAdapterViewModel;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExpensesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private MyAdapterViewModel viewModel;
    private ArrayList<Expense> expenses;
    private Application app;

    private ArrayAdapter<String> adapterItems;
    private String[] sorts;
    private AutoCompleteTextView autoCompleteTextView;
    private String wantedSort;
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

        sorts = getResources().getStringArray(R.array.sort);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextViewSort);
        adapterItems = new ArrayAdapter<String>(app.getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.sort));
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                wantedSort = item;
                switch (wantedSort)
                {
                    case "Newest":
                    {
                        expenses = viewModel.getExpenseList().getValue();
                        Collections.sort(expenses, Expense.oldToNew);
                        myAdapter.notifyDataSetChanged();
                        break;
                    }
                    case "Oldest":
                    {
                        expenses = viewModel.getExpenseList().getValue();
                        Collections.sort(expenses, Expense.newToOld);
                        myAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                Toast.makeText(app.getApplicationContext(),"Sort: " + item, Toast.LENGTH_SHORT).show();
            }
        });


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