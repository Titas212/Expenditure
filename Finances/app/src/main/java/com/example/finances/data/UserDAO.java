package com.example.finances.data;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.finances.MainActivity;
import com.example.finances.Register_User;
import com.example.finances.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserDAO
{
    private static UserDAO instance;
    private MutableLiveData<String> authenticationMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressBar = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> completed = new MutableLiveData<>(false);
    private FirebaseAuth mAuth;

    public UserDAO()
    {
        mAuth = FirebaseAuth.getInstance();
    }

    public static UserDAO getInstance() {
        if(instance == null)
        {
            instance = new UserDAO();
        }
        return instance;
    }

    public MutableLiveData<String> getAuthenticationMessage() {
        return authenticationMessage;
    }

    public MutableLiveData<Boolean> getProgressBar() {
        return progressBar;
    }

    public MutableLiveData<Boolean> getCompleted() {
        return completed;
    }

    public void register(String email, String password, String fullName, String age)
    {
        progressBar.setValue(true);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(fullName, age, email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "signInUserWithEmail:success");
                                        progressBar.setValue(false);
                                        completed.setValue(true);
                                        authenticationMessage.postValue("User has been registered successfully!");
                                    } else {
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        authenticationMessage.postValue("Failed to register! Try again!");
                                        progressBar.setValue(false);
                                    }
                                }
                            });
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    progressBar.setValue(false);
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                }
            }
        });

    }
}
