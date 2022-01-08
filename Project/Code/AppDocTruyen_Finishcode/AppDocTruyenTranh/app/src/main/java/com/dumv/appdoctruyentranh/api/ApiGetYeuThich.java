package com.dumv.appdoctruyentranh.api;

import com.dumv.appdoctruyentranh.model.YeuThich;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiGetYeuThich {
    @FormUrlEncoded
    @POST("/getDataYeuThich.php")
    Observable<List<YeuThich>> listYeuThich(@Field("id_user") String id_user);
}
