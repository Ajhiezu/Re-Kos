package com.jedu.re_kos.Model;

import java.util.List;

public class ResponModel {
    private DataWrapper data;
    private String status;

    public ResponModel(DataWrapper data){
        this.data = data;
    }

    public DataWrapper getData() {
        return data;
    }

    public void setData(DataWrapper data) {
        this.data = data;
    }

    public static class DataWrapper{
        private List<KosModel> kosModel;

        public DataWrapper(List<KosModel> kosModel){
            this.kosModel = kosModel;
        }

        public List<KosModel> getKosModel() {
            return kosModel;
        }

        public void setKosModel(List<KosModel> kosModel) {
            this.kosModel = kosModel;
        }
    }
}
