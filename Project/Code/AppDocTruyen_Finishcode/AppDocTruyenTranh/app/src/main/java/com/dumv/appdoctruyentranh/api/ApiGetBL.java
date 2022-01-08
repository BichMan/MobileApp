package com.dumv.appdoctruyentranh.api;

import com.dumv.appdoctruyentranh.model.Message;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiGetBL {
    @GET("/getBinhLuan.php")
    Observable<List<Message>> getData();
}
