package com.example.finances.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finances.R;
import com.example.finances.ViewModel.ForgotPasswordViewModel;

public class ForgotPassword extends AppCompatActivity {

    private EditText editTextEmail;
    private Button forgotPassword;
    private ProgressBar progressBar;
    private ForgotPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = (EditText) findViewById(R.id.email);
        forgotPassword = (Button) findViewById(R.id.resetPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        viewModel.getAuthenticationMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ForgotPassword.this, s, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getCompleted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == true)
                {
                    startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                }
            }
        });

        viewModel.getProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == false)
                {
                    int visibility = aBoolean ? View.VISIBLE : View.INVISIBLE;
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword()
    {
        String email = editTextEmail.getText().toString().trim();

        if(email.isEmpty())
        {
            editTextEmail.setError("You need to enter email!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("You need to enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        viewModel.resetPassword(email);
    }
}