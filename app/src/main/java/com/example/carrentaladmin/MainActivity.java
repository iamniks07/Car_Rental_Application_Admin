package com.example.carrentaladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private TextView forgotPass;
    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        clickListenHandler();
    }

    private void initComponents() {
        login = findViewById(R.id.login);

        forgotPass = findViewById(R.id.forgot_password);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    private void clickListenHandler() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateemail() | !validatepass()){
                    return;
                }
                else{
                    isUser(view);
                }
            }
        });
    }

    private void isUser(View view)
    {
        final String enterUsername = username.getText().toString().trim();
        final String enterPass = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query checkUser = reference.orderByChild("username").equalTo(enterUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String pass = snapshot.child(enterUsername).child("password").getValue().toString();

                    if(pass.equals(enterPass))
                    {
                        String name = snapshot.child(enterUsername).child("name").getValue().toString();
                        String email = snapshot.child(enterUsername).child("email").getValue().toString();
                        String number = snapshot.child(enterUsername).child("contact").getValue().toString();

                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("email",email);
                        intent.putExtra("number",number);
                        startActivity(intent);
//                        Toast.makeText(MainActivity.this, "Password Match", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this, pass, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "InValid Username", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private Boolean validateemail(){
        String val = username.getText().toString();
        if(val.isEmpty()){
            username.setError("field cannot be empty");
            return false;
        }
        else{
            username.setError(null);
            return true;
        }
    }

    private Boolean validatepass(){
        String val = password.getText().toString();
        if(val.isEmpty()){
            password.setError("field cannot be empty");
            return false;
        }
        else{
            password.setError(null);
            return true;
        }
    }
}