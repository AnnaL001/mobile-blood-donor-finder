package edu.strathmore.lnyangon.blood_donor_finder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ProfileUpdateActivity extends AppCompatActivity {
    // Declaration of variables
    private EditText profile_name, profile_phone;
    private Button profile_update;
    private Spinner profile_rtype;
    private ImageView profile_img;

    private ProgressDialog progress;
    private Uri photoUri;


    private String recepient_id;
    private String recepient_name;
    private String recepient_phone;
    private String recepient_rtype;
    private String recepient_profileimg;

    private FirebaseAuth mAuth;
    private DatabaseReference current_recepient_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        //Layout variables
        profile_name = (EditText) findViewById(R.id.profile_name);
        profile_phone = (EditText) findViewById(R.id.profile_phone);
        profile_rtype = (Spinner) findViewById(R.id.profile_rtype);
        profile_img = (ImageView) findViewById(R.id.profile_img);
        profile_update = (Button) findViewById(R.id.btn_profile_update);

        //Firebase variables
        mAuth = FirebaseAuth.getInstance();
        recepient_id = mAuth.getCurrentUser().getUid();

        //Database reference to get information on logged in recepient
        current_recepient_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Recepient").child(recepient_id);

        //ProgressDialog
        progress = new ProgressDialog(this);
        progress.setMessage("Thanks for waiting while loading...");
        progress.setCancelable(false);
        progress.show();
        getRecepientInfo();

        //OnClickListener set on profile image to capture profile image
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        //OnClickListener set on update button to save all profile details input by user
        profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setMessage("Thanks for waiting while loading...");
                progress.setCancelable(false);
                progress.show();
                saveRecepientInfo();
            }
        });

        return;

    }

    // Method getRecepientInfo() gets information on recepient from the firebase database
    // By using addValueEventListener, the method gets user information from the database reference
    @SuppressWarnings("unchecked")
    private void getRecepientInfo(){
        current_recepient_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                   // Map data structure to hold values from the db reference
                    // and add them to the specified edittext fields
                    Map<String,Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("recepient_name") != null){
                        recepient_name = map.get("recepient_name").toString();
                        profile_name.setText(recepient_name);
                    }

                    if(map.get("recepient_phone") != null){
                        recepient_phone = map.get("recepient_phone").toString();
                        profile_phone.setText(recepient_phone);
                    }


                    //if(map.get("recepient_rtype") != null){
                        //recepient_rtype = map.get("recepient_rtype").toString();

                      //  profile_rtype.setSelection(((ArrayAdapter<String>)profile_rtype.getAdapter()).getPosition(recepient_rtype));
                   // }

                    if(map.get("recepient_profileimg") != null){
                        recepient_profileimg = map.get("recepient_profileimg").toString();
                        //Loading of image using Glide library
                        Glide.with(ProfileUpdateActivity.this).load(recepient_profileimg).into(profile_img);
                    }

                    progress.dismiss();
                    Toast.makeText(ProfileUpdateActivity.this, "Profile details loaded",Toast.LENGTH_SHORT).show();
                }

                progress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                 Toast.makeText(ProfileUpdateActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }


        });
    }
     @SuppressWarnings("unchecked")
    private void saveRecepientInfo(){
        recepient_name = profile_name.getText().toString();
        recepient_phone = profile_phone.getText().toString();
        recepient_rtype = profile_rtype.getSelectedItem().toString();

        //Ensure user puts all his/her details
        if(recepient_name.equals("") || recepient_phone.equals("")){
            Toast.makeText(ProfileUpdateActivity.this, "Please input all details", Toast.LENGTH_SHORT).show();
        }

        Map recepient_info = new HashMap();
        recepient_info.put("recepient_name", recepient_name);
        recepient_info.put("recepient_phone", recepient_phone);
        recepient_info.put("recepient_rtype", recepient_rtype);

        current_recepient_db.updateChildren(recepient_info);

        progress.dismiss();
        Toast.makeText(ProfileUpdateActivity.this, "Recepient details successfull updated",Toast.LENGTH_SHORT).show();

        //Validation that recepient has uploaded profile image
        if(photoUri != null){
            //Reference of storage as to the path where image is to be stored
            final StorageReference image_filepath = FirebaseStorage.getInstance().getReference().child("recepient_profileimg").child(recepient_id);

            //Uploading task to handle upload of image
            UploadTask taskUpload = image_filepath.putFile(photoUri);

            //Uploading of image
            Task<Uri> urlTask= taskUpload.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    //Check if task was successful
                    if(!task.isSuccessful()){
                        Toast.makeText(ProfileUpdateActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }

                    //Continuation of task to get Url of download
                    return image_filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri url_download = task.getResult();
                        if(url_download != null){
                            Map new_profileimg = new HashMap();
                            new_profileimg.put("recepient_profileimg",url_download.toString());

                            current_recepient_db.updateChildren(new_profileimg);

                            progress.dismiss();
                            Toast.makeText(ProfileUpdateActivity.this,"Recepient details successfully changed",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            });


        }

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK){

            final Uri uri_image = data.getData();
            photoUri = uri_image;

            profile_img.setImageURI(photoUri);
        }
    }



    }

