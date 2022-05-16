package com.example.finances.data;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.finances.Model.Date;
import com.example.finances.Model.Expense;
import com.example.finances.Model.User;
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

import java.util.ArrayList;
import java.util.HashMap;

public class UserDAO
{
    private static UserDAO instance;
    private MutableLiveData<String> authenticationMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressBar = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> completed = new MutableLiveData<>(false);
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> fullName = new MutableLiveData<>();
    private MutableLiveData<String> age = new MutableLiveData<>();
    private MutableLiveData<Double> sumDate = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Expense>> expenses = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Expense>> dateExpenses = new MutableLiveData<>();
    private FirebaseAuth mAuth;
    private String userId;
    private DatabaseReference reference;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public UserDAO()
    {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public static UserDAO getInstance() {
        if(instance == null)
        {
            instance = new UserDAO();
        }
        return instance;
    }

    public MutableLiveData<Double>getSumDate()
    {
        return sumDate;
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

    public MutableLiveData<ArrayList<Expense>> getExpenseList()
    {
        return expenses;
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

    public void addExpense(Expense expense)
    {
        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Expense").push().setValue(expense).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    progressBar.setValue(false);
                    authenticationMessage.setValue("Your expense has been registered!");
                    completed.setValue(true);
                }
                else
                {
                    progressBar.setValue(false);
                    authenticationMessage.setValue("Something went wrong!");
                }
            }
        });

    }

    public void getExpenses()
    {
        ArrayList<Expense> tempExpense = new ArrayList<>();
        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Expense").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Expense expense =dataSnapshot.getValue(Expense.class);
                    tempExpense.add(expense);
                }
                expenses.setValue(tempExpense);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getExpensesPeriod(Date start, Date end)
    {
        ArrayList<Expense> tempExpense = new ArrayList<>();
        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Expense").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Expense expense =dataSnapshot.getValue(Expense.class);
                    tempExpense.add(expense);
                }
                sumDate.setValue(expensesSumBetween(tempExpense, start, end));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getExpensesCategory(String category)
    {
        ArrayList<Expense> tempExpense = new ArrayList<>();
        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Expense").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Expense expense =dataSnapshot.getValue(Expense.class);
                    tempExpense.add(expense);
                }
                sumDate.setValue(categoryExpenses(tempExpense, category));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getExpensesCategoryDate(String category, Date start, Date end)
    {
        ArrayList<Expense> tempExpense = new ArrayList<>();
        myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Expense").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Expense expense =dataSnapshot.getValue(Expense.class);
                    tempExpense.add(expense);
                }
                sumDate.setValue(categoryAndDateExpenses(tempExpense, category, start, end));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public double expensesSum(ArrayList<Expense> expenses)
    {
        double sum = 0;

        for(Expense expense: expenses)
        {
            sum+= expense.getSpent();
        }
        return sum;
    }

    public double expensesSumBetween(ArrayList<Expense> expenses, Date start, Date end)
    {
        double sum = 0;

        for(Expense expense: expenses)
        {
            if(dateBetween(expense.getDate(),start, end))
            {
                sum += expense.getSpent();
            }
        }
        return sum;
    }

    public boolean dateBetween(Date date,Date start, Date end)
    {
        if(date.getYear()>start.getYear() && date.getYear() < end.getYear())
        {
            return true;
        }
        if((date.getYear()==start.getYear() && date.getYear()== end.getYear())&& (date.getMonth()> start.getMonth() && date.getMonth()< end.getMonth()))
        {
            return true;
        }
        if((date.getYear()==start.getYear() && date.getYear()== end.getYear())&& (date.getMonth()== start.getMonth() && date.getMonth()== end.getMonth()) && (date.getDay()>= start.getDay() && date.getDay()<=end.getDay()))
        {
            return true;
        }
        return false;
    }

    public double categoryExpenses(ArrayList<Expense> expenses, String category)
    {
        double sum = 0;

        for(Expense expense: expenses)
        {
            if(expense.getCategory().equals(category))
            {
                sum += expense.getSpent();
            }
        }
        return sum;
    }
    public double categoryAndDateExpenses(ArrayList<Expense> expenses, String category, Date start, Date end)
    {
        double sum = 0;

        for(Expense expense: expenses)
        {
            if(expense.getCategory().equals(category) && dateBetween(expense.getDate(),start, end))
            {
                sum += expense.getSpent();
            }
        }
        return sum;
    }
}
