package com.example.myapplicationstyle.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationstyle.DataBase.GameEntry;
import com.example.myapplicationstyle.R;

import java.util.ArrayList;

public class Games_fragment extends Fragment implements gamesAdapter.onClickAdapter{

    ArrayList<GameEntry> games=new ArrayList<>();
    gamesAdapter gamesAdapterObj;
    GridLayoutManager layoutManager;



    Games_fragment(){

    }

    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.games_fragment_layout,container,false);

        layoutManager=new GridLayoutManager(inflater.getContext(),2);
        gamesAdapterObj=new gamesAdapter(this);

        RecyclerView recyclerView=(RecyclerView)rootView.findViewById(R.id.games_recycler_view);
        recyclerView.setAdapter(gamesAdapterObj);
        recyclerView.setLayoutManager(layoutManager);
        updateDataAdapter();
        return rootView;
    }

    public void setGamesData(ArrayList<GameEntry> data ){
        games=data;

    }

    public void updateDataAdapter(){
        gamesAdapterObj.updateData(this.games);
        gamesAdapterObj.notifyDataSetChanged();
    }

    @Override
    public void onClick(GameEntry gameEntry) {

    }
}
