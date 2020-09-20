package com.sikander.meettheteam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.adapter.MessageAdapter;
import com.sikander.meettheteam.model.MessageClass;
import com.sikander.meettheteam.model.TeamMember;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private List<MessageClass> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private MessageAdapter messageAdapter;
    private String memberId;
    private String memberName;
    private TextView textViewMemberName;
    private ListView listView;
    private String myName;
    private EditText input;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        memberId = getIntent().getStringExtra("memberId");
        memberName = getIntent().getStringExtra("memberName");
        textViewMemberName = findViewById(R.id.memberName);
        textViewMemberName.setText(memberName);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        messageAdapter = new MessageAdapter(list);
        recyclerView = findViewById(R.id.list_of_messages);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(messageAdapter);
        mLayoutManager.setItemPrefetchEnabled(false);
        ref.child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessageClass chatMessage = snapshot.getValue(MessageClass.class);
                    if (chatMessage.getMessageId()==null) {
                        //nothing
                    } else {
                        if (chatMessage.getMessageId().contains(memberId + FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            list.add(chatMessage);
                            messageAdapter.notifyDataSetChanged();
                        } else if (chatMessage.getMessageId().contains(FirebaseAuth.getInstance().getCurrentUser().getUid() + memberId)) {
                            list.add(chatMessage);
                            messageAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Server error! Unable to get messages.", Toast.LENGTH_SHORT).show();
            }
        });
        ref.child("Team").orderByChild("userId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    TeamMember userClass = snapshot.getValue(TeamMember.class);

                    myName = userClass.getName();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.input);
                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Messages")
                        .push()
                        .setValue(new MessageClass(memberId + FirebaseAuth.getInstance().getCurrentUser().getUid(),input.getText().toString(),myName)
                        );
                // Clear the input
                input.setText("");
            }
        });
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}