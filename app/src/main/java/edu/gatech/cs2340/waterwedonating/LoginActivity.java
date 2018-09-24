package edu.gatech.cs2340.waterwedonating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.txt_username); //assigns variable to editText ID
        password = findViewById(R.id.txt_password);
        login = findViewById(R.id.btn_login);
        info = findViewById(R.id.txt_info);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Validate(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void Validate(String usernameInput, String passwordInput) {
        if ((usernameInput.equals("admin")) && (passwordInput.equals("1234"))) {
            Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
            startActivity(intent);
        } else {
            info.setText("Incorrect Username or Password");
        }
    }
}
