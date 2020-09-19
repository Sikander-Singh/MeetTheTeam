package com.sikander.meettheteam.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.sikander.meettheteam.R;

public class RegisterFinalActivity extends AppCompatActivity {
    private Button back;
    private Button next;
    private EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_final);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next=findViewById(R.id.next);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                    Toast.makeText(RegisterFinalActivity.this, "Please fill the empty fields",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    String emailStr=email.getText().toString();
                    String passwordStr=password.getText().toString();
                    Intent intent=new Intent(RegisterFinalActivity.this,VerificationActivity.class);
                    intent.putExtra("email",emailStr);
                    intent.putExtra("password",passwordStr);
                    intent.putExtra("full_name",getIntent().getStringExtra("full_name"));
                    intent.putExtra("title",getIntent().getStringExtra("title"));
                    startActivity(intent);
                }
            }
        });
    }
}