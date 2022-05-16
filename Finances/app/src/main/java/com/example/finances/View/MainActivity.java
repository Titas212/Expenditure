package com.example.finances.View;

import android.os.Bundle;

import com.example.finances.Fragments.AddFragment;
import com.example.finances.Fragments.ExpensesFragment;
import com.example.finances.Fragments.HomeFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finances.Fragments.StatisticsFragment;
import com.example.finances.R;
import com.example.finances.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());
     replaceFragment(new HomeFragment());

     binding.bottomNavigationView.setOnItemSelectedListener(item ->
     {
         switch (item.getItemId())
         {
             case R.id.homeFragment:
                 replaceFragment(new HomeFragment());
                 break;
             case R.id.addExpenditure:
                 replaceFragment(new AddFragment());
                 break;
             case R.id.expensesFragment:
                 replaceFragment(new ExpensesFragment());
                 break;
             case R.id.statisticsFragment:
                 replaceFragment(new StatisticsFragment());
                 break;
         }
         return true;
     });
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}