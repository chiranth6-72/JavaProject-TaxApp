package com.example.taxapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;


    TextInputEditText emailEdit, pwEdit;
    Button btnReg;
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
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        emailEdit = findViewById(R.id.email);
        pwEdit = findViewById(R.id.password);
        btnReg = findViewById(R.id.btnRegister);
        pgBar = findViewById(R.id.progressBar);
        tv = findViewById(R.id.loginNow);

        tv.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        btnReg.setOnClickListener(v -> {
            pgBar.setVisibility(View.VISIBLE);
            String email, password;
            email = Objects.requireNonNull(emailEdit.getText()).toString();
            password = Objects.requireNonNull(pwEdit.getText()).toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Register.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(Register.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pgBar.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "User successfully registered",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                pgBar.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "Registration failed",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        });


    }
}