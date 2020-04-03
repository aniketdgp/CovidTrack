package com.aniket.covidtrack;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage.getNotification().getTitle() , remoteMessage.getNotification().getBody());



    }

    public void showNotification(String title , String Message){


        String urlString = "https://www.google.com/";
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , "covid")
                .setContentTitle(title)
                .setTicker("COVID-19 Notification")
                .setSmallIcon(R.drawable.about)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setContentText(Message);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999,builder.build());




    }

}