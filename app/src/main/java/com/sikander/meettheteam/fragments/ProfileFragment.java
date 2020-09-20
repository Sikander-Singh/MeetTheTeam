package com.sikander.meettheteam.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.activities.EditActivity;
import com.sikander.meettheteam.activities.MainActivity;
import com.sikander.meettheteam.activities.ViewImageActivity;
import com.sikander.meettheteam.model.TeamMember;
import com.squareup.picasso.Picasso;

import static com.sikander.meettheteam.R.menu.edit;

public class ProfileFragment extends Fragment {
    private ImageView memberImage;
    private TextView userName,userPosition,userPersonality,userInterest,userDatePref;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private  TeamMember object;
    private CircularProgressDrawable circularProgressDrawable;
    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circularProgressDrawable = new CircularProgressDrawable(getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
       inflater.inflate(edit,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile_edit) {
            Intent intent=new Intent(getContext(), EditActivity.class);
            intent.putExtra("object",object);
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        userName=view.findViewById(R.id.memberName);
        userPosition=view.findViewById(R.id.memberPosition);
        userPersonality=view.findViewById(R.id.memberIntro);
        userInterest=view.findViewById(R.id.memberInterest);
        userDatePref=view.findViewById(R.id.memberDatePref);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        databaseReference.child("Team").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    object = snapshot.getValue(TeamMember.class);
                    userName.setText(object.getName());
                    userPosition.setText(object.getPosition());
                    userPersonality.setText(object.getPersonality());
                    userInterest.setText(object.getInterests());
                    userDatePref.setText(object.getDating_preferences());
                Picasso.get()
                        .load(String.valueOf(object.getProfile_image()))
                        .placeholder(circularProgressDrawable)
                        .error(R.drawable.nophoto)
                        .into(memberImage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        memberImage=view.findViewById(R.id.memberImage);
        memberImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getContext(), ViewImageActivity.class);
                        intent.putExtra("url",object.getProfile_image());
                        startActivity(intent);
                    }
                });
            }
        });
        setHasOptionsMenu(true);
        return view;
    }
}