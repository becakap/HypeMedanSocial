package br.com.hype.medan;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Mohammad IQBAL on 9/8/2016.
 * Email : iqbalhood@gmail.com
 * Masukkan nomor hp agar bisa diverifikasi dan generate verification code
 */
public interface CreateVerifikasiKodeAPI {
    @FormUrlEncoded
    @POST("/androidapi/create_verifikasi_hp.php")
    public void createVerifikasi(
            @Field("nomor_hp") String nomor_hp,
            @Field("kode") String kode,
            Callback<Response> callback);
}
