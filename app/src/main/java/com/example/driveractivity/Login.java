package com.example.driveractivity;

import android.app.Service;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.jar.Attributes;

public class Login extends AppCompatActivity
{   private EditText Name;
    private EditText Password;
    public TextView Info;
    private Button LoginBtn;
    private int counter = 5;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    // private ProgressBar progressDialog;
    private TextView forgotPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        LoginBtn = (Button)findViewById(R.id.btnLogin);

        Info.setText("No of attempts remaining: "+counter);
        //Info.setText(counter);
        // Firebase instance
        //final FirebaseApp firebaseApp = FirebaseApp.initializeApp(this);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        
        //FirebaseUser user = firebaseAuth.getCurrentUser();

        // go to Menu



        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=Name.getText().toString().trim();
                String pass=Password.getText().toString().trim();
                validate(name,pass);
                return;


            }
        });
        // go to new User form


        // forgot Password recovery


    }
    // defining method for login

    public void validate(String userName, String userPassword) {
        if (userName.isEmpty())
        {
            Toast.makeText(Login.this, "Enter UserName Correctly!", Toast.LENGTH_LONG).show();
        }
        else if (userPassword.isEmpty())
        {
            Toast.makeText(Login.this, "Enter UserPassword Correctly!", Toast.LENGTH_LONG).show();

        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {

                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startService(new Intent(Login.this, TrackingService.class));

                    } else {
                        Toast.makeText(Login.this, "Login Failed\n Enter Correct Details\n User Doesn't Exist", Toast.LENGTH_SHORT).show();

                        counter--;
                        Info.setText("No of attempts remaining: " + counter);

                        if (counter == 0) {
                            LoginBtn.setEnabled(false);
                        }
                    }
                }
            });

        }

    }
}