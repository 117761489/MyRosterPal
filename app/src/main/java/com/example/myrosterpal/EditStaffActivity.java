package com.example.myrosterpal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;


public class EditStaffActivity extends Activity {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button btnSave;

    private DatabaseReference databaseReference;

    private Staff staff = new Staff();

    private boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        initUI();
        setButtonSaveOnClickListener();
        handleBundle();
        initUIFromStaff();
    }

    private void initUI(){
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        btnSave = findViewById(R.id.btnSave);
    }

    private void initUIFromStaff(){
        editTextFirstName.setText(staff.getFirstName());
        editTextLastName.setText(staff.getLastName());
        editTextAge.setText(staff.getAge() + "");
    }

    private void setButtonSaveOnClickListener(){
        btnSave.setOnClickListener(e -> {
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            int age = Integer.parseInt(editTextAge.getText().toString());

            staff.setFirstName(firstName);
            staff.setLastName(lastName);
            staff.setAge(age);

            if(edit){
                databaseReference.child(staff.getKey()).setValue(staff);
            }else{
                String key = databaseReference.push().getKey();
                staff.setKey(key);
                databaseReference.child(key).setValue(staff);
            }
            finish();
        });
    }

    private void handleBundle(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            edit = bundle.getBoolean("edit");
            if(edit){
                staff = Parcels.unwrap(bundle.getParcelable("staff"));
            }
        }
    }
}
