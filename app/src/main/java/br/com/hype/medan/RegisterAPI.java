package br.com.hype.medan;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by IQBAL on 9/3/2016.
 */
public interface RegisterAPI {

    @FormUrlEncoded
    @POST("/androidapi/create_member.php")
    public void insertUser(
            @Field("id_member") String id_member,
            @Field("id_sosmed") String id_sosmed,
            @Field("fb_gplus") String fb_gplus,
            @Field("lokasi") String lokasi,
            @Field("nama_depan") String nama_depan,
            @Field("nama_belakang") String nama_belakang,
            @Field("language") String language,
            @Field("ponsel") String ponsel,
            @Field("gender") String gender,
            @Field("tahun_lahir") String tahun_lahir,
            @Field("list_favorit") String list_favorit,
            @Field("catatan") String catatan,
            @Field("kode_promo") String kode_promo,
            @Field("status") String status,
            Callback<Response> callback);
}
