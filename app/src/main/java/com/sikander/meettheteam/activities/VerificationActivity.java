package com.sikander.meettheteam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sikander.meettheteam.R;

public class VerificationActivity extends AppCompatActivity {

     Button resend,next;
     private String email,password,name,position;
     private FirebaseAuth mAuth;
     private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mAuth=mAuth = FirebaseAuth.getInstance();
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        name=getIntent().getStringExtra("full_name");
        position=getIntent().getStringExtra("title");
        resend=findViewById(R.id.resend);
        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getCurrentUser().reload();
                if(mAuth.getCurrentUser().isEmailVerified()){
                    Intent intent=new Intent(VerificationActivity.this,VerifiedActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(VerificationActivity.this, "Please first verify the email.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerification();
            }
        });
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendVerification();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(email, "signInWithEmail:failure", task.getException());
                            Toast.makeText(VerificationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
    @Override
    public void onBackPressed() {
     //nothing
    }
    private void sendVerification(){
        user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        // Re-enable button
                        if (task.isSuccessful()) {
                            Toast.makeText(VerificationActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(VerificationActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}