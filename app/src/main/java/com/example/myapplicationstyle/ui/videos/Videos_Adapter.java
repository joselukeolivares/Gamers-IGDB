package com.example.myapplicationstyle.ui.videos;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Videos_Adapter extends RecyclerView.Adapter<Videos_Adapter.vh> {

    onClickVideosAdapter onClickVideosAdapterObj;

    public interface onClickVideosAdapter{
        void onClickVideo(String video_id);
    }

    public Videos_Adapter(onClickVideosAdapter onClickVideosAdapter){
        this.onClickVideosAdapterObj=onClickVideosAdapter;
    }

    @NonNull
    @Override
    public vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull vh holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class vh extends RecyclerView.ViewHolder {
        public vh(@NonNull View itemView) {
            super(itemView);
        }
    }
}
