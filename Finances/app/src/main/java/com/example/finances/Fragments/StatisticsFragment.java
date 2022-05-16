package com.example.finances.Fragments;

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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finances.Model.Date;
import com.example.finances.R;
import com.example.finances.ViewModel.StatisticsFragmentViewModel;

import java.util.Calendar;

public class StatisticsFragment extends Fragment implements View.OnClickListener {

    private DatePickerDialog datePickerDialog;
    private TextView startSelectDate;
    private TextView endSelectDate;
    private TextView textViewSpent;

    private Switch aSwitch;
    private boolean includeDate;

    private Button button;

    private int startYear;
    private int startMonth;
    private int startDay;
    private Date startDate;

    private int endYear;
    private int endMonth;
    private int endDay;
    private Date endDate;

    private Application app;
    private StatisticsFragmentViewModel viewModel;

    private ArrayAdapter<String> adapterItems;
    private String[] categories;
    private AutoCompleteTextView autoCompleteTextView;
    private String category;

    public StatisticsFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        app = (Application) getActivity().getApplication();

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        viewModel = new ViewModelProvider(this).get(StatisticsFragmentViewModel.class);

        button = (Button) view.findViewById(R.id.seeExpensesButton);
        button.setOnClickListener(this);
        textViewSpent = (TextView) view.findViewById(R.id.textViewSpent);

        aSwitch = (Switch) view.findViewById(R.id.switch1);
        includeDate = false;
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(aSwitch.isChecked())
                {
                    includeDate = true;
                }
                else includeDate = false;
            }
        });

        Calendar cal = Calendar.getInstance();
        startYear = cal.get(Calendar.YEAR);
        startMonth = cal.get(Calendar.MONTH);
        startDay = cal.get(Calendar.DAY_OF_MONTH);
        endYear = cal.get(Calendar.YEAR);
        endMonth = cal.get(Calendar.MONTH);
        endDay = cal.get(Calendar.DAY_OF_MONTH);
        startSelectDate = view.findViewById(R.id.textDateStart);
        endSelectDate = view.findViewById(R.id.textDateEnd);

        categories = getResources().getStringArray(R.array.categories);
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextViewStats);
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

        startSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        startDate = new Date(year, month, day);
                        month = month+1;
                        String date = getMonthFormat(month)+" "+day+" "+year;
                        startSelectDate.setText(date);
                    }
                }, startYear, startMonth, startDay);
                dialog.show();
            }
        });

        endSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        endDate = new Date(year, month, day);
                        month = month+1;
                        String date = getMonthFormat(month)+" "+day+" "+year;
                        endSelectDate.setText(date);
                    }
                }, endYear, endMonth, endDay);
                dialog.show();
            }
        });

        viewModel.getSumDate().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                textViewSpent.setText(Double.toString(aDouble) + " EUR");
            }
        });

        return view;
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

    @Override
    public void onClick(View view) {
        String startDateTemp = startSelectDate.getText().toString().trim();
        String endDateTemp = endSelectDate.getText().toString().trim();
        switch (view.getId())
        {
            case R.id.seeExpensesButton:

                if(includeDate == true && category!=null)
                {
                    viewModel.getExpensesCategoryDate(category, startDate, endDate);
                }
                if(includeDate == false && category!=null)
                {
                    viewModel.getExpensesCategory(category);
                }
                if(includeDate==true && (category==null || category.equals("No category")))
                {
                    viewModel.getExpensesPeriod(startDate, endDate);
                }

                break;
        }
    }
}