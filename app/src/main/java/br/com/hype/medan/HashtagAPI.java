package br.com.hype.medan;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by IQBAL on 9/8/2016.
 */
public interface HashtagAPI {
    @FormUrlEncoded
    @POST("/androidapi/kirim_hastag.php")
    public void kirimHastagAPI(
            @Field("hastag_kirim") String hastag_kirim,
            @Field("email_kirim") String email_kirim,
            Callback<Response> callback);
}
