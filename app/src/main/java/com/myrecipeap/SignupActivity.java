package com.myrecipeap;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText edtUsername, edtEmail, edtPassword, edtConfirmPassword;
    Button btnSignup;
    TextView txtLoginLink; // Link to navigate to login screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity); // Updated file name

        db = new DatabaseHelper(this);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);
        txtLoginLink = findViewById(R.id.txtLoginLink);

        // Handle the signup process
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                if (username.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        // Check if the username already exists
                        boolean checkUsername = db.checkUsername(username);
                        if (!checkUsername) {
                            // Check if the email already exists
                            boolean checkEmail = db.checkEmail(email);
                            if (!checkEmail) {
                                boolean insert = db.insertUser(username, email, password);
                                if (insert) {
                                    Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    // Redirect to LoginActivity
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish(); // Optional: close the signup activity
                                } else {
                                    Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignupActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignupActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Link to LoginActivity
        txtLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Optional: close the signup activity
            }
        });
    }
}
