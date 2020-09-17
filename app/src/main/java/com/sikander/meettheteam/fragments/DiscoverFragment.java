package com.sikander.meettheteam.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sikander.meettheteam.R;
import com.sikander.meettheteam.activities.MainActivity;
import com.sikander.meettheteam.activities.MemberActivity;
import com.sikander.meettheteam.activities.RegisterActivity;
import com.sikander.meettheteam.adapter.MemberListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    List list = new ArrayList();
    RecyclerView recyclerView;
    MemberListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        recyclerView = view.findViewById(R.id.memberViewList);
        adapter = new MemberListAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), MemberActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}