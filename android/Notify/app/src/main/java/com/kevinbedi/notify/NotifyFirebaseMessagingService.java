package com.kevinbedi.notify;

import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.app.Notification.PRIORITY_MAX;

public class NotifyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(
                remoteMessage.getMessageId(),
                1,
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_paperplane)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setVibrate(new long[] { 150, 300, 150, 600})
                        .setSound(alarmSound)
                        .setPriority(PRIORITY_MAX)
                        .setAutoCancel(true)
                        .build());
        super.onMessageReceived(remoteMessage);
    }
}
