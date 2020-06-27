package com.example.myapplicationstyle.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Games_fragment extends Fragment implements gamesAdapter.onClickAdapter{

    ArrayList<GameEntry> games=new ArrayList<>();
    gamesAdapter gamesAdapterObj;
    GridLayoutManager layoutManager;




    public Games_fragment(){

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


        games_fragment_itemSelectedObj.gameSelected(gameEntry);

    }

    public interface games_fragment_ItemSelected{
        public void gameSelected(GameEntry game);
    }

    games_fragment_ItemSelected games_fragment_itemSelectedObj;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            games_fragment_itemSelectedObj=(games_fragment_ItemSelected) context;

        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
