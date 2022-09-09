package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCustomerRecord extends AppCompatActivity {

    private EditText fieldCustomerName, fieldCustomerPhoneNo, fieldCustomerDebt;
    private final String TAG = "Match";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_record);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        fieldCustomerName = findViewById(R.id.fieldCustomerName);
        fieldCustomerPhoneNo = findViewById(R.id.fieldCustomerPhoneNo);
        fieldCustomerDebt = findViewById(R.id.fieldCustomerDebt);
        Button btnAddCustomer = findViewById(R.id.btnAddCustomer);
        Button btnBackToCustomer = findViewById(R.id.btnBackToCustomer);

        btnAddCustomer.setOnClickListener(v -> addCustomerDetail());

        btnBackToCustomer.setOnClickListener(v -> {
            startActivity(new Intent(AddCustomerRecord.this, CustomerManagement.class));
            finish();
        });
    }

    // Function to take details of customer detail i.e., name and quantity
    // check for the item in database and if available
    // update them

    private void addCustomerDetail() {
        // Take the input from user into edit fields and convert them into string
        String customerName = fieldCustomerName.getText().toString();
        String customerPhoneNo = fieldCustomerPhoneNo.getText().toString();
        String customerDebt = fieldCustomerDebt.getText().toString();


        // Check whether the customer name field is non-empty
        // if empty show a message to enter it.
        if (TextUtils.isEmpty(customerName)) {
            Toast.makeText(getApplicationContext(),
                            "Please Enter Customer Name!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Check whether phone no field is non-empty
        // if empty show a message to enter it.
        if (TextUtils.isEmpty(customerPhoneNo)) {
            Toast.makeText(getApplicationContext(),
                            "Please Enter Customer Phone No!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Find the customer name in the database using the entered name
        // if available return a message that record already present
        // otherwise add the record.
        DocumentReference docRef = db.collection("customers").document(customerName);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Customer customer = documentSnapshot.toObject(Customer.class);

            if(customer == null){
                // customer not found
                // add the details
                Customer newCustomer = new Customer(customerName, customerPhoneNo, customerDebt);
                docRef.set(newCustomer)
                        .addOnSuccessListener(aVoid -> {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                            Toast.makeText(getApplicationContext(),
                                            "Successfully added: "+ customerName +".",
                                            Toast.LENGTH_LONG)
                                    .show();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(TAG, "Error writing document", e);
                            Toast.makeText(getApplicationContext(),
                                            "Sorry! Error occurred.",
                                            Toast.LENGTH_LONG)
                                    .show();
                        });
            }
            else{
                // customer found
                // show found message
                Toast.makeText(getApplicationContext(),
                                "A customer already exist with name: "+ customerName +".",
                                Toast.LENGTH_LONG)
                        .show();
            }
        });
        //return;
    }
}
