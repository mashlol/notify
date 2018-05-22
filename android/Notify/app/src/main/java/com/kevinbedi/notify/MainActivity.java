package com.kevinbedi.notify;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements GcmTokenManager.Listener {

    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                GcmTokenManager.storeToken(MainActivity.this);
            } else {
                signIn();
            }
        }
    };

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressBar mProgressBar;
    private View mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GcmTokenManager.setListener(this);

        mProgressBar = findViewById(R.id.progress_bar);
        mContainer = findViewById(R.id.container);

        mAuth.addAuthStateListener(mAuthListener);

        if (mAuth.getCurrentUser() != null) {
            updateText();
        } else {
            signIn();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAuth.removeAuthStateListener(mAuthListener);
        GcmTokenManager.removeListener();
    }

    private void signIn() {
        mAuth.signInAnonymously();
    }

    private void updateText() {
        String token = GcmTokenManager.getExistingToken(this);

        mContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);

        TextView idTextView = findViewById(R.id.identifier);
        idTextView.setText(token);
    }

    @Override
    public void onTokenGenerated() {
        updateText();
    }
}
