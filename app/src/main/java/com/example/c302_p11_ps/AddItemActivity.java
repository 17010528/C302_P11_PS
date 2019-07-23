package com.example.c302_p11_ps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddStudentActivity";

    private EditText etName, etAge;
    private Button btnAdd;
    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;

    // TODO: Task 1 - Declare Firebase variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etName = (EditText) findViewById(R.id.editTextName);
        etAge = (EditText) findViewById(R.id.editTextAge);
        btnAdd = (Button) findViewById(R.id.buttonAdd);

        db = FirebaseFirestore.getInstance();

        colRef = db.collection("inventory");
        docRef = colRef.document("1a8WzWZeFLNbPTeIB3pe");



        //TODO: Task 3: Get real time updates from firestore by listening to collection "students"

        // TODO: Task 2: Get FirebaseFirestore instance and collection reference to "students"


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Task 3: Retrieve name and age from EditText and instantiate a new Student object
                //TODO: Task 4: Add student to database and go back to main screen
                String name = etName.getText().toString();
                double age = Double.parseDouble(etAge.getText().toString());

                Inventory newItem = new Inventory(age, name);

                        colRef.add(newItem)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddItemActivity.this, "Student added into Firestore", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                finish();

            }
        });


    }
}
