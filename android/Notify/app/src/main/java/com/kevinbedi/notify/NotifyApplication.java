package com.kevinbedi.notify;

import android.app.Application;

public class NotifyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NotifyFirebaseMessagingService.setupChannel(this);
    }
}
