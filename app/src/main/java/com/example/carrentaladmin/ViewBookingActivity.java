package com.example.carrentaladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ViewBookingActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private WaitingFragment waitingFragment;
    private ApprovedFragment approvedFragment;
    private RejectedFragment rejectedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        initComponents();
        setFragment(waitingFragment);

        clickListener();
    }

    private void initComponents() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        frameLayout = findViewById(R.id.framelayout);

        waitingFragment = new WaitingFragment();
        approvedFragment = new ApprovedFragment();
        rejectedFragment = new RejectedFragment();
    }

    private void clickListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_waiting:
                        setFragment(waitingFragment);
                        return true;

                    case R.id.nav_approved:
                        setFragment(approvedFragment);
                        return true;

                    case R.id.nav_rejected:
                        setFragment(rejectedFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).commit();
    }

}