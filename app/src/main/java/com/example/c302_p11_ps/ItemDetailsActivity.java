package com.example.c302_p11_ps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ItemDetailsActivity extends AppCompatActivity {

    private static final String TAG = "StudentDetailsActivity";

    private EditText etName, etAge;
    private Button btnUpdate, btnDelete;

    private Inventory student;

    // TODO: Task 1 - Declare Firebase variables
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        etName = findViewById(R.id.editTextName);
        etAge = findViewById(R.id.editTextAge);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("StudentID");

        // TODO: Task 2: Get FirebaseFirestore instance
        db = FirebaseFirestore.getInstance();
        final CollectionReference colRef = db.collection("inventory");
        //TODO: Task 3: Get document reference by the student's id and set the name and age to EditText
        final DocumentReference docRef = colRef.document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Inventory student = document.toObject(Inventory.class);
                        etAge.setText(student.getCost() +"");
                        etName.setText(student.getName());
                    }
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Task 4: Update Student record based on input given

                String name = etName.getText().toString();
                double age = Double.parseDouble(etAge.getText().toString());
                Inventory updateStudent = new Inventory(age,name);
                        docRef.set(updateStudent)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ItemDetailsActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ItemDetailsActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                            }
                        });


                Toast.makeText(getApplicationContext(), "Student record updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Task 5: Delete Student record based on student id

                docRef.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

                Toast.makeText(getApplicationContext(), "Student record deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
