package com.jedu.re_kos.network;

import com.jedu.re_kos.Model.ImageKosResponse;
import com.jedu.re_kos.Model.KosModel;
import com.jedu.re_kos.Model.LoginRequest;
import com.jedu.re_kos.Model.LoginResponse;
import com.jedu.re_kos.Model.Notifikasi;
import com.jedu.re_kos.Model.request.UpdateRequest;
import com.jedu.re_kos.Model.response.DetailResponse;
import com.jedu.re_kos.Model.response.PembayaranResponse;
import com.jedu.re_kos.Model.response.UpdateRespon;
import com.jedu.re_kos.Model.response.UserResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @GET("data/{id}")
    Call<UserResponse> getDataById(@Path("id") int id);
    @GET("kos")Call<KosModel> kos();
    @GET("best")Call<KosModel> bestkos();
    @GET("terdekat")Call<KosModel> kosterdekat();

    @GET("getImageKos/{id}")
    Call<ImageKosResponse> getImageKos(@Path("id") String id);


    @POST("update")
    Call<UpdateRespon> updateUser(@Body UpdateRequest updateRequest);

    @GET("Notifikasi/{id_user}")
    Call<List<Notifikasi>> getNotifikasi(@Path("id_user") int userId);

    //    @Headers({"Accept: application/json", "Content-Type: multipart/form-data"})
    @Multipart
    @POST("pembayaran")
    Call<PembayaranResponse> konfirmPay(
            @Part("id_user") RequestBody idUser,
            @Part("id_kamar") RequestBody idKamar,
            @Part("id_kos") RequestBody idKos,
            @Part("totalkamar") RequestBody totalKamar,
            @Part("durasi") RequestBody durasi,
            @Part("harga") RequestBody harga,
            @Part("tanggal_penyewaan") RequestBody tanggalPenyewaan,
            @Part("waktu_penyewaan") RequestBody waktuSewa,
            @Part MultipartBody.Part buktiPembayaran
    );

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("imgProfile/{userId}") // Replace with the actual endpoint
    Call<ResponseBody> getUserProfileImage(@Path("userId") String userId);

    @Multipart
    @POST("upload") // Adjust this endpoint as needed
    Call<ResponseBody> uploadImage(
            @Part MultipartBody.Part file,
            @Part("user_id") RequestBody userId
    );

//    @GET("kos")
//    Call<KosModel.KostData> kos();

    @GET("detail/{id}")
    Call<DetailResponse>getDetailKos(@Path("id") int id);

    @FormUrlEncoded
    @POST("allkos")
    Call<KosModel> AllKos(@Field("lokasi") String lokasi, @Field("hargaawal") String harga_awal, @Field("hargaakhir") String harga_akhir);

}
