package br.com.hype.medan;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by IQBAL on 9/8/2016.
 */
public interface KirimSmsVerifikasiAPI {
    @FormUrlEncoded
    @POST("/androidapi/Event/send_sms")
    public void sendSmsVerifikasiAPI(
            @Field("nomor_hp") String nomor_hp,
            @Field("kode") String kode,
            Callback<Response> callback);
}
