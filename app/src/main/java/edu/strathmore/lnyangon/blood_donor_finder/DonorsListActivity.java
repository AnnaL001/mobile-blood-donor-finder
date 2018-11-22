package edu.strathmore.lnyangon.blood_donor_finder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import edu.strathmore.lnyangon.blood_donor_finder.donorRecyclerView.DonorAdapter;
import edu.strathmore.lnyangon.blood_donor_finder.donorRecyclerView.DonorObject;

public class DonorsListActivity extends AppCompatActivity {
    private String userId;
    private String donorName;
    private String donorPhone;
    private String

    private RecyclerView donorRecyclerView;
    private ProgressDialog progress;
    private RecyclerView.Adapter donorAdapter;
    private RecyclerView.LayoutManager donorLayoutManager;
    private DatabaseReference donorDetailsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors_list);

        donorRecyclerView = (RecyclerView) findViewById(R.id.donorListRecyclerView);
        donorRecyclerView.setNestedScrollingEnabled(false);

        donorRecyclerView.setHasFixedSize(true);
        donorLayoutManager = new LinearLayoutManager(getApplicationContext());

        donorRecyclerView.setLayoutManager(donorLayoutManager);
        donorAdapter = new DonorAdapter(getDataSetHistory(), getApplicationContext());
        donorRecyclerView.setAdapter(donorAdapter);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        progress = new ProgressDialog(getApplicationContext());
        //Use the progressDialog so as to avoid user wait as Firebase loads
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
        getConsultInformation();

        return;
    }

    private void getConsultInformation() {
        donorDetailsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot child:dataSnapshot.getChildren()){

                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                        if (map.get("Donor")!= null) {
                            String parentId = map.get("Donor").toString();
                            DatabaseReference mOtherUserDB = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor").child(userId);
                            mOtherUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                                        if(map.get("donor_name") != null){
                                            String name = map.get("donor_name").toString();
                                            donorName.setText(map.get("donor_name").toString());
                                        }
                                        if(map.get("donor_phone") != null){
                                            donorPhone.setText(map.get("donor_phone").toString());
                                        }
                                        if(map.get("donor_profileimg") != null){
                                            Glide.with(getApplication()).load(map.get("donor_profileimg").toString()).into(parentImage);
                                        }
                                    }

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }

                        if (map.get("paediatrician")!= null) {
                            String pedId = map.get("paediatrician").toString();
                            DatabaseReference mOtherUserDB = FirebaseDatabase.getInstance().getReference().child("Users").child("Paediatricians").child(pedId);
                            mOtherUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                                        if(map.get("name") != null){
                                            String name = map.get("name").toString();
                                            paediatricianName.setText(map.get("name").toString());
                                        }
                                        if(map.get("phone") != null){
                                            paediatricianPhone.setText(map.get("phone").toString());
                                        }
                                        if(map.get("photoUrl") != null){
                                            Glide.with(getApplication()).load(map.get("photoUrl").toString()).into(paediatricianImage);
                                        }
                                    }

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }


                        if (child.getKey().equals("timestamp")){
                            consultDate.setText(getDate(Long.valueOf(child.getValue().toString())));
                        }
                        if (child.getKey().equals("consultLocation")){
                            consultLocation.setText(child.getValue().toString());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DonorsListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getDate(Long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time*1000);
        String date = DateFormat.format("MM-dd-yyyy hh:mm", cal).toString();
        return date;
    }


}