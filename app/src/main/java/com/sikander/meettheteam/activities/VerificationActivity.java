package com.sikander.meettheteam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.model.TeamMember;

public class VerificationActivity extends AppCompatActivity {
     private Button resend,next;
     private TextView textView;
     private String email,password,name,position;
     private FirebaseAuth mAuth;
     private FirebaseUser user;
     private TeamMember teamMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mAuth=mAuth = FirebaseAuth.getInstance();
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        name=getIntent().getStringExtra("full_name");
        position=getIntent().getStringExtra("title");
        teamMember=new TeamMember();
        teamMember.setName(name);
        teamMember.setPosition(position);
        resend=findViewById(R.id.resend);
        next=findViewById(R.id.next);
        textView=findViewById(R.id.verifiedText);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(mAuth.getCurrentUser().isEmailVerified()){
                            Intent intent=new Intent(VerificationActivity.this,VerifiedActivity.class);
                            intent.putExtra("object",teamMember);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(VerificationActivity.this, "Please first verify the email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerification(user);
            }
        });

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user=mAuth.getCurrentUser();
                            sendVerification(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(email, "signInWithEmail:failure", task.getException());
                            resend.setVisibility(View.GONE);
                            textView.setText("Sorry ! Failed to create a account or Might be your email already exits.");
                            next.setVisibility(View.GONE);
                            Toast.makeText(VerificationActivity.this, "Your email is already exits ! Try to login",
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
    private void sendVerification(final FirebaseUser user){
        user.sendEmailVerification().addOnCompleteListener(VerificationActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
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