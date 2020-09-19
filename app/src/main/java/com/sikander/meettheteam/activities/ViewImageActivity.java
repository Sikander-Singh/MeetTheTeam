package com.sikander.meettheteam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sikander.meettheteam.R;
import com.squareup.picasso.Picasso;

public class ViewImageActivity extends AppCompatActivity {

    private Button back;
    private ImageView memberImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        back=findViewById(R.id.back);
        memberImage=findViewById(R.id.viewMemberImage);
        Picasso.get()
                .load(getIntent().getStringExtra("url"))
                .placeholder(R.drawable.nophoto)
                .error(R.drawable.nophoto)
                .into(memberImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}