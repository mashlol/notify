package com.kevinbedi.notify.notify;

import android.app.Application;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class NotifyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(
                this,
                "HQrMLZDevpTv2J1raSC6KATvlpNqqePPecUE0EgG",
                "dPK2CAhKJBbtInZKyFJnpJZFAStigdwLGAGlWWPN");
        ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Intent intent = new Intent(MainActivity.UPDATE_TEXT_INTENT_ACTION);
                LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(
                        getApplicationContext());
                lbm.sendBroadcast(intent);
                ParsePush.subscribeInBackground(
                        ParseInstallation.getCurrentInstallation().getObjectId());
            }
        });
    }
}
