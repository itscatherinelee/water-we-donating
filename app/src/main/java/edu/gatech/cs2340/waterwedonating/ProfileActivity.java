package edu.gatech.cs2340.waterwedonating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView userName;
    private TextView Uid;
    private Button logout;
    private TextView names;
    private TextView types;
    private Button viewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        types = findViewById(R.id.typeE);
        names = findViewById(R.id.nameId);
        userName = findViewById(R.id.userName);
        Uid = findViewById(R.id.uid1);
        logout = findViewById(R.id.btn_logout);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        viewLocation = findViewById(R.id.viewLocation);

        if (user != null){
            String username = user.getEmail();
            String uid = user.getUid();
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            String user_name = extras.getString("Username");
            String name = extras.getString("Name");
            String type = extras.getString("Type");
            names.setText(name);
            types.setText(type);
            userName.setText(user_name.substring(0,username.indexOf('@')));
            Uid.setText(uid);
        }


        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        viewLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, LocationActivity.class));
            }
        });
    }

}
