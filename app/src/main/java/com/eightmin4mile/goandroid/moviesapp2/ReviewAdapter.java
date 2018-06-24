package com.eightmin4mile.goandroid.moviesapp2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Review;

import java.util.List;

/**
 * Created by goandroid on 6/23/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private static final String TAG = "ReviewAdapter";

    private Context mContext;

    private List<Review> reviewList;

    public ReviewAdapter(Context context){
        mContext = context;
    }


    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_review_layout, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review reviewItem = reviewList.get(position);


        holder.authorTextView.setText("Written by " + reviewItem.getAuthor() + ":");
        holder.contentTextView.setText(reviewItem.getContent());

    }

    @Override
    public int getItemCount() {
        return reviewList == null ? 0 : reviewList.size();
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewData) {

        this.reviewList = reviewData;
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView authorTextView;
        TextView contentTextView;

        public ReviewViewHolder(View itemView){
            super(itemView);

            authorTextView = itemView.findViewById(R.id.tv_review_author);
            contentTextView = itemView.findViewById(R.id.tv_review_content);
        }
    }
}
