package com.example.myrosterpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class StaffActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    private FloatingActionButton fab;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private List<Staff> listStaff = new ArrayList<>();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        //https://suragch.medium.com/how-to-add-a-bottom-navigation-bar-in-android-958ed728ef6c
        //Article sited above used as a source for bottom navigation code
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(StaffActivity.this, MainActivity.class));
                        break;
                    case R.id.action_staff:
                        startActivity(new Intent(StaffActivity.this, StaffActivity.class));
                        break;
                    case R.id.action_roster:
                        startActivity(new Intent(StaffActivity.this, RosterActivity.class));
                        break;
                    case R.id.action_request:
                        startActivity(new Intent(StaffActivity.this, RequestActivity.class));
                        break;
                    case R.id.action_settings:
                        startActivity(new Intent(StaffActivity.this, SettingsActivity.class));
                        break;
                }
                return true;
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();

        initUI();
        setListViewAdapter();

        addSingleEventListener();
        addChildEventListener();

        setFabClickListener();
        setListViewItemListener();
        setListViewLongClickListener();
    }

    private void initUI() {
        progressBar = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab);
        listView = findViewById(R.id.listView);
    }

    private void setListViewAdapter(){
        listViewAdapter = new ListViewAdapter(this, listStaff);
        listView.setAdapter(listViewAdapter);
    }

    private void addChildEventListener() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Staff staff = dataSnapshot.getValue(Staff.class);
                if(staff != null){
                    staff.setKey(dataSnapshot.getKey());
                    listStaff.add(dataSnapshot.getValue(Staff.class));
                    listViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Staff staff = dataSnapshot.getValue(Staff.class);
                if(staff != null){
                    String key = dataSnapshot.getKey();
                    for(int i=0;i<listStaff.size();i++){
                        Staff staff1 = listStaff.get(i);
                        if(staff1.getKey().equals(key)){
                            listStaff.set(i, staff);
                            listViewAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                listStaff.remove(dataSnapshot.getValue(Staff.class));
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void addSingleEventListener(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void setListViewItemListener(){
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("edit", true);
            bundle.putParcelable("staff", Parcels.wrap(listStaff.get(i)));
            Intent intent = new Intent(this, EditStaffActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void setListViewLongClickListener(){
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Staff staff = listStaff.get(i);
            new AlertDialog.Builder(this)
                    .setTitle("Delete " + staff.getFirstName() + " " + staff.getLastName())
                    .setMessage("Do you want to delete the selected record?")
                    .setPositiveButton("Delete", (dialogInterface, i1) -> {
                        databaseReference.child(staff.getKey()).removeValue();
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i12) -> {
                        dialogInterface.dismiss();
                    })
                    .create()
                    .show();
            return true;
        });
    }

    private void setFabClickListener() {
        fab.setOnClickListener(e -> {
            startActivity(new Intent(this, EditStaffActivity.class));
        });
    }
}