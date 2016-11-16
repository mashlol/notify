package com.kevinbedi.notify.notify;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String INSTRUCTIONS = "To register, type:\n$ notify -r %s\n\n" +
            "After registering, you can use notify to send push notifications to your phone.\n\n" +
            "A common use-case is:\n$ someLongRunningCommand ; notify\n\nThis will send a push " +
            "to your phone when the command has competed, regardless of success or failure.";

    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                updateText(user.getUid());
                GcmTokenManager.storeToken();
            } else {
                signIn();
            }
        }
    };

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressBar mProgressBar;
    private View mIdContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mIdContainer = findViewById(R.id.id_container);

        mAuth.addAuthStateListener(mAuthListener);

        if (mAuth.getCurrentUser() != null) {
            updateText(mAuth.getCurrentUser().getUid());
        } else {
            signIn();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void signIn() {
        mAuth.signInAnonymously();
    }

    private void updateText(String id) {
        mIdContainer.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);

        TextView idTextView = (TextView) findViewById(R.id.identifier);
        TextView instructionsTextView = (TextView) findViewById(R.id.instructions);

        idTextView.setText(id);
        instructionsTextView.setText(String.format(INSTRUCTIONS, id));
    }
}
