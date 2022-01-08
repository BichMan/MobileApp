package com.dumv.appdoctruyentranh.api;

import com.dumv.appdoctruyentranh.model.ResponseBodyDTO;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiDeleteYeuThich {
    @FormUrlEncoded
    @POST("/deleteYeuThich.php")
    Observable<ResponseBodyDTO> deleteYeuThich(@Field("id_truyen") String id_truyen,
                                               @Field("id_user") String id_user);
}
