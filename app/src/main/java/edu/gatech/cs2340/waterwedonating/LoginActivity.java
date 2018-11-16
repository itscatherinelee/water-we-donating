package edu.gatech.cs2340.waterwedonating;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Handles the login process and authentication for this app
 */
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

        username = findViewById(R.id.Names_reg);
        password = findViewById(R.id.pass_reg);
        login = findViewById(R.id.btn_login);
        info = findViewById(R.id.txt_info);
        registration = findViewById(R.id.btn_registerhere);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString().trim();
                String passWord = password.getText().toString().trim();
                LoginUser(userName, passWord);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent2);
            }
        });
    }
    private boolean LoginUser(String userName, String passWord) {
        boolean flag = true;
        if((userName.isEmpty() || passWord.isEmpty())) {
            flag = false;
            Toast.makeText(LoginActivity.this, "Login Failed. Try Again", Toast.LENGTH_SHORT).show();
        }
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
            flag = true;
            logon(userName, passWord);
        } else if (!userName.contains("@")) {
            flag = true;
            logon(userName + "@email.com", passWord);
        }
        return flag;
    }
    private void logon(final String userName, String passWord) {
        mAuth.signInWithEmailAndPassword(userName, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() ) {
                    currentUser = mAuth.getCurrentUser();
                    finish();
                    startActivity(new Intent(LoginActivity.this, LocationActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed. Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




}
