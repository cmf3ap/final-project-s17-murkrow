// Based Google's Firebase example code: https://github.com/firebase/quickstart-android/blob/master/auth/app/src/main/java/com/google/firebase/quickstart/auth/EmailPasswordActivity.java

package cs4720.cs.virginia.edu.hoowantsbrunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.text.TextUtils;
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

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Login";

    private boolean signIn = false;

    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.verify_email_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

                // updateUI(user);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount: " + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn: " + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete: " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                } else {
                    signIn = true;
                    updateUI(null);
                }
            }
        });
    }



    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        findViewById(R.id.verify_email_button).setEnabled(false);

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                findViewById(R.id.verify_email_button).setEnabled(true);
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(Login.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
        } else {

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.email_sign_in_button) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            if (signIn == true) {
                goToMainActivity(v);
            }
        } else if (i == R.id.sign_out_button){
            signOut();
        } else if (i == R.id.verify_email_button) {
            sendEmailVerification();
        }
    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

