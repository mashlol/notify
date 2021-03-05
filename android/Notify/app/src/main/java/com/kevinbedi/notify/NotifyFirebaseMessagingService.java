package com.kevinbedi.notify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.app.Notification.PRIORITY_MAX;

public class NotifyFirebaseMessagingService extends FirebaseMessagingService {

    public static String CHANNEL_ID = "notify_channel_id";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.settings_file), MODE_PRIVATE);
        Boolean sound = prefs.getBoolean(getString(R.string.menu_sound), true);
        Boolean vibration = prefs.getBoolean(getString(R.string.menu_vibration), true);
        String title = null;
        String text = null;

        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            text = remoteMessage.getNotification().getBody();
        }
        if (title == null) {
            title = remoteMessage.getData().get("title");
        }
        if (text == null) {
            text = remoteMessage.getData().get("text");
        }

        manager.notify(
                remoteMessage.getMessageId(),
                1,
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_paperplane)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                        .setVibrate(vibration ? new long[]{150, 300, 150, 600} : new long[]{0L})
                        .setSound(sound ? Settings.System.DEFAULT_NOTIFICATION_URI : null)
                        .setLights(Color.BLUE, 1000, 500)
                        .setPriority(PRIORITY_MAX)
                        .setAutoCancel(true)
                        .build());
        super.onMessageReceived(remoteMessage);
    }

    public static void setupChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            CharSequence name = "Notify";
            String description = "Notifications from Notify";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{150, 300, 150, 600});
            notificationManager.createNotificationChannel(mChannel);
        }
    }
}
