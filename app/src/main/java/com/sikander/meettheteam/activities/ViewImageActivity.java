package com.sikander.meettheteam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.sikander.meettheteam.R;
import com.squareup.picasso.Picasso;

public class ViewImageActivity extends AppCompatActivity {
    private Button back;
    private ImageView memberImage;
    private CircularProgressDrawable circularProgressDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        back=findViewById(R.id.back);
        memberImage=findViewById(R.id.viewMemberImage);
        circularProgressDrawable = new CircularProgressDrawable(ViewImageActivity.this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Picasso.get()
                .load(getIntent().getStringExtra("url"))
                .placeholder(circularProgressDrawable)
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