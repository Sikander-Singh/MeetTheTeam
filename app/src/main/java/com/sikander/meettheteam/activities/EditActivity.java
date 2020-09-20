package com.sikander.meettheteam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sikander.meettheteam.R;
import com.sikander.meettheteam.model.TeamMember;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {
    private Button back;
    private Button update;
    private EditText userName,userPosition,userPersonality,userInterest,userDatePref;
    private ImageView profileImage;
    private TeamMember teamMember;
    private int PICK_IMAGE_REQUEST = 111;
    private Uri filePath;
    private ProgressDialog pd;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef ;
    private StorageReference downloadRef;
    private  DatabaseReference databaseReference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");
        storageRef= storage.getReferenceFromUrl(getString(R.string.storagePath));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userName = findViewById(R.id.memberName);
        userPosition = findViewById(R.id.memberPosition);
        userPersonality = findViewById(R.id.memberIntro);
        userInterest = findViewById(R.id.memberInterest);
        userDatePref = findViewById(R.id.memberDatePref);
        profileImage = findViewById(R.id.profileImage);
        teamMember = (TeamMember) getIntent().getSerializableExtra("object");
        userName.setText(teamMember.getName());
        userPosition.setText(teamMember.getPosition());
        userPersonality.setText(teamMember.getPersonality());
        userInterest.setText(teamMember.getInterests());
        userDatePref.setText(teamMember.getDating_preferences());
       //profile image
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(EditActivity.this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Picasso.get()
                .load(String.valueOf(teamMember.getProfile_image()))
                .placeholder(circularProgressDrawable)
                .error(R.drawable.nophoto)
                .into(profileImage);
        //profile image
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //update
        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString().isEmpty() ||
                        userDatePref.getText().toString().isEmpty() ||
                        userInterest.getText().toString().isEmpty() ||
                        userPersonality.getText().toString().isEmpty() ||
                        userPosition.getText().toString().isEmpty()){
                    Toast.makeText(EditActivity.this, "Please fill the empty fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    TeamMember teamObject = new TeamMember(id, userName.getText().toString(),
                            userDatePref.getText().toString(),
                            userInterest.getText().toString(),
                            userPersonality.getText().toString(),
                            userPosition.getText().toString(),teamMember.getProfile_image()
                    );
                    databaseReference.child("Team").child(id).setValue(teamObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(EditActivity.this, "Profile updated",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });
        //update
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting image to ImageView
                profileImage.setImageBitmap(bitmap);
                UploadImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void UploadImage() throws IOException {
        if(filePath != null) {
            pd.show();

            Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            byte[] data = baos.toByteArray();
             StorageReference childRef=storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            //uploading the image
            UploadTask uploadTask = childRef.putBytes(data);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    Toast.makeText(EditActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    downloadRef= storage.getReferenceFromUrl(getString(R.string.storagePath)+teamMember.getId());
                    downloadRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            teamMember.setProfile_image(task.getResult().toString());
                            databaseReference.child("Team").child(teamMember.getId()).child("profile_image").setValue(task.getResult().toString());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(EditActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(EditActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
        }
    }
}