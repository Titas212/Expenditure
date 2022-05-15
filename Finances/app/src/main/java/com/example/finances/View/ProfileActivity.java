package com.example.finances.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finances.R;
import com.example.finances.ViewModel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity {

    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });

        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView ageTextView = (TextView) findViewById(R.id.age);
        final TextView emailtextView = (TextView) findViewById(R.id.email);

        viewModel.getAge().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ageTextView.setText("Age:" + "\n" + s);
            }
        });

        viewModel.getFullName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                fullNameTextView.setText("Full name:" + "\n" + s);
            }
        });

        viewModel.getEmail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                emailtextView.setText("Email Address:" + "\n" + s);
            }
        });

        viewModel.getAuthenticationMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ProfileActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
        viewModel.getProfile();
    }
}