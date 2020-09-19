package com.sikander.meettheteam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.model.GlideApp;
import com.squareup.picasso.Picasso;

public class ViewFirebaseImage extends AppCompatActivity {
    private Button back;
    private ImageView memberImage;
    private StorageReference storageRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_firebase_image);
        back=findViewById(R.id.back);
        memberImage=findViewById(R.id.viewMemberImage);
        //Image path reference from firebase
        storageRef= FirebaseStorage.getInstance().getReferenceFromUrl(getString(R.string.storagePath)+ FirebaseAuth.getInstance().getCurrentUser().getUid());
        //Image path reference from firebase
        //load image into the user profile
        GlideApp.with(ViewFirebaseImage.this)
                .load(storageRef)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(memberImage);
        //load image into the user profile
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
      }
}