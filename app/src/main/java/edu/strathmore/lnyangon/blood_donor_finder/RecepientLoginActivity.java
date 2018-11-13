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

public class RecepientLoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText email, password;
public Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepient_login);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null){
                   Intent intent = new Intent(RecepientLoginActivity.this, landingpage.class);
                   startActivity(intent);
                   finish();

                }
            }
        };

        email = (EditText) findViewById(R.id.rlogin_email);
        password = (EditText) findViewById(R.id.rlogin_pswd);
        register = (Button) findViewById(R.id.btn_register);
        login = (Button) findViewById(R.id.btn_login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String r_email = email.getText().toString();
                 String r_pswd = password.getText().toString();

                if(r_email.equals("")||r_pswd.equals("")){
                    makeText(RecepientLoginActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(r_email,r_pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Recepient").child(user_id);
                                current_user_db.setValue(true);
                                makeText(RecepientLoginActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            }else{
                                makeText(RecepientLoginActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r_email = email.getText().toString();
                String r_pswd = password.getText().toString();

                if(r_email.equals("")||r_pswd.equals("")){
                    Toast.makeText(RecepientLoginActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(r_email,r_pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                makeText(RecepientLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RecepientLoginActivity.this,landingpage. class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(RecepientLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }


}
