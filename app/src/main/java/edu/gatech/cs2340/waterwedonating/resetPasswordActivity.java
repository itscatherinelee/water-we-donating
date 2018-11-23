package edu.gatech.cs2340.waterwedonating;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPasswordActivity extends AppCompatActivity {
    private Button sendEmail;
    private EditText resetEmail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_recovery);

        sendEmail = findViewById(R.id.sendingEmail);
        resetEmail = findViewById(R.id.emailBar);
        mAuth = FirebaseAuth.getInstance();

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = resetEmail.getText().toString();
                if (TextUtils.isEmpty(email) || email.length() < 2) {
                    Toast.makeText(resetPasswordActivity.this, "Incorrect Email! Please enter a valid email.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(resetPasswordActivity.this, "Email sent! Please check your email to reset password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(resetPasswordActivity.this, LoginActivity.class));
                            } else {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(resetPasswordActivity.this, "Error occurred: "+ errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
