package com.example.android.githubsearchwithsqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.githubsearchwithsqlite.data.MovieSearchRepo;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.SearchResultViewHolder>{
    private List<MovieSearchRepo> mSearchResultsList;
    private onMovieSearchClicked mResultClickListener;
    /////////////////////////////////
    //private User uResultClickListener;

    //public MovieSearchAdapter(User user) {
       // uResultClickListener = user;
    //}

    /////////////////////////////////////////////////////////

    interface onMovieSearchClicked {
        void onMovieSearchClicked(MovieSearchRepo repo);
    }

    public MovieSearchAdapter(onMovieSearchClicked listener) {
        mResultClickListener = listener;
    }
    public void updateSearchResults(List<MovieSearchRepo> searchResultsList) {
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
        View view = inflater.inflate(R.layout.movie_search_item, parent, false);
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
        private TextView mAverageVoteTV;

        SearchResultViewHolder(View itemView) {
            super(itemView);
            mSearchResultTV = itemView.findViewById(R.id.tv_search_result);
            mMovieImageTV = itemView.findViewById(R.id.movie_icon);
            mPopularityTV = itemView.findViewById(R.id.popularity);
            mAverageVoteTV = itemView.findViewById(R.id.average_vote);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mResultClickListener.onMovieSearchClicked(
                            mSearchResultsList.get(getAdapterPosition())
                    );
                }
            });
        }


        void bind(MovieSearchRepo repo) {
            mSearchResultTV.setText(repo.title);
            //Convert popularity
            int popularity = (int)repo.popularity;
            String popular = Integer.toString(popularity );
            mPopularityTV.setText("Popularity: " + popular);
            //-------------------------
            //Convert average vote

            String avg = Double.toString(repo.vote_average);
            mAverageVoteTV.setText("Average Vote: "+ avg);
            //Cover poster of the movie
            String base = "https://image.tmdb.org/t/p/w500/";
            String iconURL = repo.poster_path;
            Glide.with(mMovieImageTV.getContext()).load(base+iconURL).into(mMovieImageTV);
        }
    }
}
