package com.dumv.appdoctruyentranh.api;

import com.dumv.appdoctruyentranh.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiGetUser {
    @GET("/getUser.php")
    Observable<List<User>> getUser();

}
