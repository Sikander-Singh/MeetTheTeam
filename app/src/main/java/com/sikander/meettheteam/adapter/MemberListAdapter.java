package com.sikander.meettheteam.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sikander.meettheteam.R;
import java.util.List;

import static com.sikander.meettheteam.R.drawable.*;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MyViewHolder>{

    //private List<JobClass> jobList;
    private Context c;
    private View.OnClickListener mClickListener;
    public MemberListAdapter(List jobList){
        //this.jobList=jobList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_card, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       // final JobClass job=jobList.get(position);
      //  holder.jobName.setText(job.getJobName());
        holder.memberName.setText("Sikander");
        holder.memberPosition.setText("Android Engineer");
        holder.memberIntro.setText("This is sikander singh");
        holder.memberImage.setImageResource(sikander);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });
    }
    @Override
    public int getItemCount() {
       return 10;
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