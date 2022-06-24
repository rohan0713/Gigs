package com.financebazaar.gigs;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        getMessage(message.getNotification().getTitle(), message.getNotification().getBody());
    }

    private void getMessage(String title, String body) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myChannel")
                .setSmallIcon(R.mipmap.insta_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(101, builder.build());
    }
}
