package com.kevinbedi.notify.notify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

public class MainActivity extends AppCompatActivity {

    static final String UPDATE_TEXT_INTENT_ACTION = "updateTextIntentAction";

    private static final String INSTRUCTIONS = "To register, type:\n$ notify -r %s\n\n" +
            "After registering, you can use notify to send push notifications to your phone.\n\n" +
            "A common usecase is:\n$ someLongRunningCommand || notify\n\nThis will send a push " +
            "to your phone when the command has competed, regardless of success or failure.";

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTextAndSubscribe();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mBroadcastReceiver, new IntentFilter(UPDATE_TEXT_INTENT_ACTION));
    }

    private void updateTextAndSubscribe() {
        String id = ParseInstallation.getCurrentInstallation().getObjectId();
        if (id != null) {
            TextView idTextView = (TextView) findViewById(R.id.identifier);
            TextView instructionsTextView = (TextView) findViewById(R.id.instructions);

            idTextView.setText(id);
            instructionsTextView.setText(String.format(INSTRUCTIONS, id));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
