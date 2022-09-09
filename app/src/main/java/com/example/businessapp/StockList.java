package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class StockList extends AppCompatActivity {

    TextView textStockList;
    FirebaseFirestore db;
    private final String TAG = "Match";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        textStockList = findViewById(R.id.textStockList);

        Button btnBackToStock = findViewById(R.id.btnBackToStock);

        btnBackToStock.setOnClickListener(v -> {
            startActivity(new Intent(StockList.this, StockManagement.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        db.collection("stocks")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        StringBuilder data = new StringBuilder();
                        int i = 1;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Stock stock = document.toObject(Stock.class);

                            if(stock.getStockName() == null) continue;

                            data.append(i).append(". ").append(stock.getStockName()).append("  ").append(stock.getStockQuantity()).append("  ").append(stock.getStockPrice()).append("\n");
                            i++;
                        }

                        textStockList.setText(String.format("%s", data));

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

}

