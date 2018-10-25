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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class donationActivity extends AppCompatActivity {

    private EditText shortDescription;
    private EditText fullDescription;
    private EditText value;
    private EditText category;
    private TextView times;
    private TextView location;
    private FirebaseAuth mAuth;
    String shortDescript = "";
    String fullDescript = "";
    String values = "";
    String cat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        times = findViewById(R.id.timestamp);
        location = findViewById(R.id.location);
        shortDescription = findViewById(R.id.shortDescript);
        fullDescription = findViewById(R.id.fullDescript);
        value = findViewById(R.id.value);
        category = findViewById(R.id.itemType);
        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();
        Long tsLong = System.currentTimeMillis()/100;
        String ts = tsLong.toString();
//        times.setText(ts);

        Intent intent = getIntent();
        String locations = intent.getStringExtra("Location");
//        location.setText(locations);
        if (shortDescription.getText().toString().length() > 5) {
            shortDescript = shortDescription.getText().toString();
            fullDescript = fullDescription.getText().toString();
        } else {
            Toast.makeText(this, "Description too short", Toast.LENGTH_SHORT).show();
        }
        if (value.getText().toString().matches("/^[0-9,$.]*$/")) {
            values = value.getText().toString();
        } else {
            Toast.makeText(this, "Incorrect amount added", Toast.LENGTH_SHORT).show();
        }
        String cC = category.getText().toString();
        if (cC.equals("Clothing") || cC.equals("Household") || cC.equals("Toys") || cC.equals("Electronics") || cC.equals("Blood")) {
            cat = category.getText().toString();
        } else {
            Toast.makeText(this, "Not a valid donation item", Toast.LENGTH_SHORT).show();
        }

        donationData donate = new donationData(ts,locations,shortDescript,fullDescript,values,cat);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        db.collection("donations").document(locations)
                .set(donate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    public static final String TAG = "";

                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    public static final String TAG = "";

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });


    }
}
