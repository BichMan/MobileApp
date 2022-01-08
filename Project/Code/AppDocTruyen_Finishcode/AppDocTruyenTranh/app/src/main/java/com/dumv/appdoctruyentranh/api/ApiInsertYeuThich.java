package com.dumv.appdoctruyentranh.api;

import com.dumv.appdoctruyentranh.model.ResponseBodyDTO;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInsertYeuThich {
    @FormUrlEncoded
    @POST("/insertYeuThich.php")
    Observable<ResponseBodyDTO> insertYeuThich(@Field("tenTruyen") String tenTruyen, @Field("tenChap") String tenChap,
                                               @Field("LinkAnh") String LinkAnh, @Field("id_truyen") String id_truyen,
                                               @Field("id_user") String id_user);
}
