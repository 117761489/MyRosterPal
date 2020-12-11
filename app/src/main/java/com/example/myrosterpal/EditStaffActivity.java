package com.example.myrosterpal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

//Code adapted from IS4447 lecture by Michael Gleeson - 03/12/20

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

    //UI if i want to add a new person
    private void initUI(){
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        btnSave = findViewById(R.id.btnSave);
    }

    //Ui if i want to edit a user
    private void initUIFromStaff(){
        editTextFirstName.setText(staff.getFirstName());
        editTextLastName.setText(staff.getLastName());
        editTextAge.setText(staff.getAge() + "");
    }

    //Passes value onto the database using the setValue method
    private void setButtonSaveOnClickListener(){
        btnSave.setOnClickListener(e -> {
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            int age = Integer.parseInt(editTextAge.getText().toString());

            staff.setFirstName(firstName);
            staff.setLastName(lastName);
            staff.setAge(age);

            //Using keys that google are automatically setting (.getKey)
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
