package com.sikander.meettheteam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sikander.meettheteam.R;

public class RegisterActivity extends AppCompatActivity {

    Button next;
    Button back;
    EditText fname,lname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=findViewById(R.id.fName);
                lname=findViewById(R.id.lName);
                Intent intent=new Intent(RegisterActivity.this,RegisterTitleActivity.class);
                String fullName=fname.getText()+" "+lname.getText();
                intent.putExtra("full_name",fullName);
                startActivity(intent);
            }
        });
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}