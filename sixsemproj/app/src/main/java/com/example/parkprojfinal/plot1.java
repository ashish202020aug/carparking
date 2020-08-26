package com.example.parkprojfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class plot1 extends AppCompatActivity {
    Button A1,A2,A3,A4,book;
    private TextView selection;
    private EditText etname;
    private String result="";
    private String place = "Gate1";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;

    UserBooking userBooking;
    StatusBooking statusBooking;
    private ArrayList<String> slotli
        t;
    private ArrayList<String> slotStatusList;
    private ArrayList<Boolean> statusList;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot1);
        progressDialog= new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        etname = (EditText)findViewById(R.id.editname);
        selection = (TextView)findViewById(R.id.tvselected);
        selection.setVisibility(View.INVISIBLE);
        book = (Button)findViewById(R.id.btnbook);

        userBooking = new UserBooking();
        slotlist = new ArrayList<>();
        statusBooking = new StatusBooking();
        slotStatusList = new ArrayList<>();
        statusList = new ArrayList<>();
        databaseReference1 = firebaseDatabase.getReference("lotdata");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dss: dataSnapshot.getChildren())
                {
                    statusBooking = dss.getValue(StatusBooking.class);
                    slotStatusList.add(statusBooking.getSlot());
                    statusList.add(statusBooking.isStatus());

                }
                    for (int k=0;k<slotStatusList.size();k++) {

                        String str = "btn" + slotStatusList.get(k);
                        int passID = getResources().getIdentifier(str, "id", getPackageName());
                        Button btn = (Button) findViewById(passID);
                        if (statusList.get(k)) {
                            btn.setBackgroundColor(getResources().getColor(R.color.colorred));
                            btn.setEnabled(false);
                        }


                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference = firebaseDatabase.getReference("Gate 1");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dss: dataSnapshot.getChildren())
                {
                    userBooking = dss.getValue(UserBooking.class);
                    slotlist.add(userBooking.getSlot());
                }
                int j= slotlist.size();
                //Log.d(j);
             //   for (int k=0;k<slotStatusList.size();k++){
                 for(int i=0; i<j;i++){
                            String str = "btn"+slotlist.get(i);
                            int passID = getResources().getIdentifier(str, "id", getPackageName());
                            Button btn = (Button)findViewById(passID);
                           // if(statusList.get(i)){
                                btn.setBackgroundColor(getResources().getColor(R.color.colorred));
                                btn.setEnabled(false);
                        //    }

                    //   }
                    //}

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

     //   databaseReference = firebaseDatabase.getReference("g1");


        A1 = (Button)findViewById(R.id.btnA1);
        A2 = (Button)findViewById(R.id.btnA2);
        A3 = (Button)findViewById(R.id.btnA3);
        A4 = (Button)findViewById(R.id.btnA4);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmmessage(result);
            }
        });
        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "A1";
                A1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                selection.setText("Selected: slot 1");
                selection.setVisibility(View.VISIBLE);
                A2.setEnabled(false);
                A3.setEnabled(false);
                A4.setEnabled(false);

            }
        });
        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "A2";
                A2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                selection.setText("Selected: A2");
                selection.setVisibility(View.VISIBLE);
                A1.setEnabled(false);
                A3.setEnabled(false);
                A4.setEnabled(false);
            }
        });
        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "A3";
                A3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                selection.setText("Selected: A3");
                selection.setVisibility(View.VISIBLE);
                A2.setEnabled(false);
                A1.setEnabled(false);
                A4.setEnabled(false);
            }
        });
        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "A4";
                A4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                selection.setText("Selected: A4");
                selection.setVisibility(View.VISIBLE);
                A2.setEnabled(false);
                A3.setEnabled(false);
            }
        });

    }
    private void confirmmessage(String x){
        name = etname.getText().toString().trim();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Important");
        builder.setMessage("Do you want to confirm "+x+" slot?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.setMessage("Please wait. Your slot is blooking.");
                progressDialog.show();
                entrytodatabase(name, result, place);
                startActivity(new Intent(plot1.this,booked.class));
                progressDialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(plot1.this, MainActivity.class));
            }
        });
        if(x.equals(""))
        {
            Toast.makeText(this,"Please select the slot.",Toast.LENGTH_SHORT).show();
        }
        else if(name.isEmpty())
        {
            Toast.makeText(this,"Please enter your name.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            builder.show();
        }
    }
    private void entrytodatabase(String name, String slot,String place){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference("Booking");
        DatabaseReference myref2 = firebaseDatabase.getReference("Gate 1");
       // DatabaseReference myref3 = firebaseDatabase.getReference("g1");
        DatabaseReference personalref = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserBooking userBooking = new UserBooking(slot, place);
        myref.child(name).setValue(userBooking);
        myref2.child(name).setValue(userBooking);
        personalref.child(name).setValue(userBooking);
    }
}
