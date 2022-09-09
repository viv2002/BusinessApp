package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerSearch extends AppCompatActivity {

    private EditText fieldCustomerName;
    TextView textPhoneNo, textDebtAmount;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        fieldCustomerName = findViewById(R.id.fieldCustomerName);
        Button btnSearchCustomerData = findViewById(R.id.btnSearchCustomerData);
        textPhoneNo = findViewById(R.id.textPhoneNo);
        textDebtAmount = findViewById(R.id.textDebtAmount);
        Button btnBackToCustomer = findViewById(R.id.btnBackToCustomer);

        btnSearchCustomerData.setOnClickListener(v -> searchCustomerRecord());
        btnBackToCustomer.setOnClickListener(v -> {
            startActivity(new Intent(CustomerSearch.this, CustomerManagement.class));
            finish();
        });
    }

    // Function to search whether an item is available in the store
    // and if available how much quantity is present
    private void searchCustomerRecord() {

        // Take the input from user into edit fields and convert them into string
        String customerName = fieldCustomerName.getText().toString();

        // Check whether the stock name field is non-empty
        // if empty show a message to enter it.
        if (TextUtils.isEmpty(customerName)) {
            Toast.makeText(getApplicationContext(),
                            "Please Enter Customer Name!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Find the stock item in the database using the entered name
        // if available show the details
        DocumentReference docRef = db.collection("customers").document(customerName);
        docRef.get().addOnSuccessListener(documentSnapshot -> {

            Customer customers = documentSnapshot.toObject(Customer.class);

            if(customers != null){
                // stock found
                // show the details
                textPhoneNo.setText(String.format("Customer Phone No: %s", customers.getPhoneNo()));
                textDebtAmount.setText(String.format("Customer Debt: %s", customers.getDebtAmount()));
            }
            else{
                // stock not found
                // show not found message
                Toast.makeText(getApplicationContext(),
                                "Sorry No Record Found With Name: "+ customerName +".",
                                Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
