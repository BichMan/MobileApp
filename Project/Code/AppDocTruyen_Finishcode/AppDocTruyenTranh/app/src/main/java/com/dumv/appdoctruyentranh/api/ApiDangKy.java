package com.dumv.appdoctruyentranh.api;

import com.dumv.appdoctruyentranh.model.ResponseBodyDTO;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiDangKy {
    @FormUrlEncoded
    @POST("/insertUser.php")
    Observable<ResponseBodyDTO> insertUser(@Field("hoTen") String hoTen, @Field("username") String username,
                                           @Field("password") String password);
}
