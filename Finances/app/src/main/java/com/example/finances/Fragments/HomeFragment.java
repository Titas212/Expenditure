package com.example.finances.Fragments;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finances.Model.Date;
import com.example.finances.Model.Limit;
import com.example.finances.R;
import com.example.finances.View.LoginActivity;
import com.example.finances.View.MainActivity;
import com.example.finances.View.ProfileActivity;
import com.example.finances.ViewModel.HomeFragmentViewModel;
import com.example.finances.ViewModel.StatisticsFragmentViewModel;

import java.util.Calendar;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private Button setLimit;
    private Button increaseBudget;
    private EditText limit;
    private TextView budget;
    private ProgressBar progressBar;
    private Limit limitHome;

    private int startYear;
    private int startMonth;
    private int startDay;
    private Date startDate;

    private int endYear;
    private int endMonth;
    private int endDay;
    private Date endDate;
    private TextView startSelectDate;
    private TextView endSelectDate;
    private Button logout;

    private HomeFragmentViewModel viewModel;

    private Application app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        app = (Application) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);

        setLimit = (Button) view.findViewById(R.id.setLimitButton);
        setLimit.setOnClickListener(this);
        increaseBudget = (Button) view.findViewById(R.id.increaseBudget);
        increaseBudget.setOnClickListener(this);
        limit = (EditText) view.findViewById(R.id.editTextLimit);
        budget = (TextView) view.findViewById(R.id.textViewBudget);
        logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(this);

        Calendar cal = Calendar.getInstance();
        startYear = cal.get(Calendar.YEAR);
        startMonth = cal.get(Calendar.MONTH);
        startDay = cal.get(Calendar.DAY_OF_MONTH);
        endYear = cal.get(Calendar.YEAR);
        endMonth = cal.get(Calendar.MONTH);
        endDay = cal.get(Calendar.DAY_OF_MONTH);
        startSelectDate = view.findViewById(R.id.textDateStartLimit);
        endSelectDate = view.findViewById(R.id.textDateEndLimit);

        progressBar = (ProgressBar) view.findViewById(R.id.limitProgressBar);

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

        viewModel.getLimit().observe(getViewLifecycleOwner(), new Observer<Limit>() {
            @Override
            public void onChanged(Limit limit) {
                limitHome = limit;
                budget.setText(Double.toString(limitHome.getSpendingLimit()));
                updateProgressBar();
            }
        });

        viewModel.getCurrentBudget().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                budget.setText(Double.toString(aDouble));
                updateProgressBar();
            }
        });
        viewModel.getBudgetFromDb();
        viewModel.getLimitFromDb();
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
        if(view.getId()==R.id.setLimitButton)
        {
            viewModel.addExpensesLimit(Double.parseDouble(limit.getText().toString().trim()), startDate, endDate);
            viewModel.addCurrentMoney(Double.parseDouble(limit.getText().toString().trim()));
            updateProgressBar();
        }
        if(view.getId()==R.id.increaseBudget)
        {
            viewModel.increaseBudget(Double.parseDouble(limit.getText().toString().trim()), Double.parseDouble(budget.getText().toString().trim()));
            updateProgressBar();
            Reload();
        }
        if(view.getId()==R.id.logout)
        {
            viewModel.signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    public void updateProgressBar()
    {
        double progressCircle = Math.floor(Double.parseDouble(budget.getText().toString().trim())/limitHome.getSpendingLimit()*100);
        progressBar.setProgress((int)progressCircle);
    }

    public void Reload(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(HomeFragment.this.getId(), new HomeFragment()).commit();
    }

}