package com.sikander.meettheteam.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.model.TeamMember;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MyViewHolder>{

    private List<TeamMember> teamList;
    private Context c;
    private View.OnClickListener mClickListener;
    public MemberListAdapter(List<TeamMember> list){
        this.teamList=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_card, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       final TeamMember member=teamList.get(position);
        holder.memberName.setText(member.getName());
        holder.memberPosition.setText(member.getPosition());
        holder.memberIntro.setText(member.getPersonality());
       /* AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = (InputStream) new URL(member.getProfile_image()).getContent();
                    Drawable d = Drawable.createFromStream(is, "image");
                    holder.memberImage.setImageDrawable(d);
                } catch (Exception e) {
                    System.out.println("Image download error"+e);
                }
            }
        });*/
        Picasso.get()
                .load(member.getProfile_image())
                .placeholder(R.drawable.nophoto)
                .error(R.drawable.nophoto)
                .into(holder.memberImage);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClickListener.onClick(view);
            }
        });
    }
    @Override
    public int getItemCount() {
       return teamList.size() ;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView memberName,memberPosition,memberIntro;
        public ImageView memberImage;
        RelativeLayout layout;
        public MyViewHolder(View itemView) {
            super(itemView);
            c=itemView.getContext();
            memberName=itemView.findViewById(R.id.memberName);
            memberPosition=itemView.findViewById(R.id.memberPosition);
            memberIntro=itemView.findViewById(R.id.memberIntro);
            memberImage=itemView.findViewById(R.id.memberImage);
            layout=itemView.findViewById(R.id.layout_member_row);
        }
    }
    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }
}