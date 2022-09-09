package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);

        Button mBtnRegister = findViewById(R.id.btnregister);
        Button mBtnLogin = findViewById(R.id.btnlogin);

        mBtnRegister.setOnClickListener(v -> registerNewUser());
        mBtnLogin.setOnClickListener(v -> {
            Intent intent
                    = new Intent(MainActivity.this,
                    LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // if user logged in, go to sign-in screen
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, BusinessActivity.class));
            finish();
        }
    }

    private void registerNewUser() {

        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "Registration successful!",
                                Toast.LENGTH_LONG)
                                .show();

                        // if the user created intent to login activity
                        Intent intent
                                = new Intent(MainActivity.this,
                                BusinessActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {

                        // Registration failed
                        Toast.makeText(
                                getApplicationContext(),
                                "Registration failed!!"
                                        + " Please try again later",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }
}