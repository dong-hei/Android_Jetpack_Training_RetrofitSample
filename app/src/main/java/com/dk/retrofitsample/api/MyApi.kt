package com.dk.retrofitsample.api

import com.dk.retrofitsample.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {

    @GET("posts/1")
    suspend fun getPost1() : Post

    @GET("posts/{num}")
    suspend fun getPostNum(@Path("num") num : Int ) : Post

    @GET("posts")
    suspend fun getPostAll() : List<Post>
}