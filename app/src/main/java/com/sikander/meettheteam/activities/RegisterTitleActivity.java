package com.sikander.meettheteam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sikander.meettheteam.R;

public class RegisterTitleActivity extends AppCompatActivity {
    private Button next;
    private Button back;
    private EditText position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_title);
        next=findViewById(R.id.next);
        position=findViewById(R.id.memberPosition);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position.getText().toString().isEmpty()){
                    Toast.makeText(RegisterTitleActivity.this, "Please fill the empty fields",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(RegisterTitleActivity.this,RegisterFinalActivity.class);
                    String title=position.getText().toString();
                    intent.putExtra("full_name",getIntent().getStringExtra("full_name"));
                    intent.putExtra("title",title);
                    startActivity(intent);
                }
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