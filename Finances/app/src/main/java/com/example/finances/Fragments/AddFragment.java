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
import android.widget.Toast;

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
    private Button dateButton;

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
        dateButton =(Button) view.findViewById(R.id.buttonDate);
        dateButton.setOnClickListener(this);
        dateButton.setText(getTodaysDate());
        initDatepicker();
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

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.addExpenseButton:
                addExpenditure();
                break;
            case R.id.buttonDate:
                datePickerDialog.show();
                break;
        }
    }

    public void addExpenditure()
    {
        double expenseTemp = Double.parseDouble(editTextAmount.getText().toString().trim());
        Expense expense = new Expense(expenseTemp, category);
        viewModel.addExpense(expense);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void initDatepicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month+1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(app.getApplicationContext(), style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
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