package com.sikander.meettheteam.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.adapter.ChatListAdapter;
import com.sikander.meettheteam.model.TeamMember;
import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private ImageView chat;
    private ImageView profile;
    private RecyclerView recyclerView;
    private List<TeamMember> list=new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private ChatListAdapter chatListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        chatListAdapter=new ChatListAdapter(getContext(),list);
        recyclerView=view.findViewById(R.id.list_of_user);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(chatListAdapter);
        mLayoutManager.setItemPrefetchEnabled(false);
        databaseReference.child("Team").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    TeamMember teamMember=snapshot.getValue(TeamMember.class);
                    if(teamMember.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        //nothing
                    }
                    else {
                        list.add(teamMember);
                    }
                    chatListAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
              //nothing
            }
        });
        return view;
    }
}