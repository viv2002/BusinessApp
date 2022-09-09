package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class CustomerList extends AppCompatActivity {

    TextView textCustomerList;
    FirebaseFirestore db;
    private final String TAG = "Match";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        textCustomerList = findViewById(R.id.textCustomerList);

        Button btnBackToCustomer = findViewById(R.id.btnBackToCustomer);

        btnBackToCustomer.setOnClickListener(v -> {
            startActivity(new Intent(CustomerList.this, CustomerManagement.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        db.collection("customers")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        StringBuilder data = new StringBuilder();
                        int i = 1;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Customer customer = document.toObject(Customer.class);

                            if(customer.getCustomerName() == null) continue;

                            data.append(i).append(". ").append(customer.getCustomerName()).append("  ").append(customer.getPhoneNo()).append("  ").append(customer.getDebtAmount()).append("\n");
                            i++;
                        }

                        textCustomerList.setText(String.format("%s", data));

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

}
