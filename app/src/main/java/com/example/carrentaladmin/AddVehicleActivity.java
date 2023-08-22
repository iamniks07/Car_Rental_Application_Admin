package com.example.carrentaladmin;

import static android.os.Build.VERSION_CODES.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class AddVehicleActivity extends AppCompatActivity {

    private EditText category;
    private EditText seats;
    private EditText price;
    private EditText mileage;
    private EditText manufacturer;
    private EditText model;
    private EditText year;
    private EditText imageURL;
    private CheckBox availability;

    private Button add;
    private Button reset;
    private Button vehicleCategory;
    private Button viewResult;
    private Button load;

    boolean i;
    int Id;
    static int vehicleID;
    private ImageView vehicleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        initComponents();
        listenHandler();
    }

    private void initComponents() {

        category = findViewById(R.id.category);
        seats = findViewById(R.id.seats);
        price = findViewById(R.id.price);
        mileage = findViewById(R.id.mileage);
        manufacturer = findViewById(R.id.manufacturer);
        model = findViewById(R.id.model);
        year = findViewById(R.id.year);
        imageURL = findViewById(R.id.imageURL);
        availability = findViewById(R.id.availability);
        add = findViewById(R.id.add);
        vehicleCategory = findViewById(R.id.vehicleCategory);

        load = findViewById(R.id.load);
        vehicleImage = findViewById(R.id.viewVehicle);

    }

    private void listenHandler() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createVehicle();
            }
        });
        vehicleCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addVehicleCategoryPage = new Intent(AddVehicleActivity.this,AddVehicleCategoryActivity.class);
                startActivity(addVehicleCategoryPage);
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageURL.getText().toString().equals("")){
                    Picasso.get().load(imageURL.getText().toString()).into(vehicleImage);
                }
            }
        });
    }

    private void createVehicle(){

        String _category = category.getText().toString();
        String _seats = seats.getText().toString();
        String _price = price.getText().toString();
        String _mileage = mileage.getText().toString();
        String _manufacturer = manufacturer.getText().toString();
        String _model = model.getText().toString();
        String _year = year.getText().toString();
        String _imageURL = imageURL.getText().toString();
        int _availability;
        if(availability.isChecked()){
            _availability = 1;
        }
        else{
            _availability = 0;
        }

//        boolean _availability = availability.isChecked();

        boolean valid = isValid(_category,_seats,_price,_mileage,_manufacturer,_model,_year,_imageURL);

        vehicleID = generateID(200,300);

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Vehicle");
        Query check = reference2.orderByChild("id").equalTo(vehicleID);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Id = generateID(200,300);
                    vehicleID = Id;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        if(valid){
            DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("Vehicle");
            Vehicle vehicle = new Vehicle(
                    vehicleID,
                    Integer.valueOf(_price),
                    Integer.valueOf(_seats),
                    Integer.valueOf(_mileage),
                    _manufacturer,
                    _model,
                    Integer.valueOf(_year),
                    _category,
                    _availability,
                    _imageURL);

            reference3.child(String.valueOf(vehicleID)).setValue(vehicle);
            Toast.makeText(this, "Vehicle Added Successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
            startActivity(intent);

        }
    }

    private boolean isValid(String category, String seats, String price, String mileage, String manufacturer, String model, String year, String imageURL) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Category");
        Query checkVehicle=reference.orderByChild("category").equalTo(category);
        checkVehicle.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(AddVehicleActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    i = true;
                }
                else {
                    i = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(i==false){
            toast(String.valueOf(i));
            return false;
        }
        else if(category.equals("")){
            toast("Category is blank");
            return false;
        }
        else if(seats.equals("")){
            toast("Seats is blank");
            return false;
        }
        else if(price.equals("")){
            toast("Price is blank");
            return false;
        }
        else if(mileage.equals("")){
            toast("Mileage is blank");
            return false;
        }
        else if(manufacturer.equals("")){
            toast("Manufacturer is blank");
            return false;
        }
        else if(model.equals("")){
            toast("Model is blank");
            return false;
        }
        else if(year.equals("")){
            toast("Year is blank");
            return false;
        }
        else if(imageURL.equals("")){
            toast("ImageURL is blank");
        }
        return true;
    }

    private void toast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_LONG);
        toast.show();
    }

    private int generateID(int start,int end){
        Random rnd = new Random();
        int id = rnd.nextInt(end-start)+start;
//        String Id = "V" + id;
        return id;
    }
}