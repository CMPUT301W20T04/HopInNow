package com.example.hopinnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    // establish the TAG of this activity:
    public static final String TAG = "LoginActivity";
    // initialize FirebaseAuth:
    private FirebaseAuth firebaseAuth;
    // UI components:
    private EditText email;
    private EditText password;
    private TextView loginWarn;
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        this.updateUI(currentUser);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.email = findViewById(R.id.regEmailEditText);
        this.password = findViewById(R.id.regPassword);
        this.loginWarn = findViewById(R.id.loginWarning);
        // set the warning as invisible
        this.loginWarn.setVisibility(View.INVISIBLE);
    }

    public void login(View v) {
        // get the strings
        String emailData = email.getText().toString();
        String passwordData = password.getText().toString();
        if (emailData.compareTo("") == 0 || passwordData.compareTo("") == 0) {
            this.loginWarn.setVisibility(View.VISIBLE);
            return;
        } else {
            this.loginWarn.setVisibility(View.INVISIBLE);
        }
        // verify the user
        firebaseAuth.signInWithEmailAndPassword(emailData, passwordData)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void toRegister(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Username or Password not found...",
                    Toast.LENGTH_LONG).show();
            this.email.setText("");
            this.password.setText("");
        }
    }
}
