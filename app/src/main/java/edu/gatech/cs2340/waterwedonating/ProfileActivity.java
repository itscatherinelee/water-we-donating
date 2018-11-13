package edu.gatech.cs2340.waterwedonating;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class used to retrieve and display user information
 * upon successful login
 */
public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private TextView userNameOrEmail;
    private TextView Uid;
    private Button logout;
    private TextView names;
    private TextView types;
    private Button viewLocation;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    ArrayList<userInformation> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        types = findViewById(R.id.typeE);
        names = findViewById(R.id.nameId);
        userNameOrEmail = findViewById(R.id.userName);
        Uid = findViewById(R.id.uid1);
        logout = findViewById(R.id.btn_logout);
        viewLocation = findViewById(R.id.viewLocation);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        Intent intent = getIntent();
        data = (ArrayList<userInformation>) intent.getSerializableExtra("userData");
        for (int i = 0; i < data.size() ; i++) {
            if (data.get(i).getId().equals(userID)) {
                String type = data.get(i).getType();
                String name = data.get(i).getName();
                String emailorname = data.get(i).getEmailOrUsername();
                names.setText(name);
                types.setText(type);
                userNameOrEmail.setText(emailorname);
                Uid.setText(userID);
            }

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
