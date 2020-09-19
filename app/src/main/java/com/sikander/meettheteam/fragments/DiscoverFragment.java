package com.sikander.meettheteam.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.activities.MemberActivity;
import com.sikander.meettheteam.adapter.MemberListAdapter;
import com.sikander.meettheteam.model.TeamMember;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private List <TeamMember>list = new ArrayList<TeamMember>();
    private RecyclerView recyclerView;
    private MemberListAdapter adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        adapter = new MemberListAdapter(list);
        recyclerView = view.findViewById(R.id.memberViewList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mDatabase= firebaseDatabase.getReference();
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item=recyclerView.getChildLayoutPosition(view);
                TeamMember teamMember=list.get(item);
                Intent intent=new Intent(getContext(), MemberActivity.class);
                intent.putExtra("object",teamMember);
                startActivity(intent);
            }
        });
        mDatabase.child("Team").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                 TeamMember object = postSnapshot.getValue(TeamMember.class);
                 if(object.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                     //nothing
                 }
                 else{
                     list.add(object);
                 }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}