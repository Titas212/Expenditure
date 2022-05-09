package com.example.finances.data;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.finances.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDAO
{
    private static UserDAO instance;
    private MutableLiveData<String> authenticationMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressBar = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> completed = new MutableLiveData<>(false);
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> fullName = new MutableLiveData<>();
    private MutableLiveData<String> age = new MutableLiveData<>();
    private FirebaseAuth mAuth;
    private String userId;
    private DatabaseReference reference;
    private FirebaseUser user;

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

    public MutableLiveData<String> getEmail() {return email;}

    public MutableLiveData<String> getAge() {
        return age;
    }

    public MutableLiveData<String> getFullName() {
        return fullName;
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

    public void resetPassword(String email)
    {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    progressBar.setValue(false);
                    authenticationMessage.postValue("Check your email to reset password!");
                    completed.setValue(true);
                }
                else
                {
                    authenticationMessage.postValue("Try again!");
                    progressBar.setValue(false);
                }
            }
        });
    }

    public void getProfile()
    {
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile !=null)
                {
                    fullName.postValue(userProfile.fullName);
                    email.postValue(userProfile.email);
                    age.postValue(userProfile.age);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                authenticationMessage.postValue("Something went wrong!");
            }
        });
    }

    public void login(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified())
                    {
                        progressBar.setValue(false);
                        completed.setValue(true);
                    }
                    else
                    {
                        user.sendEmailVerification();
                        authenticationMessage.setValue("Check your email to verify your account!");
                        progressBar.setValue(false);
                    }

                }
                else
                {
                    authenticationMessage.setValue("Failed to login! Please check your credentials!");
                }
            }
        });
    }
}
