package com.kevinbedi.notify;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotifyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(
                remoteMessage.getMessageId(),
                1,
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_paperplane)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setVibrate(new long[] { 150, 300, 150, 600})
                        .setAutoCancel(true)
                        .build());
        super.onMessageReceived(remoteMessage);
    }
}
