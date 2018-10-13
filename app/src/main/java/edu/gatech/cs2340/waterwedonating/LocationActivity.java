package edu.gatech.cs2340.waterwedonating;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import java.io.InputStream;
import java.util.List;

public class LocationActivity extends Activity implements OnItemClickListener {

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

        locationList.setOnItemClickListener(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LocationActivity.this, ProfileActivity.class);
                startActivity(intent2);
            }
        });
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Intent intent = new Intent(LocationActivity.this, ItemDetailActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

}
