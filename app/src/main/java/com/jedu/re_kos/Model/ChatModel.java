package com.jedu.re_kos.Model;

import java.util.List;

public class ChatModel {
    private String status;
    private List<ChatsData> data;

    // Getter dan Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ChatsData> getData() {
        return data;
    }

    public void setData(List<ChatsData> data) {
        this.data = data;
    }


    public static class ChatsData {
        private String nama;
        private String message;

        public ChatsData(String id_gambar, String id_sender, String id_user, String message, String nama, String time) {
            this.id_gambar = id_gambar;
            this.id_sender = id_sender;
            this.id_user = id_user;
            this.message = message;
            this.nama = nama;
            this.time = time;
        }

        public ChatsData(){

        }

        public String getId_sender() {
            return id_sender;
        }

        public void setId_sender(String id_sender) {
            this.id_sender = id_sender;
        }

        private String id_sender;


        public String getId_gambar() {
            return id_gambar;
        }

        public void setId_gambar(String id_gambar) {
            this.id_gambar = id_gambar;
        }

        private String id_gambar;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getId_user() {
            return id_user;
        }

        public void setId_user(String id_user) {
            this.id_user = id_user;
        }

        private String time;
        private String id_user;




    }


}