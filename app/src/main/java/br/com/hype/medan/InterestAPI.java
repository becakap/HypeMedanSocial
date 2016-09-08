package br.com.hype.medan;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Mohammad Iqbal on 9/7/2016.
 * Email : iqbalhood@gmail.com
 */


public interface InterestAPI {
    @GET("/androidapi/list_interest.php")
    public void getInterest(Callback<List<Interest>> response);
}
