package edu.strathmore.lnyangon.blood_donor_finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {
public Button bdonor, brecepient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bdonor = (Button) findViewById(R.id.btn_donor) ;
        brecepient = (Button) findViewById(R.id.btn_recepient);

        bdonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,DonorLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        brecepient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,RecepientLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
