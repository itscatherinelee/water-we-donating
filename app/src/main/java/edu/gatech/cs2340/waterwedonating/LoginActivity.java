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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Handles the login process and authentication for this app
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView info, forgotPass;
    private Button registration;
    private Button guest;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView lockout;
    private GoogleApiClient mGoogleApiClient;
    int loginAttempt;
    int counter;
    static final int RC_SIGN_IN = 10;





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
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton button = (SignInButton) findViewById(R.id.login_button);

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


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            Toast.makeText(LoginActivity.this, " You've made your account", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}



