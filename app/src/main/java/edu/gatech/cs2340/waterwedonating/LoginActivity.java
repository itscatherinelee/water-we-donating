package edu.gatech.cs2340.waterwedonating;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
    private TextView info, forgotPass;
    private Button registration;
    private Button guest;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView lockout;
    int loginAttempt;
    int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgotPass = findViewById(R.id.forgot);
        username = findViewById(R.id.Names_reg);
        password = findViewById(R.id.pass_reg);
        login = findViewById(R.id.btn_login);
        info = findViewById(R.id.txt_info);
        registration = findViewById(R.id.btn_registerhere);
        guest = findViewById(R.id.guestbtn);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        lockout = findViewById(R.id.lockoutClock);
        lockout.setVisibility(View.GONE);

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(LoginActivity.this, LocationActivity.class);
                intent.putExtra("User","Guest");
                startActivity(intent);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), resetPasswordActivity.class));
            }
        });
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
    public boolean LoginUser(String userName, String passWord) {
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
    private void logon(final String userName, final String passWord) {
        mAuth.signInWithEmailAndPassword(userName, passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() ) {
                    loginAttempt = 0;
                    currentUser = mAuth.getCurrentUser();
                    finish();
                    Intent intent = new Intent(LoginActivity.this, LocationActivity.class);
                    intent.putExtra("User","RegisteredUser");
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed. Try Again", Toast.LENGTH_SHORT).show();
                    loginAttempt++;
                    if (loginAttempt > 3) {
                        lockout.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Too many incorrect attempts",Toast.LENGTH_SHORT).show();
                        login.setEnabled(false);
                        registration.setEnabled(false);
                        guest.setEnabled(false);
                        forgotPass.setEnabled(false);
                        username.setFocusable(false);
                        password.setFocusable(false);
                        counter = 30;
                        new CountDownTimer(30000, 1000){
                            public void onTick(long millisUntilFinished){
                                lockout.setText("Please wait: " + String.valueOf(counter));
                                counter--;
                            }
                            public  void onFinish(){
                                login.setEnabled(true);
                                registration.setEnabled(true);
                                guest.setEnabled(true);
                                forgotPass.setEnabled(true);
                                username.setFocusableInTouchMode(true);
                                password.setFocusableInTouchMode(true);
                                lockout.setVisibility(View.GONE);
                            }
                        }.start();
                    }
                }
            }
        });
    }




}
