package com.example.pushfromserver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
// MyFirebaseMessagingService.java
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    //private static final String ONESIGNAL_APP_ID = "22902f86-87f3-4e13-8891-bb9ea385e71a";
    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL_ID = "Your_Channel_ID";
    private static final int NOTIFICATION_ID = 1;

    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Обработка полученного уведомления.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Проверяем, содержит ли уведомление данные.
        if (remoteMessage.getData().size() > 0) { Log.d(TAG, "Message data payload: " + remoteMessage.getData()); }

        // Проверяем, содержит ли уведомление тело.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            // Здесь вы можете выполнить любую обработку текста уведомления, например, вызвать метод для отображения уведомления.
            // В этом примере мы просто отображаем текст уведомления в логах.

            // Отображаем уведомление на устройстве.
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        }
    }

    private void showNotification(String title, String body) {
        // Создаем интент для открытия приложения при нажатии на уведомление.


        // Строим уведомление.
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true);

        // Отображаем уведомление.
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Проверяем, нужно ли создать канал уведомлений (для Android 8 и выше).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}


// работает с OneSignal
//OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

