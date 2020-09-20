package com.sikander.meettheteam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.model.TeamMember;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URL;

public class VerifiedActivity extends AppCompatActivity {
    private Button next;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageRef;
    private TeamMember teamMember;
    private  FirebaseAuth auth;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);
        next=findViewById(R.id.next);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        auth=FirebaseAuth.getInstance();
        teamMember= (TeamMember) getIntent().getSerializableExtra("object");
        teamMember.setId(auth.getCurrentUser().getUid());

        //upload default profile picture of user
        storageRef= FirebaseStorage.getInstance().getReferenceFromUrl(getString(R.string.storagePath)+teamMember.getId());
        Bitmap bm = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.nophoto);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask=storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              finish();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        teamMember.setProfile_image(task.getResult().toString());
                        databaseReference.child("Team").child(auth.getCurrentUser().getUid()).setValue(teamMember).isComplete();
                    }
                });
            }
        });
        //upload default profile picture of user

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VerifiedActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}