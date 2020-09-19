package com.sikander.meettheteam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.model.TeamMember;
import com.squareup.picasso.Picasso;

public class MemberActivity extends AppCompatActivity {
    private Button back;
    private TeamMember teamMember;
    private TextView memberName,memberPosition,memberInto,memberInterest,memberDatingPref;
    private ImageView memberImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        back=findViewById(R.id.back);
        memberImage=findViewById(R.id.memberImage);
        memberName=findViewById(R.id.memberName);
        memberPosition=findViewById(R.id.memberPosition);
        memberInto=findViewById(R.id.memberIntro);
        memberInterest=findViewById(R.id.memberInterest);
        memberDatingPref=findViewById(R.id.memberDatePref);
        teamMember= (TeamMember) getIntent().getSerializableExtra("object");
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(MemberActivity.this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Picasso.get()
                .load(teamMember.getProfile_image())
                .placeholder(circularProgressDrawable)
                .error(R.drawable.nophoto)
                .into(memberImage);
        memberName.setText(teamMember.getName());
        memberPosition.setText(teamMember.getPosition());
        memberInto.setText(teamMember.getPersonality());
        memberInterest.setText(teamMember.getInterests());
        memberDatingPref.setText(teamMember.getDating_preferences());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        memberImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MemberActivity.this,ViewImageActivity.class);
                intent.putExtra("url",teamMember.getProfile_image());
                startActivity(intent);
            }
        });
    }
}