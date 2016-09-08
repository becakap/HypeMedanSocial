package br.com.hype.medan;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Mohammad IQBAL on 9/8/2016.
 * Email : iqbalhood@gmail.com
 * Verifikasi apakah nomor hp dan kode ada di website
 */


public interface VerifikasiHpAPI {
    @FormUrlEncoded
    @POST("/androidapi/cek_verifikasi_hp.php")
    public void verifikasiHPSET(
            @Field("nomor_hp") String nomor_hp,
            @Field("kode") String kode,
            Callback<Response> callback);
}
