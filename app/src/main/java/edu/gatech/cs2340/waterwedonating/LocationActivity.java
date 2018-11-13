package edu.gatech.cs2340.waterwedonating;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Class used to find and create donation locations
 */
public class LocationActivity extends Activity implements OnItemClickListener {

    private Button backButton;
    private ListView locationList;
    private ItemArrayAdapter itemArrayAdapter;
    private List<String[]> dataList;
    private Button addDonation;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        addDonation = findViewById(R.id.donations);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mapButton = findViewById(R.id.mapButton);
        backButton = findViewById(R.id.backButton);
        locationList = findViewById(R.id.locationList);
        itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    userInformation users = childDataSnapshot.getValue(userInformation.class);
                    if (users.getId().equals(user.getUid())) {
                        if (users.getType().equals("User")){
                            addDonation.setVisibility(View.GONE);
                        } else {
                            addDonation.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        Parcelable state = locationList.onSaveInstanceState();
        locationList.setAdapter(itemArrayAdapter);
        locationList.onRestoreInstanceState(state);

        InputStream inputStream = getResources().openRawResource(R.raw.locationdata);
        LocationReader locationReader = new LocationReader(inputStream);
        dataList = locationReader.read();

        for(String[] data:dataList ) {
            itemArrayAdapter.add(data);
        }

        locationList.setOnItemClickListener(this);


        addDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), donationActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LocationActivity.this, LoginActivity.class);
                startActivity(intent2);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LocationActivity.this, MapsActivity.class);
                startActivity(intent2);
            }
        });
    }


    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Intent intent = new Intent(LocationActivity.this, ItemDetailActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

}
