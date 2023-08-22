package com.example.carrentaladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    int bcount,catcount,vehcount,usercount=0;
    TextView name,email,number;
//    CardView car;
    TextView payment, booking, category, cars, user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initComponents();
        displayComponents();
        retrieveData();


    }

    public void viewBooking(View view){
        Intent intent = new Intent(getApplicationContext(),ViewBookingActivity.class);
        startActivity(intent);
    }

    public void addCar(View view){
        Intent intent = new Intent(getApplicationContext(),AddVehicleActivity.class);
        startActivity(intent);
    }

    public void addCategory(View view){
        Intent intent = new Intent(getApplicationContext(),AddVehicleCategoryActivity.class);
        startActivity(intent);
    }

    @SuppressLint("WrongViewCast")
    private void initComponents() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        number = findViewById(R.id.number);

//        car = findViewById(R.id.cars);

        payment = findViewById(R.id.payment_label);
        booking = findViewById(R.id.booking_label);
        category = findViewById(R.id.category_label);
        cars = findViewById(R.id.vehicle_label);
        user = findViewById(R.id.user_label);

    }

    private void retrieveData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    int i = 1;
                    usercount += i;

                    user.setText(String.valueOf(usercount));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Category");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot ds : snapshot.getChildren())
                {
                    int i = 1 ;
                    catcount += i;

                    category.setText(String.valueOf(catcount));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Booking");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    int i = 1;
                    bcount += i;

                    booking.setText(String.valueOf(bcount));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Vehicle");
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    int i = 1;
                    vehcount += i;

                    cars.setText(String.valueOf(vehcount));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void displayComponents() {
        name.setText(getIntent().getStringExtra("name"));
        email.setText(getIntent().getStringExtra("email"));
        number.setText(getIntent().getStringExtra("number"));

//        payment.setText("0");
//        booking.setText("0");
//        category.setText("0");
//        cars.setText("0");
//        user.setText("0");

    }


}