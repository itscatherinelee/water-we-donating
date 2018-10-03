package edu.gatech.cs2340.waterwedonating;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView info;
    private Button registration;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView userName;
    private TextView uid;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        username = findViewById(R.id.txt_nameReg); //assigns variable to editText ID
        password = findViewById(R.id.txt_pwReg1);
        login = findViewById(R.id.btn_login);
        info = findViewById(R.id.txt_info);
        registration = findViewById(R.id.btn_registerhere);
        logout = findViewById(R.id.btn_logout);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null){
            String username = user.getEmail();
            String uid = user.getUid();
            Email.setText(email);
            Uid.setText(uid);
        }


        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
