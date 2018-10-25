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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference info =  db.collection("users").document(uid);

                    info.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        public static final String TAG = "";

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                String name = doc.get("Name").toString();
                                String type = doc.get("UserType").toString();
                                String user_name = doc.get("Username").toString();
                                names.setText(name);
                                types.setText(type);
                                userName.setText(user_name);

                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
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
