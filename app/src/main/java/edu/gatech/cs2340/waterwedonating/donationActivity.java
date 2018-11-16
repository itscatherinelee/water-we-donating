package edu.gatech.cs2340.waterwedonating;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Class used to track all donations
 */
public class donationActivity extends AppCompatActivity

        implements AdapterView.OnItemSelectedListener {

    //******************Inner class to add and retrieve data from firebase*************************
    public class FirebaseHelper {

        DatabaseReference db;
        Boolean saved;
        ArrayList<donationData> dd = new ArrayList<>();
        ArrayList<donationData> holder = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> cattemp = new ArrayList<>();
        ArrayList<donationData> cat = new ArrayList<>();
        ListView mListView;
        Context c;
        SearchView sv;
        Spinner spin, spin1;
        String categoryChoice = "";

        /**
         * Constructor initializes view components and the database
         * @param db database reference
         * @param context context
         * @param mListView list view
         * @param spin spinner one
         * @param spin1 spinner 2
         * @param sv searchview
         */
        public FirebaseHelper(DatabaseReference db, Context context, ListView mListView,
                              Spinner spin, Spinner spin1, SearchView sv) {
            this.db = db;
            this.c = context;
            this.mListView = mListView;
            this.spin = spin;
            this.spin1 = spin1;
            this.sv = sv;
            this.retrieve();
        }

        /**
         * Checks if data is saved to firebase
         * @param donation accepts type donationData
         * @return boolean if saved or not
         */
        public Boolean save(donationData donation) {
            if (donation == null) {
                saved = false;
            } else {
                try {
                    db.child("Donations").push().setValue(donation);
                    saved = true;

                } catch (DatabaseException e) {
                    e.printStackTrace();
                    saved = false;
                }
            }
            return saved;
        }

        /**
         * @return arraylist if data was saved
         */
        public ArrayList<donationData> retrieve() {
            temp.clear();
            cattemp.clear();
            db.child("Donations").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dd.clear();
                    if (dataSnapshot.exists() && (dataSnapshot.getChildrenCount() > 0)) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            donationData donate = ds.getValue(donationData.class);
                            dd.add(donate);
                        }
                        cattemp.addAll(Arrays.asList("All Categories","Clothing","Shoes", "Boots",
                                "Outerwear", "Books","Electronics","Sports", "Toys"
                                ,"Games", "Media","Housewares","Collectibles","Tools","Domestics",
                                "Furniture","Non-Perishable Foods","Other"));
                        temp.addAll(Arrays.asList("All Locations","AFD Station 4",
                                "Boys & Girls Club W.W. Woolfolk"
                                , "Pathway Upper Room Christian Ministries",
                                "Pavilion of Hope Inc","D&D Convenience Store",
                                "Keep North Fulton Beautiful"));

                        final ArrayAdapter adapter1 = new ArrayAdapter(donationActivity.this,
                                android.R.layout.simple_spinner_item, temp);
                        adapter1.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item);
                        spin.setAdapter(adapter1);
                        ArrayAdapter adapter2 = new ArrayAdapter(donationActivity.this,
                                android.R.layout.simple_spinner_item, cattemp);
                        adapter2.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item);
                        spin1.setAdapter(adapter2);
                        spin1.setVisibility(View.GONE);

                        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                holder.clear();
                                String locationChoice =
                                        parent.getItemAtPosition(position).toString();
                                if (locationChoice.equals("All Locations")) {
                                    spin1.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < dd.size(); i++) {
                                        holder.add(dd.get(i));
                                    }
                                    adapter = new CustomAdapter(c, holder);
                                    mListView.setAdapter(adapter);

                                    sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                        @Override
                                        public boolean onQueryTextSubmit(String s) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onQueryTextChange(String s) {
                                            adapter = new CustomAdapter(c, holder);
                                            mListView.setAdapter(adapter);
                                            adapter.getFilter().filter(s);
                                            return false;
                                        }
                                    });

                                    spin1.setOnItemSelectedListener(
                                            new AdapterView.OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view,
                                                                   int position, long l) {
                                            cat.clear();
                                            adapter = new CustomAdapter(c, holder);
                                            mListView.setAdapter(adapter);
                                            categoryChoice =
                                                    parent.getItemAtPosition(position).toString();
                                            if (categoryChoice.equals("All Categories")) {
                                                adapter = new CustomAdapter(c, holder);
                                                mListView.setAdapter(adapter);
                                                sv.setOnQueryTextListener(
                                                        new SearchView.OnQueryTextListener() {
                                                    @Override
                                                    public boolean onQueryTextSubmit(String s) {
                                                        return false;
                                                    }

                                                    @Override
                                                    public boolean onQueryTextChange(String s) {
                                                        adapter = new CustomAdapter(c, holder);
                                                        mListView.setAdapter(adapter);
                                                        adapter.getFilter().filter(s);
                                                        return false;
                                                    }
                                                });
                                            } else {
                                                for (int i = 0; i < holder.size(); i++) {
                                                    if (holder.get(i).getCategory().equals(
                                                            categoryChoice)) {
                                                        cat.add(holder.get(i));
                                                    }
                                                }
                                                adapter = new CustomAdapter(c, cat);
                                                mListView.setAdapter(adapter);
                                                sv.setOnQueryTextListener(
                                                        new SearchView.OnQueryTextListener() {
                                                    @Override
                                                    public boolean onQueryTextSubmit(String s) {
                                                        return false;
                                                    }

                                                    @Override
                                                    public boolean onQueryTextChange(String s) {
                                                        adapter = new CustomAdapter(c, cat);
                                                        mListView.setAdapter(adapter);
                                                        adapter.getFilter().filter(s);
                                                        return false;
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                            adapter = new CustomAdapter(c, holder);
                                            mListView.setAdapter(adapter);
                                        }
                                    });
                                } else {
                                    spin1.setVisibility(View.VISIBLE);
                                    holder.clear();
                                    for (int i = 0; i < dd.size(); i++) {
                                        if (locationChoice.equals(dd.get(i).getLocation())) {
                                            holder.add(dd.get(i));
                                        }
                                    }
                                    if (holder.isEmpty()) {
                                        Toast.makeText(donationActivity.this,
                                                "No donation found for " + locationChoice,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    adapter = new CustomAdapter(c, holder);
                                    mListView.setAdapter(adapter);

                                    sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                        @Override
                                        public boolean onQueryTextSubmit(String s) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onQueryTextChange(String s) {
                                            adapter = new CustomAdapter(c, holder);
                                            mListView.setAdapter(adapter);
                                            adapter.getFilter().filter(s);
                                            return false;
                                        }
                                    });
                                    spin1.setOnItemSelectedListener(
                                            new AdapterView.OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view,
                                                                   int position, long l) {
                                            cat.clear();
                                            adapter = new CustomAdapter(c, holder);
                                            mListView.setAdapter(adapter);
                                            categoryChoice =
                                                    parent.getItemAtPosition(position).toString();
                                            if (categoryChoice.equals("All Categories")) {
                                                adapter = new CustomAdapter(c, holder);
                                                mListView.setAdapter(adapter);
                                                sv.setOnQueryTextListener(
                                                        new SearchView.OnQueryTextListener() {
                                                    @Override
                                                    public boolean onQueryTextSubmit(String s) {
                                                        return false;
                                                    }

                                                    @Override
                                                    public boolean onQueryTextChange(String s) {
                                                        adapter = new CustomAdapter(c, holder);
                                                        mListView.setAdapter(adapter);
                                                        adapter.getFilter().filter(s);
                                                        return false;
                                                    }
                                                });
                                            } else {
                                                for (int i = 0; i < holder.size(); i++) {
                                                    if (holder.get(i).getCategory().equals(
                                                            categoryChoice)) {
                                                        cat.add(holder.get(i));
                                                    }
                                                }
                                                adapter = new CustomAdapter(c, cat);
                                                mListView.setAdapter(adapter);
                                                sv.setOnQueryTextListener(
                                                        new SearchView.OnQueryTextListener() {
                                                    @Override
                                                    public boolean onQueryTextSubmit(String s) {
                                                        return false;
                                                    }

                                                    @Override
                                                    public boolean onQueryTextChange(String s) {
                                                        adapter = new CustomAdapter(c, cat);
                                                        mListView.setAdapter(adapter);
                                                        adapter.getFilter().filter(s);
                                                        return false;
                                                    }
                                                });
                                            }

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                adapter = new CustomAdapter(c, dd);
                                mListView.setAdapter(adapter);
                            }
                        });


                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                mListView.smoothScrollToPosition(dd.size());
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("mTAG", databaseError.getMessage());
                    Toast.makeText(c, "ERROR " + databaseError.getMessage(),
                            Toast.LENGTH_LONG).show();

                }
            });

            return dd;
        }

    }
//*********************************Second class used to add popup data to listview*****************
        class CustomAdapter extends BaseAdapter implements Filterable {
        Context c;
        List<donationData> donor;
        TextView viewItem, viewLocation, viewFull,viewValue, viewCategory, viewTime;
        Button bClose;
        Dialog e;
        customFilter filter;
    public CustomAdapter(Context c, ArrayList<donationData> donor) {
            this.c = c;
            this.donor = donor;
        }

        @Override
        public int getCount() {
            return donor.size();
        }

        @Override
        public Object getItem(int position) {
            return donor.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View convertView1 = convertView;
            if (convertView1 == null) {
                convertView1 = LayoutInflater.from(c).inflate(R.layout.activity_donationlayout,
                        parent, false);
            }

            TextView nameTextView = convertView1.findViewById(R.id.nameTextView);
            TextView descriptionTextView = convertView1.findViewById(R.id.descriptionTextView2);

            final donationData s = (donationData) this.getItem(position);

            nameTextView.setText(s.getShortDescription().toUpperCase());
            String setup = "Time Donated: "+s.getTimestamp()
                    +"           Location: "+s.getLocation();
            descriptionTextView.setText(setup);

            convertView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    e = new Dialog(donationActivity.this);
                    e.setTitle("Donation Data");
                    e.setContentView(R.layout.activity_itemdonation_view);
                    viewItem = e.findViewById(R.id.itemView);
                    viewLocation = e.findViewById(R.id.locationView);
                    viewFull = e.findViewById(R.id.fullView);
                    viewValue = e.findViewById(R.id.valueView);
                    viewCategory = e.findViewById(R.id.categoryView);
                    viewTime = e.findViewById(R.id.timeView);
                    bClose = e.findViewById(R.id.closeB);
                    viewItem.setText("Item Name: "+s.getShortDescription());
                    viewLocation.setText("Location:  "+s.getLocation());
                    viewFull.setText("Description: "+s.getFullDescription());
                    viewValue.setText("Dollar Value: "+s.getValue());
                    viewCategory.setText("Category: "+s.getCategory());
                    viewTime.setText("Time Stamp: "+s.getTimestamp());
                    e.show();
                    bClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            e.dismiss();
                        }
                    });
                }
            });
            return convertView1;
        }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new customFilter();
        }
        return filter;
    }
    class customFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            CharSequence charSequence1 = charSequence;
            FilterResults filters = new FilterResults();
            if (charSequence1 != null && charSequence1.length() > 0) {
                List<donationData> filteredItems = new ArrayList<>();
                charSequence1 = charSequence1.toString().toUpperCase();
                for (int i = 0; i < donor.size(); i++) {
                    if (donor.get(i).getShortDescription().toUpperCase().contains(charSequence1)) {
                        filteredItems.add(donor.get(i));
                    }
                }
                filters.count = filteredItems.size();
                filters.values = filteredItems;
            } else {
                filters.count = donor.size();
                filters.values = donor;
            }
            return filters;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filters) {
                    donor = (ArrayList<donationData>) filters.values;
                    notifyDataSetChanged();
        }
    }
}

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView mListView;
    EditText shortDescript, fullDescript, itemValue;
    TextView timestamp;
    String category = "";
    String fullDescrip = "";
    String shortDescrip = "";
    String dateStr = "";
    String location = "";
    String locationView = "";
    String categoryView = "";
    Button close;
    Dialog d;
    SearchView sv;
    Spinner s2;
    Button search;
    ArrayList<String> loc = new ArrayList<>();
    private DatabaseReference myRef;

    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference("Donations");
        Spinner spin4 = (Spinner) findViewById(R.id.locationSelecter);
        Spinner spin5 = (Spinner) findViewById(R.id.categorySearch);
        mListView = (ListView) findViewById(R.id.listView);
        sv = findViewById(R.id.searchView);
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db, this, mListView, spin4, spin5, sv);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListView.smoothScrollToPosition(4);
                displayInputDialog();
            }
        });
        FloatingActionButton fab2 = findViewById(R.id.fabSearch);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySearchDialog();
            }
        });
    }
    private void displaySearchDialog() {

    }

    private void displayInputDialog() {
        d = new Dialog(this);
        d.setTitle("Add Donation");
        d.setContentView(R.layout.activity_popupdonation);

        Long tsLong = System.currentTimeMillis();
        Date date = new Date(tsLong);

        close = d.findViewById(R.id.closeButton);
        timestamp = d.findViewById(R.id.timestamps);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        dateStr = dateFormat.format(date);
        timestamp.setText(dateStr);

        loc.addAll(Arrays.asList("AFD Station 4", "Boys & Girls Club W.W. Woolfolk"
                , "Pathway Upper Room Christian Ministries", "Pavilion of Hope Inc",
                "D&D Convenience Store", "Keep North Fulton Beautiful"));

        Spinner spin1 = d.findViewById(R.id.locationSelecter);
        spin1.setOnItemSelectedListener(this);

        ArrayAdapter adapter1 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, loc);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter1);

        items.addAll(Arrays.asList("Clothing","Shoes", "Boots", "Outerwear", "Books","Electronics",
                "Sports", "Toys"
                ,"Games", "Media","Housewares","Collectibles","Tools","Domestics","Furniture",
                "Non-Perishable Foods","Other"));
        Spinner spin2 = d.findViewById(R.id.categorySpinner);
        spin2.setOnItemSelectedListener(this);

        ArrayAdapter adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, items);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter2);


        shortDescript = d.findViewById(R.id.shortDescript);
        fullDescript = d.findViewById(R.id.fullDescript);
        itemValue = d.findViewById(R.id.values);
        Button saveBtn = d.findViewById(R.id.saveBtn);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = "";
                if (shortDescript.getText().toString().length() > 2
                        || fullDescript.getText().toString().length() > 2) {
                    shortDescrip = shortDescript.getText().toString();
                    fullDescrip = fullDescript.getText().toString();
                } else {
                    Toast.makeText(donationActivity.this,
                            "Description too short", Toast.LENGTH_SHORT).show();
                }
                if (itemValue.getText().toString().matches("^[0-9,.$]*$")) {
                    value = itemValue.getText().toString();
                } else {
                    Toast.makeText(donationActivity.this,
                            "Invalid amount", Toast.LENGTH_SHORT).show();
                }

                donationData don8 = new donationData(dateStr, location,
                        shortDescrip, fullDescrip, value, category);

                    if (helper.save(don8)) {
                        shortDescript.setText("");
                        fullDescript.setText("");
                        itemValue.setText("");

                        ArrayList<donationData> fetchedData = helper.retrieve();
                        adapter = new CustomAdapter(donationActivity.this, fetchedData);
                        mListView.setAdapter(adapter);
                        mListView.smoothScrollToPosition(fetchedData.size());
                    }
                }


        });
        d.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position,long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.locationSelecter) {
            location = (String) parent.getItemAtPosition(position);
        } else if (spinner.getId() == R.id.categorySpinner) {
            category = (String) parent.getItemAtPosition(position);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}


