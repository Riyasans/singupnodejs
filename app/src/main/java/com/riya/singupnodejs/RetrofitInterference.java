package com.riya.singupnodejs;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterference {
    @POST("/login")
    Call<login> executeLogin(@Body HashMap<String,String>map);
    @POST("/singup")
    Call<Void> executeSingup (@Body HashMap<String,String>map);

}
