package edu.gatech.cs2340.waterwedonating;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
<<<<<<< HEAD
 * Class used for handling registration activity
 */
public class RegistrationActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener{
=======
 * Class used for handling registraction activity
 */
public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
>>>>>>> master

    private EditText nameReg;
    private EditText usernameReg;
    private EditText passwordReg;
    private String type;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private ArrayList<userInformation> localData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        String[] users ={"Admin","Employee","User"};
        final List<String> userType = new ArrayList<>(Arrays.asList(users));
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, userType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);


        nameReg = findViewById(R.id.Names_reg);
        usernameReg = findViewById(R.id.userName_reg);
        passwordReg = findViewById(R.id.pass_reg);
        type = spin.getSelectedItem().toString();
        TextView infoReg = findViewById(R.id.txt_infoReg);
        Button signUp = findViewById(R.id.btn_signUp);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        user = mAuth.getCurrentUser();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        type = (String) arg0.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(), type, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    /**
     * Takes user input for username and password and
     * checks if it is not empty and in proper format.
     */
    public void RegisterUser(){
        String username = usernameReg.getText().toString().trim();
        String password = passwordReg.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "User name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            register(username, password);
        } else if (!username.contains("@")) {
            register(username + "@email.com", password);
        } else {
            Toast.makeText(RegistrationActivity.this, "Invalid Format, Try Again",
                    Toast.LENGTH_SHORT).show();
        }

    }
    private void register(String username, String password) {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                userInformation users = new userInformation(type,
                                        nameReg.getText().toString(),
                                        usernameReg.getText().toString(), user.getUid());
                                localData.add(users);
                                myRef.child("users").child(user.getUid()).setValue(users);
                                Toast.makeText(RegistrationActivity.this,
                                        "Registration Successful",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(getApplicationContext(),
                                        ProfileActivity.class);
                                intent.putExtra("userData",localData);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistrationActivity.this,
                                        "Registration Failed, Try Again",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }

}
