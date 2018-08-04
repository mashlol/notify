package com.kevinbedi.notify;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.SharedPreferences;

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
    private Toolbar mToolbar;
    private boolean mSound;
    private boolean mVibration;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mPrEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GcmTokenManager.setListener(this);

        mProgressBar = findViewById(R.id.progress_bar);
        mContainer = findViewById(R.id.container);
        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mPrefs = getSharedPreferences(getString(R.string.settings_file), MODE_PRIVATE);
        mPrEdit = mPrefs.edit();
        mSound = mPrefs.getBoolean(getString(R.string.menu_sound), true);
        mVibration = mPrefs.getBoolean(getString(R.string.menu_vibration), true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.sound).setChecked(mSound);
        menu.findItem(R.id.vibration).setChecked(mVibration);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.sound:
                mSound = !item.isChecked();
                item.setChecked(mSound);
                mPrEdit.putBoolean(getString(R.string.menu_sound), mSound).apply();
                return true;
            case R.id.vibration:
                mVibration = !item.isChecked();
                item.setChecked(mVibration);
                mPrEdit.putBoolean(getString(R.string.menu_vibration), mVibration).apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
