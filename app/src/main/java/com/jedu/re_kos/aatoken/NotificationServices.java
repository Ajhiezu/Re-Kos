package com.jedu.re_kos.aatoken;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationServices extends FirebaseMessagingService {
    private static final String TAG = "NotificationServices";

    public NotificationServices() {
        // Konstruktor default
    }

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
}
