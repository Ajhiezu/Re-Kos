package com.jedu.re_kos.aatoken;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.R;

import java.util.Random;

public class NotificationServices extends FirebaseMessagingService {

    private static final String TAG = "CombinedMessagingService";
    private static final String CHANNEL_ID = "Default";

    // Callback ketika token FCM diperbarui
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "New token: " + token);

        // Kirim token ke server jika diperlukan
        sendTokenToServer(token);
    }

    // Callback ketika menerima pesan
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Log sumber pesan
        Log.d(TAG, "From: " + (remoteMessage.getFrom() != null ? remoteMessage.getFrom() : "Unknown"));

        // Cek apakah pesan berisi notifikasi
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            Log.d(TAG, "Notification Title: " + title);
            Log.d(TAG, "Notification Body: " + body);

            // Tampilkan notifikasi
            showNotification(title, body);
        }

        // Cek apakah pesan berisi data
        if (!remoteMessage.getData().isEmpty()) {
            Log.d(TAG, "Message Data Payload: " + remoteMessage.getData());
            // Anda dapat menangani payload data di sini jika diperlukan
        }
    }

    private void sendTokenToServer(String token) {
        // Tambahkan logika untuk mengirim token ke server Anda
        Log.d(TAG, "Token sent to server: " + token);
    }

    private void showNotification(String title, String body) {
        // Intent ke MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // PendingIntent untuk menangani klik notifikasi
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        // Bangun notifikasi
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // Ganti dengan ikon aplikasi Anda
                .setContentTitle(title != null ? title : "Default Title")
                .setContentText(body != null ? body : "Default Body")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        // Notifikasi Manager
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Buat NotificationChannel untuk Android 8.0 ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Default Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel untuk notifikasi FCM");
            manager.createNotificationChannel(channel);
        }

        // Trigger notifikasi dengan ID unik
        manager.notify(new Random().nextInt(), builder.build());
    }
}
