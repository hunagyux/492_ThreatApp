package com.example.android.githubsearchwithsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.githubsearchwithsqlite.data.NowPlayingRepo;
import com.example.android.githubsearchwithsqlite.data.Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class DashBoard extends AppCompatActivity implements NowPlayingAdapter.OnNowPlayingClickListener{

    private RecyclerView mSearchResultsRV;
    private NowPlayingAdapter mNowPlayingAdapter;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mErrorMessageTV;
    private NowPlayingViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashbaord);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashbaord:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        return true;
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext()
                                ,User.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;
                }
                return false;
            }
        });

        mSearchResultsRV = findViewById(R.id.rv_search_results);

        mSearchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRV.setHasFixedSize(true);

        mNowPlayingAdapter = new NowPlayingAdapter(this);
        mSearchResultsRV.setAdapter(mNowPlayingAdapter);

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mErrorMessageTV = findViewById(R.id.tv_error_message);


        mViewModel = new ViewModelProvider(this).get(NowPlayingViewModel.class);

        mViewModel.getSearchResults().observe(this, new Observer<List<NowPlayingRepo>>() {
            @Override
            public void onChanged(List<NowPlayingRepo> gitHubRepos) {
                mNowPlayingAdapter.updateSearchResults(gitHubRepos);
            }
        });

        mViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mSearchResultsRV.setVisibility(View.VISIBLE);
                    mErrorMessageTV.setVisibility(View.INVISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mSearchResultsRV.setVisibility(View.INVISIBLE);
                    mErrorMessageTV.setVisibility(View.VISIBLE);
                }
            }
        });
        doGenerateNowPlaying();
        //CustomIntent.customType(this,"fadein-to-fadeout");


    }

    private void doGenerateNowPlaying(){
        mViewModel.loadSearchResults();
    }


    @Override
    public void onNowPlayingClicked(NowPlayingRepo repo) {

    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
