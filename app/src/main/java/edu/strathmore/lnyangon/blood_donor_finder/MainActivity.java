package edu.strathmore.lnyangon.blood_donor_finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
public Button btn_donor, btn_recipient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_donor = (Button) findViewById(R.id.btn_donor);
        btn_recipient = (Button) findViewById(R.id.btn_recipient);


        btn_donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DonorLoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        btn_recipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RecepientLoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

}
