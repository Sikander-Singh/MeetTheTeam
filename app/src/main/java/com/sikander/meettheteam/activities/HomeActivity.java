package com.sikander.meettheteam.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.fragments.ChatFragment;
import com.sikander.meettheteam.fragments.DiscoverFragment;
import com.sikander.meettheteam.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private Toolbar toolbar;
    private TextView textView;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.title);
        setSupportActionBar(toolbar);
        loadFragment(new DiscoverFragment());
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.teamMate) {
                    textView.setText(R.string.teamMate);
                    loadFragment(new DiscoverFragment());
                    return true;
                } else if(id == R.id.profile) {
                    textView.setText(R.string.myProfile);
                    loadFragment(new ProfileFragment());
                    return true;
                } else if(id == R.id.chat) {
                    textView.setText(R.string.chat);
                    loadFragment(new ChatFragment());
                    return true;
                }
                return true;
            }

        });


    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

    }
}