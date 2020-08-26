package com.example.parkprojfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth firebaseAuth;
    Button p1,p2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        p1 = findViewById(R.id.bt1);
        p2 = findViewById(R.id.bt2);

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), plot1.class));
            }
        });
     p2.setOnClickListener(new View.OnClickListener() {
        @Override
          public void onClick(View v) {

            startActivity(new Intent(getApplicationContext(), plot2.class));

            }
        });
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.bookedmenu:{
                    startActivity(new Intent(MainActivity.this, booked.class));
                    return true;
                }
                case R.id.aboutmenu: {
                    startActivity(new Intent(MainActivity.this, about.class));
                    return true;
                }
                case R.id.logoutmenu:{
                    logoutfunct();
                }
                default:{
                    return super.onOptionsItemSelected(item);
                }
            }
        }
        private void logoutfunct(){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this, Login.class));
    }
}

