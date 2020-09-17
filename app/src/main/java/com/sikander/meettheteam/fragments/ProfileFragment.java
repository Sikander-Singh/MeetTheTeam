package com.sikander.meettheteam.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.activities.EditActivity;
import com.sikander.meettheteam.activities.HomeActivity;
import com.sikander.meettheteam.activities.MainActivity;
import com.sikander.meettheteam.activities.MemberActivity;
import com.sikander.meettheteam.activities.ViewImageActivity;

import java.util.zip.Inflater;

import static com.sikander.meettheteam.R.menu.edit;

public class ProfileFragment extends Fragment {
    private String mParam1;
    private String mParam2;

    ImageView imageView;
    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
       inflater.inflate(edit,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile_edit) {
            Intent intent=new Intent(getContext(), EditActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.logout){

            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(getContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        imageView=view.findViewById(R.id.memberImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getContext(), ViewImageActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        setHasOptionsMenu(true);
        return view;
    }
}