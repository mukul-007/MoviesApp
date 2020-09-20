package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.moviesapp.apiClient.ApiInterface;
import com.example.moviesapp.model.Result;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView title, overview, userRating, releaseDate, genreTV;
    private ImageView posterImage, backdropImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();

        Intent intent = getIntent();
        Result r = (Result) intent.getSerializableExtra("Result");

        String poster_path = ApiInterface.IMAGE_BASE_URL + r.getPosterPath();

        String backdrop_path = ApiInterface.IMAGE_BASE_URL + r.getBackdropPath();

        Glide.with(this)
                .load(poster_path)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(posterImage);

        Glide.with(this)
                .load(backdrop_path)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(backdropImage);

        title.setText(r.getTitle());
        String genre = "";
        List<Integer> genreIds = r.getGenreIds();
        if(genreIds.size() == 1){
            genre = MainActivity.genreMap.get(genreIds.get(0)) + ".";
        }else {
            for (int id : genreIds) {
                genre += MainActivity.genreMap.get(id) + ", ";
            }
            genre = genre.substring(0, genre.length() - 2) + ".";
        }

        genreTV.setText(genre);
        overview.setText(r.getOverview());
        userRating.setText(String.valueOf(r.getVoteAverage()));
        releaseDate.setText(r.getReleaseDate());
    }

    private void initViews() {
        title = findViewById(R.id.detail_title);
        overview = findViewById(R.id.overview);
        userRating = findViewById(R.id.user_rating);
        releaseDate = findViewById(R.id.detail_release_date);
        posterImage = findViewById(R.id.detail_image);
        backdropImage = findViewById(R.id.detail_backdrop_image);
        genreTV = findViewById(R.id.detail_genre);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}