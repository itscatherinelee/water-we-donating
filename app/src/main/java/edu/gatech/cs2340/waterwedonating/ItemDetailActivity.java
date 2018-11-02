package edu.gatech.cs2340.waterwedonating;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ItemDetailActivity extends Activity{

    private TextView key1;
    private TextView value1;
    private TextView key2;
    private TextView value2;
    private TextView key3;
    private TextView value3;
    private TextView key4;
    private TextView value4;
    private TextView key5;
    private TextView value5;
    private TextView key6;
    private TextView value6;
    private TextView key7;
    private TextView value7;
    private TextView key8;
    private TextView value8;
    private TextView key9;
    private TextView value9;
    private TextView key10;
    private TextView value10;
    private TextView key11;
    private TextView value11;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button addDonation;
    private DatabaseReference myRef;
    String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitem);

            key1 = findViewById(R.id.key1);
        value1 = findViewById(R.id.value1);
        key2 = findViewById(R.id.key2);
        value2 = findViewById(R.id.value2);
        key3 = findViewById(R.id.key3);
        value3 = findViewById(R.id.value3);
        key4 = findViewById(R.id.key4);
        value4 = findViewById(R.id.value4);
        key5 = findViewById(R.id.key5);
        value5 = findViewById(R.id.value5);
        key6 = findViewById(R.id.key6);
        value6 = findViewById(R.id.value6);
        key7 = findViewById(R.id.key7);
        value7 = findViewById(R.id.value7);
        key8 = findViewById(R.id.key8);
        value8 = findViewById(R.id.value8);
        key9 = findViewById(R.id.key9);
        value9 = findViewById(R.id.value9);
        key10 = findViewById(R.id.key10);
        value10 = findViewById(R.id.value10);
        key11 = findViewById(R.id.key11);
        value11 = findViewById(R.id.value11);

        InputStream inputStream = getResources().openRawResource(R.raw.locationdata);
        LocationReader locationReader = new LocationReader(inputStream);
        List<String[]> dataList = locationReader.read();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String[] keys = dataList.get(0);
        values = dataList.get((int)extras.get("position"));

        key1.setText(keys[0]);
        key2.setText(keys[1]);
        key3.setText(keys[2]);
        key4.setText(keys[3]);
        key5.setText(keys[4]);
        key6.setText(keys[5]);
        key7.setText(keys[6]);
        key8.setText(keys[7]);
        key9.setText(keys[8]);
        key10.setText(keys[9]);
        key11.setText(keys[10]);

        value1.setText(values[0]);
        value2.setText(values[1]);
        value3.setText(values[2]);
        value4.setText(values[3]);
        value5.setText(values[4]);
        value6.setText(values[5]);
        value7.setText(values[6]);
        value8.setText(values[7]);
        value9.setText(values[8]);
        value10.setText(values[9]);
        value11.setText(values[10]);

    }
}
