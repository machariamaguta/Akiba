package com.akiba.data.remote;

/**
 * Created by Leyo on 17/02/2017.
 */
import com.akiba.data.model.RegisterPostData;
import com.akiba.data.model.SmsModel;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {
    @Headers("Content-Type: application/json")

    @POST("/savings/save")
    Observable<Response<RegisterPostData>> savePost(@Body RegisterPostData registerPostData);

    @Headers("Content-Type: application/json")

    @POST("/savings/saveMessages")
    Observable<Response<RegisterPostData>> saveMessages(@Body SmsModel smsModel);
}
