package com.sikander.meettheteam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sikander.meettheteam.R;

public class MemberActivity extends AppCompatActivity {
    Button back;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        back=findViewById(R.id.back);
        imageView=findViewById(R.id.memberImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MemberActivity.this,ViewImageActivity.class);
                startActivity(intent);
            }
        });
    }
}