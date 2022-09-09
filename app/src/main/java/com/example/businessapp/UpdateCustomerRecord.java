package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateCustomerRecord extends AppCompatActivity {

    private EditText fieldCustomerName, fieldAmount;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer_record);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        fieldCustomerName = findViewById(R.id.fieldCustomerName);
        fieldAmount = findViewById(R.id.fieldAmount);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnBackToCustomer = findViewById(R.id.btnBackToCustomer);

        btnUpdate.setOnClickListener(v -> updateCustomerDetail());
        btnBackToCustomer.setOnClickListener(v -> {
            startActivity(new Intent(UpdateCustomerRecord.this, CustomerManagement.class));
            finish();
        });
    }

    // Function to take details of sold item i.e., stock name and quantity
    // check for the item in database and if available
    // update the quantity of stock and update
    // the account balance accordingly
    private void updateCustomerDetail() {
        // Take the input from user into edit fields and convert them into string
        String customerName = fieldCustomerName.getText().toString();
        String amount = fieldAmount.getText().toString();

        // Check whether the stock name field is non-empty
        // if empty show a message to enter it.
        if (TextUtils.isEmpty(customerName)) {
            Toast.makeText(getApplicationContext(),
                            "Please Enter Customer Name!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Check whether the stock quantity field is non-empty
        // if empty show a message to enter it.
        if (TextUtils.isEmpty(amount)) {
            Toast.makeText(getApplicationContext(),
                            "Please Enter Amount!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Find the stock item in the database using the entered name
        // if available update the quantity of stock and update
        // the account balance accordingly
        DocumentReference docRef = db.collection("customers").document(customerName);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Customer customers = documentSnapshot.toObject(Customer.class);

            if(customers != null && customers.getCustomerName().equals(customerName)){
                // stock name found update its quantity and main account balance
                float previousAmount = Float.parseFloat(customers.getDebtAmount());
                float newAmount = Float.parseFloat(amount);
                String updatedAmount = Float.toString(previousAmount + newAmount);

                customers.setDebtAmount(updatedAmount);
                db.collection("customers").document(customerName).set(customers);

                Toast.makeText(getApplicationContext(),
                                "Record Updated Successfully",
                                Toast.LENGTH_LONG)
                        .show();
            }
            else{
                // stock name not found in database show error message
                Toast.makeText(getApplicationContext(),
                                "Sorry Customer: "+ customerName +" Not Found.",
                                Toast.LENGTH_LONG)
                        .show();
            }
        });
        // return back to previous page
        startActivity(new Intent(UpdateCustomerRecord.this, CustomerManagement.class));
        finish();
    }
}