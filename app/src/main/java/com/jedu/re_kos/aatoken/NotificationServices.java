package com.jedu.re_kos.aatoken;

//import android.util.Log;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//public class NotificationServices extends FirebaseMessagingService {
//    private static final String TAG = "NotificationServices";
//
//    public NotificationServices() {
//        // Konstruktor default
//    }
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // Log pesan yang diterima
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//        // Periksa apakah pesan berisi payload data
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//
//            // Tangani data payload
//            if (true) { // Ganti dengan logika yang sesuai
//                scheduleJob(); // Untuk tugas panjang
//            } else {
//                handleNow(); // Untuk tugas cepat
//            }
//        }
//
//        // Periksa apakah pesan berisi payload notifikasi
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//
//            // Anda dapat menambahkan logika untuk menampilkan notifikasi di sini
//            sendNotification(remoteMessage.getNotification().getBody());
//        }
//    }
//
//    @Override
//    public void onNewToken(String token) {
//        super.onNewToken(token);
//        Log.d(TAG, "Refreshed token: " + token);
//
//        // Kirim token ke server aplikasi Anda jika diperlukan
//        sendRegistrationToServer(token);
//    }
//
//    private void scheduleJob() {
//        // Implementasi pekerjaan panjang (misalnya, menggunakan WorkManager)
//        Log.d(TAG, "Scheduling long-running job.");
//    }
//
//    private void handleNow() {
//        // Implementasi pekerjaan cepat
//        Log.d(TAG, "Handling message within 10 seconds.");
//    }
//
//    private void sendNotification(String messageBody) {
//        // Implementasi untuk membuat notifikasi lokal
//        Log.d(TAG, "Creating notification: " + messageBody);
//    }
//
//    private void sendRegistrationToServer(String token) {
//        // Kirim token registrasi ke server aplikasi Anda
//        Log.d(TAG, "Sending token to server: " + token);
//    }
//}


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

public class NotificationServices extends FirebaseMessagingService {
    private static final String TAG = "NotificationServices";
    private static final String CHANNEL_ID = "Default";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Log pesan yang diterima
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Periksa apakah pesan berisi payload data
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            // Tangani data payload
            if (true) { // Ganti dengan logika yang sesuai
                scheduleJob(); // Untuk tugas panjang
            } else {
                handleNow(); // Untuk tugas cepat
            }
        }

        // Periksa apakah pesan berisi payload notifikasi
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            // Anda dapat menambahkan logika untuk menampilkan notifikasi di sini
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        // Kirim token ke server aplikasi Anda jika diperlukan
        sendRegistrationToServer(token);
    }
    private void scheduleJob() {
        // Implementasi pekerjaan panjang (misalnya, menggunakan WorkManager)
        Log.d(TAG, "Scheduling long-running job.");
    }

    private void handleNow() {
        // Implementasi pekerjaan cepat
        Log.d(TAG, "Handling message within 10 seconds.");
    }

    private void sendNotification(String messageBody) {
        // Implementasi untuk membuat notifikasi lokal
        Log.d(TAG, "Creating notification: " + messageBody);
    }

    private void sendRegistrationToServer(String token) {
        // Kirim token registrasi ke server aplikasi Anda
        Log.d(TAG, "Sending token to server: " + token);
    }

    private void showNotification(RemoteMessage remoteMessage) {
        // Intent untuk membuka MainActivity saat notifikasi diklik
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_MUTABLE);

        // Membuat builder untuk notifikasi
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // Ikon notifikasi
                .setContentTitle(remoteMessage.getNotification().getTitle()) // Judul notifikasi
                .setContentText(remoteMessage.getNotification().getBody()) // Isi notifikasi
                .setAutoCancel(true) // Auto cancel setelah diklik
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Prioritas
                .setContentIntent(pendingIntent); // Aksi ketika diklik

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Untuk Android Oreo (8.0) dan yang lebih baru, tambahkan NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        // Menampilkan notifikasi
        manager.notify(0, builder.build());
    }
}



