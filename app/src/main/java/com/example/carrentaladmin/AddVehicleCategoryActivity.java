package com.example.carrentaladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import yuku.ambilwarna.AmbilWarnaDialog;

public class AddVehicleCategoryActivity extends AppCompatActivity {

    private EditText category;
    private EditText categoryID;
    private EditText imageURL;
    private Button colorDisplay;
    private Button add;
    private Button addVehicle;
    private Button loadImage;

    private ImageView viewImage;
    private int colorCode = -1;

    boolean i,j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_category);

        initComponent();
        listenHandler();
    }

    private void initComponent() {
        category = findViewById(R.id.category);
        categoryID = findViewById(R.id.categoryID);
        colorDisplay = findViewById(R.id.colorDisplay);
        imageURL = findViewById(R.id.imageURL);
        add = findViewById(R.id.add);
        addVehicle = findViewById(R.id.vehicle);
        loadImage = findViewById(R.id.loadImage);

        viewImage = findViewById(R.id.viewImage);
    }

    private void listenHandler() {
        colorDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorDialog();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCategory();
            }
        });

        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addVehiclePage = new Intent(AddVehicleCategoryActivity.this,AddVehicleActivity.class);
                startActivity(addVehiclePage);
            }
        });

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageURL.getText().toString().equals("")){
                    Picasso.get().load(imageURL.getText().toString()).into(viewImage);
                }
            }
        });
    }

    private void createCategory() {
        String categoryName = category.getText().toString();
        String category_ID = categoryID.getText().toString();
        String image_URL = imageURL.getText().toString();

        boolean valid = isValid(categoryName,category_ID,image_URL);

        if (valid)
        {
            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Category");
            VehicleCategory vehicleCategory = new VehicleCategory(categoryName,Integer.valueOf(category_ID),colorCode,image_URL);
        }
    }

    private boolean isValid(String categoryName, String category_ID, String image_URL)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Category");
        Query check = reference.orderByChild("id").equalTo(Integer.valueOf(category_ID));
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
//                    Toast.makeText(AddVehicleCategoryActivity.this, "Category ID Already Exist", Toast.LENGTH_SHORT).show();
//                    categoryID.setError("Already Exist");
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

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Category");
        Query check1 = reference1.orderByChild("category").equalTo(categoryName);
        check1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
//                    Toast.makeText(AddVehicleCategoryActivity.this, "Category Already Exist", Toast.LENGTH_SHORT).show();
//                    category.setError("Already Exist");
                    j = true;
                }
                else {
                    j = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(!category_ID.equals("") && !i && !j)
        {
            return true;
        }

        if(category_ID.equals(""))
            toast("CategoryID is blank");
        else if(categoryName.equals(""))
            toast("Category name is blank");
        else if(i)
            toast("CategoryID already exists");
        else if(image_URL.equals(""))
            toast("Please enter image URL");
        else
            toast("Category already exists");
        return false;
    }
    public void openColorDialog(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, colorCode, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                colorCode = color;
                colorDisplay.setBackgroundTintList(ColorStateList.valueOf(color));
                Toast.makeText(AddVehicleCategoryActivity.this, ""+color, Toast.LENGTH_SHORT).show();
            }
        });
        ambilWarnaDialog.show();
    }

    private void toast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_LONG);
        toast.show();
    }
}