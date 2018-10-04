package edu.gatech.cs2340.waterwedonating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameReg;
    private EditText passwordReg1;
    private EditText passwordReg2;
    private TextView infoReg;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameReg = findViewById(R.id.txt_nameReg);
        passwordReg1 = findViewById(R.id.txt_pwReg1);
        passwordReg2 = findViewById(R.id.txt_pwReg2);
        infoReg = findViewById(R.id.txt_infoReg);
        signUp = findViewById(R.id.btn_signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Validate(passwordReg1.getText().toString(), passwordReg1.getText().toString());
            }
        });

    }


    private void Validate(String passwordReg1, String passwordReg2) {
        if (passwordReg1.equals(passwordReg2)) {
            Intent intent = new Intent(RegistrationActivity.this, SecondActivity.class);
            startActivity(intent);
        } else {
            infoReg.setText("Password does not match, try again");
        }
    }
}
