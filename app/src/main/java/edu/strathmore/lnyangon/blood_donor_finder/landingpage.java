package edu.strathmore.lnyangon.blood_donor_finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class landingpage extends AppCompatActivity {
private Button rprofile, bdonors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);

        rprofile = (Button) findViewById(R.id.btn_rprofile);
        bdonors  = (Button) findViewById(R.id.btn_bdonors);

        rprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(landingpage.this,ProfileUpdateActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        bdonors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(landingpage.this,BloodDonorsActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
