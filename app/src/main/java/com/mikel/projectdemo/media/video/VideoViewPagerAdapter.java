package com.mikel.projectdemo.media.video;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.mikel.projectdemo.R;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


public class VideoViewPagerAdapter extends RecyclerView.Adapter<VideoViewPagerAdapter.VideoViewHolder> {
    private Context mContext;
    private List<String> mVieoUrls = new ArrayList<>();


    public VideoViewPagerAdapter(Context context) {
        super();
        this.mContext = context;
    }

    public void setDataList(List<String> videoUrls) {
        mVieoUrls.clear();
        mVieoUrls.addAll(videoUrls);
        notifyDataSetChanged();
        Log.d("Video_Play_TAG", "setDataList" );
    }

    public void addDataList(List<String> videoUrls) {
        mVieoUrls.addAll(videoUrls);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewPagerAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.fragment_video_item, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewPagerAdapter.VideoViewHolder holder, int position) {
        holder.videoUrl = mVieoUrls.get(position);
        holder.itemView.setTag(position);
        Log.d("Video_Play_TAG", " on bind view holder pos = "+ position + " , url = " + holder.videoUrl);
    }

    @Override
    public int getItemCount() {
        return mVieoUrls.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public SimpleExoPlayerView mVideoView;
        public String videoUrl;

        VideoViewHolder(View itemView) {
            super(itemView);
            mVideoView = itemView.findViewById(R.id.video_view);
        }
    }

    public String getUrlByPos(int pos) {
        return mVieoUrls.get(pos);
    }
}
