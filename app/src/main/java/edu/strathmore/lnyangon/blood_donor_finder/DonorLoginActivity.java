package edu.strathmore.lnyangon.blood_donor_finder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.makeText;

public class DonorLoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText email, password;
    public Button dregister, dlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_login);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null){
                    Intent intent = new Intent(DonorLoginActivity.this, donorlandingpage.class);
                    startActivity(intent);
                    finish();

                }
            }
        };


        email = (EditText) findViewById(R.id.dlogin_email);
        password = (EditText) findViewById(R.id.dlogin_pswd);
        dregister = (Button) findViewById(R.id.btn_dregister);
        dlogin = (Button) findViewById(R.id.btn_dlogin);


        dregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d_email = email.getText().toString();
                String d_pswd = password.getText().toString();

                if(d_email.equals("")||d_pswd.equals("")){
                    makeText(DonorLoginActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(d_email,d_pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Donor").child(user_id);
                                current_user_db.setValue(true);
                                makeText(DonorLoginActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            }else{
                                makeText(DonorLoginActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        dlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d_email = email.getText().toString();
                String d_pswd = password.getText().toString();

                if(d_email.equals("")||d_pswd.equals("")){
                    Toast.makeText(DonorLoginActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(d_email,d_pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                makeText(DonorLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DonorLoginActivity.this,donorlandingpage. class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(DonorLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}
