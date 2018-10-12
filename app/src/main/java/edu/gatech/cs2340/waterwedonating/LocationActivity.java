package edu.gatech.cs2340.waterwedonating;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ListView;

import java.io.InputStream;
import java.util.List;

public class LocationActivity extends Activity {

    private Button backButton;
    private ListView locationList;
    private ItemArrayAdapter itemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        backButton = findViewById(R.id.backButton);
        locationList = findViewById(R.id.locationList);
        itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

        Parcelable state = locationList.onSaveInstanceState();
        locationList.setAdapter(itemArrayAdapter);
        locationList.onRestoreInstanceState(state);

        InputStream inputStream = getResources().openRawResource(R.raw.locationdata);
        LocationReader locationReader = new LocationReader(inputStream);
        List<String[]> dataList = locationReader.read();

        for(String[] data:dataList ) {
            itemArrayAdapter.add(data);
        }
    }

}
