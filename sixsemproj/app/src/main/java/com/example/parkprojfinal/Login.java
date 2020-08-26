package com.example.parkprojfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkprojfinal.R;
import com.example.parkprojfinal.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn,btg;
    TextView mCreateBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String emai, pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginBtn);
        btg = findViewById(R.id.btg);
        mCreateBtn = findViewById(R.id.createText);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(Login.this, MainActivity.class));
        }
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    emai = mEmail.getText().toString();
                    pas = mPassword.getText().toString();
                    progressDialog.setMessage("Please wait for a while...");
                    progressDialog.show();
                    if (emai.equals("admin") && pas.equals("admin")) {
                        progressDialog.dismiss();
                        Toast.makeText(Login.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, admin.class));
                    } else {
                        firebaseAuth.signInWithEmailAndPassword(emai, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                } else {
                                    Toast.makeText(Login.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }

                        });
                    }
                }

            }

        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
        btg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }

        });



    }
    private Boolean validate(){
        Boolean result = false;
        String emai = mEmail.getText().toString();
        String pas = mPassword.getText().toString();
        if(emai.isEmpty() || pas.isEmpty()){
            Toast.makeText(this, "Please enter all the details.",Toast.LENGTH_SHORT).show();
            result = false;
        }else {
            result = true;
        }
        return result;
    }
}
