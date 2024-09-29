package com.myrecipeap;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView; // Import TextView
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView txtSignupLink; // Declare the TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity); // Ensure this matches the XML filename

        db = new DatabaseHelper(this);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignupLink = findViewById(R.id.txtSignupLink); // Initialize the TextView

        // Handle login process
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString(); // Changed variable name from email to username
                String password = edtPassword.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean verify = db.verifyUser(username, password); // Pass username instead of email
                    if (verify) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Redirect to the next activity
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Link to SignupActivity
        txtSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
