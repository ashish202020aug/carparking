package com.example.parkprojfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parkprojfinal.R;
import com.example.parkprojfinal.UserBooking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class admin extends AppCompatActivity {
    private ListView listView;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;

    ArrayList<String> list;
    ArrayList<String> statuslist;
    ArrayList<String> finalList;


   ArrayAdapter<String> adapter;

    UserBooking userBooking;
    StatusBooking statusBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        userBooking = new UserBooking();
        listView = (ListView)findViewById(R.id.listview);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Booking");
        databaseReference1 = firebaseDatabase.getReference("lotdata");

        list = new ArrayList<>();
        statuslist = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfoName, list);
     //   adapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfoName, statuslist);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dss: dataSnapshot.getChildren())
                {
                    String name = dss.getKey().toString();
                    statusBooking = dss.getValue(StatusBooking.class);
if(statusBooking.isStatus()) {
    statuslist.add( "\nPlace: " + "Gate 1" + " | Slot: " + statusBooking.getSlot());
}
               }
                list.addAll(statuslist);
              //  listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(admin.this,"No bookings.",Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dss: dataSnapshot.getChildren())
                {
                    String name = dss.getKey().toString();
                    userBooking = dss.getValue(UserBooking.class);

                    list.add("Name: "+ name +"\nPlace: "+ userBooking.getPlace() +" | Slot: "+ userBooking.getSlot());
                }
               // finalList.addAll(list);
                listView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(admin.this,"No bookings.",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
