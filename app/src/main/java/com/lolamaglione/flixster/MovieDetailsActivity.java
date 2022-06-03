package com.lolamaglione.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.lolamaglione.flixster.databinding.ActivityMainBinding;
import com.lolamaglione.flixster.databinding.ActivityMovieDetailsBinding;
import com.lolamaglione.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    // the movie to display
    Movie movie;

    //the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster;
    TextView tvLanguage;

    private ActivityMovieDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_movie_details.xml -> ActivityMovieDetailsBinding
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);
        // resolve the view objects, originally was found with findViewByID;
        tvTitle = binding.tvTitle;
        tvOverview = binding.tvOverview;
        rbVoteAverage = binding.rbVoteAverage;
        ivPoster = binding.ivPoster;
        tvLanguage = binding.tvLanguage;
        //unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("showing details for %s", movie.getTitle()));

        //set teh title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvLanguage.setText("Language: " + movie.getLanguage());
        //vote average is 0.10, convert to 0.5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage/2.0f);
        String imageURL;
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageURL = movie.getBackdropPath();
        } else {
            imageURL = movie.getPosterPath();
        }
        //get image poster and round corners
        Glide.with(this).load(imageURL).transform(new CenterInside(),new RoundedCorners(15)).placeholder(R.drawable.flicks_movie_placeholder).error(R.drawable.flicks_movie_placeholder).into(ivPoster);
    }
}