package com.jedu.re_kos.aatoken;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import android.util.Log;

public class NotifInstanceService extends FirebaseMessagingService {

    private static final String TAG = "NotifInstanceService";

    // Callback ketika token FCM diperbarui
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "New token: " + token);

        // Kirim token ke server Anda jika diperlukan
        sendTokenToServer(token);
    }

    // Callback ketika menerima pesan
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "Message received: " + remoteMessage.getData());

        // Tangani pesan notifikasi atau data di sini
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notification: " + remoteMessage.getNotification().getBody());
        }
    }

    private void sendTokenToServer(String token) {
        // Tambahkan logika untuk mengirim token ke server Anda
        Log.d(TAG, "Token sent to server: " + token);
    }
}
