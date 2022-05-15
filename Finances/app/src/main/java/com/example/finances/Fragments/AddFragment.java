package com.example.finances.Fragments;

import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finances.Model.Date;
import com.example.finances.Model.Expense;
import com.example.finances.R;
import com.example.finances.ViewModel.AddFragmentViewModel;

import java.lang.reflect.Array;
import java.util.Calendar;

public class AddFragment extends Fragment implements View.OnClickListener {

    private EditText editTextAmount;
    private Button addExpenseButton;

    private AddFragmentViewModel viewModel;
    private ProgressBar progressBar;

    private String[] categories;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    private String category;

    private DatePickerDialog datePickerDialog;
    private TextView tvSelectDate;
    private TextView selectDate;

    private int year;
    private int month;
    private int day;
    private Date date;

    private Application app;

    public AddFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        app = (Application) getActivity().getApplication();
        viewModel = new ViewModelProvider(this).get(AddFragmentViewModel.class);
        View view = inflater.inflate(R.layout.fragment_add_expenditure, container, false);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        tvSelectDate = view.findViewById(R.id.textViewDate);
        selectDate = view.findViewById(R.id.textDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date = new Date(year, month, day);
                        month = month+1;
                        String date = getMonthFormat(month)+" "+day+" "+year;
                        selectDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        editTextAmount = (EditText) view.findViewById(R.id.amountSpentText);
        addExpenseButton = (Button) view.findViewById(R.id.addExpenseButton);
        addExpenseButton.setOnClickListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        categories = getResources().getStringArray(R.array.categories);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<String>(app.getApplicationContext(), R.layout.dropdown_item, categories);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                category = item;
                Toast.makeText(app.getApplicationContext(),"Categorie: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getAuthenticationMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(app.getApplicationContext(),s, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getProgressBar().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean==false)
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getCompleted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean==true)
                {
                    editTextAmount.setText("");
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.addExpenseButton:
                addExpenditure();
                break;
        }
    }

    public void addExpenditure()
    {
        double expenseTemp = Double.parseDouble(editTextAmount.getText().toString().trim());
        Expense expense = new Expense(expenseTemp, category, date);
        viewModel.addExpense(expense);
        progressBar.setVisibility(View.VISIBLE);
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
}