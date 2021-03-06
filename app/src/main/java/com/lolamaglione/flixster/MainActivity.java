package com.lolamaglione.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.facebook.stetho.common.ArrayListAccumulator;
import com.lolamaglione.flixster.adapters.MovieAdapter;
import com.lolamaglione.flixster.databinding.ActivityMainBinding;
import com.lolamaglione.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=e3217c18e977cbb4240ebafe80b4c253";
    public static String TAG = "MainActivity";
    List<Movie> movies;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_main.xml -> ActivityMainBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);
       // setContentView(R.layout.activity_main);
        RecyclerView rvMovies = binding.rvMovies;
                //findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        //Create and adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        //Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);
        // set a layout manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hut json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}