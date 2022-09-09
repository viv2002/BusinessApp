package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        Button mBtnLogin = findViewById(R.id.login);

        mBtnLogin.setOnClickListener(v -> loginUserAccount());
    }
    @Override
    public void onStart() {
        super.onStart();

        // if user logged in, go to sign-in screen
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, BusinessActivity.class));
            finish();
        }
    }

    // Take email id and password from user
    // and if it matches the already present id,password pair
    // redirect the user to home page
    private void loginUserAccount()
    {

        // Take input from user into the edit text fields
        // and store them into string variables
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // Check whether the email field is non-empty
        // if empty show a message to enter email
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Check whether the password field is non-empty
        // if empty show a message to enter email
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Check whether the email, password is valid
        // and if valid redirect the user to Home page
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Login successful!!",
                                        Toast.LENGTH_LONG)
                                        .show();

                                // if sign-in is successful redirect the user to home page
                                Intent intent
                                        = new Intent(LoginActivity.this,
                                        BusinessActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {

                                // sign-in failed show login fail message
                                Toast.makeText(getApplicationContext(),
                                        "Login failed!!",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
    }
}
