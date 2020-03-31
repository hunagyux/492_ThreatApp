package com.example.android.githubsearchwithsqlite;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.android.githubsearchwithsqlite.data.MovieSearchRepo;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.List;

public class RepoDetailActivity extends AppCompatActivity {
    public static final String EXTRA_GITHUB_REPO = "MovieSearchRepo";

    private MovieSearchRepo mRepo;
    private boolean mIsSaved = false;

    private SavedReposViewModel mViewModel;
    private SlidrInterface slidr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
        slidr = Slidr.attach(this);
        mViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedReposViewModel.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GITHUB_REPO)) {
            mRepo = (MovieSearchRepo)intent.getSerializableExtra(EXTRA_GITHUB_REPO);

            TextView repoNameTV = findViewById(R.id.tv_repo_name);
            repoNameTV.setText(mRepo.title);

            ImageView mMovieImageTV = findViewById(R.id.movie_image);
            String base = "https://image.tmdb.org/t/p/w500/";
            String iconURL = mRepo.poster_path;
            Glide.with(mMovieImageTV.getContext()).load(base+iconURL).into(mMovieImageTV);

            TextView repoDescriptionTV = findViewById(R.id.tv_repo_description);
            repoDescriptionTV.setText("Overview: "+mRepo.overview);
        }

        final ImageView repoBookmarkIV = findViewById(R.id.iv_repo_bookmark);
        repoBookmarkIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRepo != null) {
                    if (!mIsSaved) {
                        mViewModel.insertSavedRepo(mRepo);
                    } else {
                        mViewModel.deleteSavedRepo(mRepo);
                    }
                }
            }
        });

        mViewModel.getRepoByName(mRepo.title).observe(this, new Observer<MovieSearchRepo>() {
            @Override
            public void onChanged(MovieSearchRepo repo) {
                if (repo != null) {
                    mIsSaved = true;
                    repoBookmarkIV.setImageResource(R.drawable.ic_bookmark_black_24dp);
                } else {
                    mIsSaved = false;
                    repoBookmarkIV.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareRepo();
                return true;
            case R.id.action_view_repo:
                viewRepoOnWeb();
                return true;
            default:
                finish();
                return false;
                //return super.onOptionsItemSelected(item);
        }
    }

    private void viewRepoOnWeb() {
        if (mRepo != null) {
            String id = Integer.toString(mRepo.id);
            String base_url = "https://www.themoviedb.org/movie/";
            Uri repoUri = Uri.parse(base_url + id);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, repoUri);

            PackageManager pm = getPackageManager();
            List<ResolveInfo> activities = pm.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
            if (activities.size() > 0) {
                startActivity(webIntent);
            }
        }
    }

    private void shareRepo() {
        if (mRepo != null) {
            String shareText = getString(R.string.share_repo_text, mRepo.title, "Average vote :" + Double.toString(mRepo.vote_average));
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(shareIntent, null);
            startActivity(chooserIntent);
        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //CustomIntent.customType(this,"fadein-to-fadeout");
    }
}
