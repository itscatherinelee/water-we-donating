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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class donationActivity extends AppCompatActivity {

    private EditText shortDescription;
    private EditText fullDescription;
    private EditText val;
    private EditText category;
    private TextView times;
    private TextView location;
    private FirebaseAuth mAuth;
    private Button submit;
    String shortDescript = "";
    String fullDescript = "";
    String values = "";
    String cat = "";
    String locations = "";
    String dateStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddonation);

        submit = findViewById(R.id.submit);
        times = findViewById(R.id.timestamp);
        location = findViewById(R.id.location);
        shortDescription = findViewById(R.id.shortDescript);
        fullDescription = findViewById(R.id.fullDescript);
        val = findViewById(R.id.value);
        category = findViewById(R.id.itemType);
        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();
        Long tsLong = System.currentTimeMillis() / 100;
        Date date = new Date(tsLong);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        dateStr = dateFormat.format(date);
        times.setText(dateStr);

        Intent intent = getIntent();
        locations = intent.getStringExtra("Location");
        location.setText(locations);
        shortDescript = shortDescription.getText().toString();
        fullDescript = fullDescription.getText().toString();
        values = val.getText().toString();
        cat = category.getText().toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }
    private void submitData() {
        if (shortDescription.getText().toString().length() > 2) {
            shortDescript = shortDescription.getText().toString();
            fullDescript = fullDescription.getText().toString();
        } else {
            Toast.makeText(this, "Description too short", Toast.LENGTH_SHORT).show();
        }
        if (values.matches("^\\$(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$\n" +
                "\n")) {
            values = val.getText().toString();
        } else {
            Toast.makeText(this, values, Toast.LENGTH_SHORT).show();
        }
        String cC = category.getText().toString();
        if (cC.equals("Clothing") || cC.equals("Household") || cC.equals("Toys") || cC.equals("Electronics") || cC.equals("Blood") || cC.equals("Food")) {
            cat = category.getText().toString();
        } else {
            Toast.makeText(this, "Not a valid donation item", Toast.LENGTH_SHORT).show();
        }

        donationData donate = new donationData(dateStr,locations,shortDescript,fullDescript,values,cat);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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


