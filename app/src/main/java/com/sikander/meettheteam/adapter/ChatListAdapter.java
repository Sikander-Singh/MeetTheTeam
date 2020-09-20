package com.sikander.meettheteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.activities.ChatActivity;
import com.sikander.meettheteam.model.TeamMember;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyHolder>  {
    private Context context;
    private List<TeamMember> list;
    private CircularProgressDrawable circularProgressDrawable;
    public ChatListAdapter(Context context, List<TeamMember> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_member, parent, false);
        return new ChatListAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.MyHolder holder, int position) {
        final TeamMember teamMember=list.get(position);
        holder.memberMessage.setText(teamMember.getName());
        holder.memberMessage.setText("Tap to pen");
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Picasso.get()
                .load(teamMember.getProfile_image())
                .placeholder(circularProgressDrawable)
                .error(R.drawable.nophoto)
                .into(holder.memberImage);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screen=new Intent(context, ChatActivity.class);
                screen.putExtra("userName",teamMember.getName());
                screen.putExtra("userId",teamMember.getId());
                context.startActivity(screen);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView memberName,memberMessage;
        ImageView memberImage;
        RelativeLayout layout;
        public MyHolder(View itemView) {
            super(itemView);
            memberName =itemView.findViewById(R.id.memberName);
            memberMessage=itemView.findViewById(R.id.memberMessage);
            memberImage=itemView.findViewById(R.id.memberMessage);
            layout=itemView.findViewById(R.id.member_list_layout);
        }
    }



}
