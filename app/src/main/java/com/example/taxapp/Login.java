package com.example.taxapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText emailEdit, pwEdit;
    Button btnLogin;
    ProgressBar pgBar;
    TextView tv;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),
                    BillsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        emailEdit = findViewById(R.id.email);
        pwEdit = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        pgBar = findViewById(R.id.progressBar2);
        tv = findViewById(R.id.regNow);

        tv.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            pgBar.setVisibility(View.VISIBLE);
            String email, password;
            email = Objects.requireNonNull(emailEdit.getText()).toString();
            password = Objects.requireNonNull(pwEdit.getText()).toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Login.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(Login.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            pgBar.setVisibility(View.GONE);

                            Toast.makeText(Login.this, "Login was successful.",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(),
                                    BillsActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            pgBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
        });
    }
}