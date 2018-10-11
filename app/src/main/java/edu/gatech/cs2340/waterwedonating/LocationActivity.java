package edu.gatech.cs2340.waterwedonating;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class LocationActivity extends Activity {

    private Button backButton;
    private TextView testScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        backButton = findViewById(R.id.backButton);
        testScreen = findViewById(R.id.testScreen);

    }

}
