package edu.gatech.cs2340.waterwedonating;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView info;
    private Button registration;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.txt_nameReg); //assigns variable to editText ID
        password = findViewById(R.id.txt_pwReg1);
        login = findViewById(R.id.btn_login);
        info = findViewById(R.id.txt_info);
        registration = findViewById(R.id.btn_registerhere);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoginUser();
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent2 = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent2);
            }
        });
    }
    private void LoginUser() {
        String userName = username.getText().toString().trim();
        String passWord = password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(userName, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    currentUser = mAuth.getCurrentUser();
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                } else {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
