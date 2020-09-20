package com.sikander.meettheteam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.model.MessageClass;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private List<MessageClass> chatList;
    private Context context;
    public MessageAdapter(List<MessageClass> chatList) {
        this.chatList = chatList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageClass chatMessage=chatList.get(position);
        holder.memberName.setText(chatMessage.getMessageUser());
        holder.memberMessage.setText(chatMessage.getMessageText());
        holder.memberMessageTime.setText(String.valueOf(chatMessage.getMessageTime()));
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {
        TextView memberName,memberMessage,memberMessageTime;
        public MyViewHolder(View itemView) {
            super(itemView);
            memberName=itemView.findViewById(R.id.memberName);
            memberMessage=itemView.findViewById(R.id.memberMessage);
            memberMessageTime=itemView.findViewById(R.id.memberMessageTime);

        }
    }
}
