package com.sikander.meettheteam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.model.TeamMember;

public class VerifiedActivity extends AppCompatActivity {

    private Button next;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);
        next=findViewById(R.id.next);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        TeamMember teamMember= (TeamMember) getIntent().getSerializableExtra("object");
        teamMember.setId(auth.getCurrentUser().getUid());
        teamMember.setProfile_image(getString(R.string.defaultImage));
        databaseReference.child("Team").child(auth.getCurrentUser().getUid()).setValue(teamMember).isComplete();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VerifiedActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}