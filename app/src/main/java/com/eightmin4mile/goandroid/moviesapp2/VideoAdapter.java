package com.eightmin4mile.goandroid.moviesapp2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eightmin4mile.goandroid.moviesapp2.retrofitMovie.Video;

import java.util.List;

/**
 * Created by goandroid on 6/18/18.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{

    private static final String TAG = "VideoAdapter";

    private Context mContext;

    private List<Video> videoList;

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    final private ItemClickListener mItemClickListener;

    public VideoAdapter(Context context, ItemClickListener listener){
        mContext = context;
        mItemClickListener = listener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_video_layout, parent, false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video videoItem = videoList.get(position);

        holder.videoTitleView.setText(videoItem.getName());
    }

    @Override
    public int getItemCount() {
        return videoList == null ? 0 : videoList.size();
    }


    @Nullable
    public Video getItem(int position){
        return videoList.get(position);
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoData) {
        this.videoList = videoData;
//        for(Video v : videoData){
//            Log.d(TAG, "setVideoData: video name = " + v.getName());
//        }
        notifyDataSetChanged();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView videoTitleView;

        public VideoViewHolder(View itemView){
            super(itemView);
            videoTitleView = itemView.findViewById(R.id.tv_video_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = getAdapterPosition();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
