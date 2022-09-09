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

public class AddSoldRecord extends AppCompatActivity {

    private EditText fieldStockName, fieldStockQuantity;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sold_record);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        fieldStockName = findViewById(R.id.fieldStockName);
        fieldStockQuantity = findViewById(R.id.fieldStockQuantity);
        Button btnAddSoldItem = findViewById(R.id.btnAddSoldItem);

        btnAddSoldItem.setOnClickListener(v -> addSoldItemDetail());
    }

    // Function to take details of sold item i.e., stock name and quantity
    // check for the item in database and if available
    // update the quantity of stock and update
    // the account balance accordingly
    private void addSoldItemDetail() {
        // Take the input from user into edit fields and convert them into string
        String stockName = fieldStockName.getText().toString();
        String stockQuantity = fieldStockQuantity.getText().toString();

        // Check whether the stock name field is non-empty
        // if empty show a message to enter it.
        if (TextUtils.isEmpty(stockName)) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Stock Name!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Check whether the stock quantity field is non-empty
        // if empty show a message to enter it.
        if (TextUtils.isEmpty(stockQuantity)) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Stock Quantity!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Find the stock item in the database using the entered name
        // if available update the quantity of stock and update
        // the account balance accordingly
        DocumentReference docRef = db.collection("stocks").document(stockName);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Stock stocks = documentSnapshot.toObject(Stock.class);

            if(stocks != null && stocks.getStockName().equals(stockName)){
                // stock name found update its quantity and main account balance
                float priceStock = Float.parseFloat(stocks.getStockPrice());
                float quantityStocks = Float.parseFloat(stocks.getStockQuantity());
                float updateQuantity = Float.parseFloat(stockQuantity);
                String newQuantity = Float.toString(quantityStocks - updateQuantity);

                stocks.setStockQuantity(newQuantity);
                db.collection("stocks").document(stockName).set(stocks);

                DocumentReference docRef1 = db.collection("account").document("balance");
                docRef1.get().addOnSuccessListener(documentSnapshot1 -> {
                    Balance balance = documentSnapshot1.toObject(Balance.class);

                    assert balance != null;
                    // balance record found update it
                    float curBalance = Float.parseFloat(balance.getAmount());
                    curBalance += priceStock*updateQuantity;
                    String newBalance = Float.toString(curBalance);

                    // update the balance record present in database
                    balance.setAmount(newBalance);
                    db.collection("account").document("balance").set(balance);

                    Toast.makeText(getApplicationContext(),
                            "Sold Record Updated Successfully.",
                            Toast.LENGTH_LONG)
                            .show();

                    // return back to previous page
                    startActivity(new Intent(AddSoldRecord.this, AccountManagement.class));
                    finish();
                });
            }
            else{
                // stock name not found in database show error message
                Toast.makeText(getApplicationContext(),
                        "Sorry Stock: "+ stockName +" Not Found. Add stock record first.",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}