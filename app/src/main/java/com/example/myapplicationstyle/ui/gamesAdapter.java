package com.example.myapplicationstyle.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationstyle.DataBase.GameEntry;
import com.example.myapplicationstyle.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class gamesAdapter extends RecyclerView.Adapter<gamesAdapter.VH> {

    ArrayList<GameEntry> gamesList=new ArrayList<>();
    onClickAdapter onClickAdapter;
    Context context;

    public interface  onClickAdapter{
        void onClick(GameEntry gameEntry);
    }

    public gamesAdapter(onClickAdapter onclickAdapter ){
        this.onClickAdapter=onclickAdapter;

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        int layout=R.layout.game_view_holder;

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layout,parent,false);
        return new VH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        GameEntry gameEntry=gamesList.get(position);
        String cover=gameEntry.getCoverUrl();
        String uri=context.getString(R.string.uriCover)+cover+context.getString(R.string.jpg);
        Log.i(this.getClass().getName(),uri);

        Picasso.with(context).load(uri).into(holder.poster);
        holder.game_name.setText(gameEntry.getName());
    }

    @Override
    public int getItemCount() {
        if(gamesList==null){
            return 0;
        }else{
            return gamesList.size();
        }

    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView poster;
        TextView game_name;
        public VH(View itemView){
            super(itemView);
            poster=itemView.findViewById(R.id.cover_view_holder);
            game_name=itemView.findViewById(R.id.game_name_value);

        }

        @Override
        public void onClick(View v) {
            GameEntry gameEntry=gamesList.get(getAdapterPosition());
            onClickAdapter.onClick(gameEntry);
        }
    }

    public void updateData(ArrayList<GameEntry> newData){
        gamesList=newData;
        Log.i(this.getClass().getName(),"Games List:"+gamesList.size());
    }

}
