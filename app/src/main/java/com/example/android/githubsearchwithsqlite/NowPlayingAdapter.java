package com.example.android.githubsearchwithsqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.githubsearchwithsqlite.data.NowPlayingRepo;

import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.SearchResultViewHolder>{
    private List<NowPlayingRepo> mSearchResultsList;
    private OnNowPlayingClickListener mResultClickListener;


    interface OnNowPlayingClickListener {
        void onNowPlayingClicked(NowPlayingRepo repo);
    }


    public NowPlayingAdapter(OnNowPlayingClickListener listener) {
        mResultClickListener = listener;
    }
    public void updateSearchResults(List<NowPlayingRepo> searchResultsList) {
        mSearchResultsList = searchResultsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSearchResultsList != null) {
            return mSearchResultsList.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_result_item, parent, false);

        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {

        holder.bind(mSearchResultsList.get(position));
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView mSearchResultTV;
        private ImageView mMovieImageTV;
        private TextView mPopularityTV;
        private TextView mReleasedayTV;
        private RatingBar mRatingBar;
        SearchResultViewHolder(View itemView) {
            super(itemView);
            mSearchResultTV = itemView.findViewById(R.id.tv_search_result);
            mMovieImageTV = itemView.findViewById(R.id.movie_icon);
            mPopularityTV = itemView.findViewById(R.id.popularity);
            mReleasedayTV = itemView.findViewById(R.id.release_day);
            mRatingBar = itemView.findViewById(R.id.sample_rate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mResultClickListener.onNowPlayingClicked(
                            mSearchResultsList.get(getAdapterPosition())
                    );
                }
            });
        }


        void bind(NowPlayingRepo repo) {
            mSearchResultTV.setText(repo.title);
            //Convert popularity
            int popularity = (int)repo.popularity;
            String popular = Integer.toString(popularity );
            mPopularityTV.setText("Popularity: " + popular);
            //-------------------------
            //Convert average vote

            //String avg = Double.toString(repo.vote_average);
            //mAverageVoteTV.setText("Average Vote: "+ avg);
            mRatingBar.setNumStars(5);
            mRatingBar.setRating(repo.vote_average/2);
            mReleasedayTV.setText("Release day: "+repo.release_date);
            //Cover poster of the movie
            String base = "https://image.tmdb.org/t/p/w500/";
            String iconURL = repo.poster_path;
            Glide.with(mMovieImageTV.getContext()).load(base+iconURL).into(mMovieImageTV);
        }
    }

}
