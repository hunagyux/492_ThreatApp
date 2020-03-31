package com.example.android.githubsearchwithsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.githubsearchwithsqlite.data.MovieSearchRepo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class User extends AppCompatActivity implements MovieSearchAdapter.onMovieSearchClicked{

    private SavedReposViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.user);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashbaord:
                        startActivity(new Intent(getApplicationContext()
                                ,DashBoard.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;
                    case R.id.user:
                        return true;
                }
                return false;
            }
        });

        RecyclerView savedReposRV = findViewById(R.id.rv_saved_repos);
        savedReposRV.setLayoutManager(new LinearLayoutManager(this));
        savedReposRV.setHasFixedSize(true);

        final MovieSearchAdapter adapter = new MovieSearchAdapter( this);
        savedReposRV.setAdapter(adapter);

        mViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedReposViewModel.class);

        mViewModel.getAllRepos().observe(this, new Observer<List<MovieSearchRepo>>() {
            @Override
            public void onChanged(List<MovieSearchRepo> movieSearchRepos) {
                adapter.updateSearchResults(movieSearchRepos);
            }
        });
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onMovieSearchClicked(MovieSearchRepo repo) {
        Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra(RepoDetailActivity.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
        CustomIntent.customType(this,"fadein-to-fadeout");
    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
