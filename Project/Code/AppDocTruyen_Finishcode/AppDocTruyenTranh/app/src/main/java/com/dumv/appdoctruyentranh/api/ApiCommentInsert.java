package com.dumv.appdoctruyentranh.api;

import com.dumv.appdoctruyentranh.model.Message;
import com.dumv.appdoctruyentranh.model.ResponseBodyDTO;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiCommentInsert {
    @FormUrlEncoded
    @POST("/insertBL.php")
    Observable<ResponseBodyDTO> insertData(@Field("id_user") String id_user, @Field("name_user") String name_user,
                                           @Field("message") String message, @Field("id_truyen") String id_truyen,
                                           @Field("date") String date);
}
