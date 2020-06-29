package com.example.myapplicationstyle.ui;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplicationstyle.DataBase.GameEntry;
import com.example.myapplicationstyle.GameAppWidget;
import com.example.myapplicationstyle.R;
import com.example.myapplicationstyle.games_service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Game_summary_fragment extends Fragment {
    private GameEntry game;

    private ImageView cover;
    private TextView game_name;
    private TextView  release;
    private TextView companies;
    private TextView member_count;
    private TextView critic_rating;
    private TextView genre;
    private TextView platform;
    private TextView summary;
    private  TextView want;
    private  TextView playing;
    private  TextView played;
    boolean favorite=false;


    onClickFavBtn onClickFavBtnObj;



    public interface onClickFavBtn{
        void clickingFavoriteBtn();
    }


    private View rootView;

    public Game_summary_fragment() {
    }
    FloatingActionButton favorite_floatingButton;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=container.getContext();
        rootView=inflater.inflate(R.layout.game_info_fragment,container,false);
        cover=(ImageView)rootView.findViewById(R.id.game_cover);
        game_name=(TextView)rootView.findViewById(R.id.game_name);
        release=(TextView)rootView.findViewById(R.id.game_release);
        companies=(TextView)rootView.findViewById(R.id.game_companies);
        member_count=(TextView)rootView.findViewById(R.id.member_count_value);
        critic_rating=(TextView)rootView.findViewById(R.id.critic_rating_value);
        genre=(TextView)rootView.findViewById(R.id.genre_value);
        platform=(TextView)rootView.findViewById(R.id.platforms_values);
        want=(TextView)rootView.findViewById(R.id.want_value);
        playing=(TextView)rootView.findViewById(R.id.playing_value);
        played=(TextView)rootView.findViewById(R.id.played_value);
        summary=(TextView) rootView.findViewById(R.id.game_summary);


        favorite_floatingButton=(FloatingActionButton)rootView.findViewById(R.id.favorite_floatingButton);

        favorite_floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(this.getClass().getName(),"Clicking FloatingButton");
                onClickFavBtnObj.clickingFavoriteBtn();


            }
        });

        if(game!=null){

            build_UI();
        }

        return rootView;
    }



    public void build_UI(){

        String uri=getString(R.string.uriCover)+game.getCoverUrl()+getString(R.string.jpg);
        Picasso.with(getContext()).load(uri).into(cover);
        String name=game.getName();
        game_name.setText(name!=null?name:"");

        games_service.updatingService(context,name,uri);


        long milliseconds=game.getFirst_release_date();
        Log.i(this.getClass().getName(),milliseconds+"");
        String date=getDate(milliseconds *1000);
        release.setText(date);

        String companie=game.getInvolved_companies();
        companies.setText(companie!=null?companie:"");

        int hype=game.getHypes();
        member_count.setText(hype+"");

        int critic_rating_value=game.getRating_count();
        critic_rating.setText(critic_rating_value+"");

        String genre_value=game.getGenres();
        genre.setText(genre_value!=null?genre_value:"");

        String platform_value=game.getPlatforms();
        platform.setText(platform_value!=null?platform_value:"");

        double  total_rating_count=game.getTotal_rating_count();
        want.setText(total_rating_count+"");

        playing.setText(((int)game.getTotal_rating())+"");
        played.setText(((int)game.getRating())+"");

        String summary_value=game.getSummary();
        summary.setText(summary_value!=null?summary_value:summary_value);




    }

    public void setGameInfo(GameEntry game){

        this.game=game;


    }

    public static String getDate(long milliSeconds)
    {


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        String myDate = dateFormat.format(new java.util.Date(milliSeconds));
        return myDate;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onClickFavBtnObj=(onClickFavBtn)context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
