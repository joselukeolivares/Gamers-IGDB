package com.example.myapplicationstyle.ui.videos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationstyle.DataBase.VideoEntry;
import com.example.myapplicationstyle.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class Videos_Adapter extends RecyclerView.Adapter<Videos_Adapter.vh> {
    private ArrayList<VideoEntry> videosList=new ArrayList<>();
    onClickVideosAdapter onClickVideosAdapterObj;
    Context context;

    public void setData(ArrayList<VideoEntry> videosListArray) {
        videosList=videosListArray;
    }

    public interface onClickVideosAdapter{
        void onClickVideo(String video_id);
    }

    public Videos_Adapter(onClickVideosAdapter onClickVideosAdapter){
        this.onClickVideosAdapterObj=onClickVideosAdapter;
    }

    @NonNull
    @Override
    public vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        int layout=R.layout.videos_view_holder;

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layout,parent,false);


        return new vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vh holder, int position) {
        VideoEntry video=videosList.get(position);
        String video_no=video.getName();
        holder.video_label.setText(context.getString(R.string.video_label_view_holder)+(position+1));
        holder.video_type.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        if(videosList.size()==0){
            return 0;
        }else{
            return videosList.size();
        }
    }

    class vh extends RecyclerView.ViewHolder  implements View.OnClickListener{

        ImageView youtube;
        TextView video_label;
        TextView video_type;

        public vh(@NonNull View itemView) {
            super(itemView);
            youtube=(ImageView)itemView.findViewById(R.id.youtube_icon_view_holder);
            video_label=(TextView)itemView.findViewById(R.id.video_name_view_holder);
            video_type=(TextView)itemView.findViewById(R.id.video_type_view_holder);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            String video_id=videosList.get(getAdapterPosition()).getVideo_id();
            onClickVideosAdapterObj.onClickVideo(video_id);
        }
    }
}
