package edu.strathmore.lnyangon.blood_donor_finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class donorlandingpage extends AppCompatActivity {
    private Button dprofile, brecepients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donorlandingpage);

        dprofile = (Button) findViewById(R.id.btn_dprofile);
        brecepients = (Button) findViewById(R.id.btn_brecepients);

        dprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(donorlandingpage.this,DonorProfileActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        brecepients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(donorlandingpage.this, BloodRecepientsActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });



    }
}
