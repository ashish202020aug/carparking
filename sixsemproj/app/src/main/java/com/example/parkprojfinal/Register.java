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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String userID;
    String eml, name, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName   = findViewById(R.id.fullName);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mPhone      = findViewById(R.id.phone);
        mRegisterBtn= findViewById(R.id.registerBtn);
        mLoginBtn   = findViewById(R.id.createText);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


       mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String user_email = mEmail.getText().toString().trim();
                    String user_password = mPassword.getText().toString().trim();
                    progressDialog.setMessage("Your details are uploading to our database.");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                sendUserData();
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this, Login.class));
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, "Email ID is already Registered",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }
    private Boolean validate(){
        Boolean result = false;
        name = mFullName.getText().toString();
        pass = mPassword.getText().toString();
        eml = mEmail.getText().toString();
        if(name.isEmpty() || pass.isEmpty() || eml.isEmpty()){
            Toast.makeText(this, "Please enter all the details.",Toast.LENGTH_SHORT).show();
            result = false;
        }
        else if(pass.length()<6) {
            Toast.makeText(this, "Password should have minimum 6 characters.",Toast.LENGTH_SHORT).show();
            result = false;
        }
        else {
            result = true;
        }
        return result;
    }

    private void sendUserData(){
        name = mFullName.getText().toString();
        eml = mEmail.getText().toString();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference("Account");
        UserProfile userProfile = new UserProfile(name,eml);
        myref.push().setValue(userProfile);

      //  myref.setValue(userProfile);
    }
}
