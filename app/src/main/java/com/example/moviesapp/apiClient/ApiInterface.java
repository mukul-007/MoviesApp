package com.example.moviesapp.apiClient;

import com.example.moviesapp.model.Movies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "https://api.themoviedb.org/3/";

    public  static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    @GET("movie/popular")
    Call<Movies> getMovies(
            @Query("api_key") String apiKey
    );
}
