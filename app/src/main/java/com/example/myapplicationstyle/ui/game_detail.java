package com.example.myapplicationstyle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplicationstyle.DataBase.AppDataBase;
import com.example.myapplicationstyle.DataBase.GameEntry;
import com.example.myapplicationstyle.R;
import com.example.myapplicationstyle.ui.reviews.Review;
import com.example.myapplicationstyle.ui.screenshots.Screenshot_fragment;
import com.example.myapplicationstyle.ui.screenshots.Screenshots;
import com.example.myapplicationstyle.ui.videos.Videos;
import com.example.myapplicationstyle.ui.videos.Videos_fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class game_detail extends AppCompatActivity  implements Game_summary_fragment.onClickFavBtn{
    private JSONObject gameJsonObj;
    private  GameEntry gameEntry;
    private Button screenshots_Btn;
    private Button videos_Btn;
    private Button reviews_Btn;
    private boolean on_tablet=false;
    GameViewModelFactory factory;
    AppDataBase appDataBase;
    GameViewModel gameViewModel;
    boolean favorite=false;
    int game_id=0;

    FragmentManager fragmentManager=getSupportFragmentManager();
    Game_summary_fragment games_fragment=new Game_summary_fragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.game_detail);

        ActionBar actionBar=this.getSupportActionBar();

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.app_name));
        }

        Intent intent=getIntent();

        if(savedInstanceState!=null){
            Log.i(this.getClass().getName(),"savedInstanceState != null");
            setGameEntry(savedInstanceState.getString("game"));
        }else if(intent!=null){


            appDataBase=AppDataBase.getInstance(getApplicationContext());



            if(intent.getIntExtra("game_idIGDB",0)!=0){

                if(intent.getIntExtra("game_id",0)!=0){
                    Log.i(this.getClass().getName(),"Game come as favorite");
                    game_id=intent.getIntExtra("game_id",0);
                    favorite=intent.getBooleanExtra("game_favorite",false);
                    findGame(game_id,true);
                    //admin_content_fragment();
                }else
                {

                    setGameEntry(intent.getStringExtra("game_jsonObj"));
                }



            }else{
                Log.i(this.getClass().getName(),"Game ID not valid");
                if(gameJsonObj!=null){
                    Log.i(this.getClass().getName(),"gameJsonObj is null");
                    setGameEntry(gameJsonObj.toString());
                }else{
                    Log.i(this.getClass().getName(),"gameJsonObj is null");
                }
            }
        }else{
            Log.i(this.getClass().getName(),"intent is null");
        }

        screenshots_Btn=(Button)findViewById(R.id.screenshot_btn);
        screenshots_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_content(1);
                //1 means update content with screenshots
            }
        });

        videos_Btn=(Button)findViewById(R.id.videos_btn);
        videos_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_content(2);
            }
        });

        reviews_Btn=(Button)findViewById(R.id.reviews_btn);
        reviews_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_content(3);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getName(),"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(this.getClass().getName(),"onRestart");
    }

    public void update_content(int content_type){

        if(content_type==1){//Intent for Screenshots
            if(!on_tablet){
                Intent intent=new Intent(this, Screenshots.class);
                putExtras_intent(intent);
                startActivity(intent);

            }else{//admin fragment in this class instead of Screenshots.class

            }
        }else if(content_type==2){//Intent for videos
            if(!on_tablet){
                Intent intent=new Intent(this, Videos.class);
                putExtras_intent(intent);
                startActivity(intent);
            }else{

            }

        }else if(content_type==3){

            if(!on_tablet){
                Intent intent=new Intent(this, Review.class);
                putExtras_intent(intent);
                startActivity(intent);
            }else{

            }

        }

    }

    public Intent putExtras_intent(Intent intent){
        intent.putExtra("game_idIGDB",gameEntry.getId_IGDB());
        intent.putExtra("game_cover",gameEntry.getCoverUrl());
        intent.putExtra("name", gameEntry.getName());
        intent.putExtra("gameJsonObj",gameEntry.getJsonObj());
        return intent;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("game_idIGDB",gameEntry.getId_IGDB());
        outState.putString("game",gameEntry.getJsonObj());
    }

    public void admin_content_fragment(){
        if(gameEntry!=null){
            games_fragment.setGameInfo(gameEntry);
            //games_fragment.build_UI();
            fragmentManager.beginTransaction()
                    .replace(R.id.game_content,games_fragment)
                    .commit();
        }else{
            Log.i(this.getClass().getName(),"game: is null");
        }

    }

    public void setGameEntry(String game) {

        if (game != null) {
            try {
                gameJsonObj = new JSONObject(game);
                gameEntry = new GameEntry();
                gameEntry.setJsonObj(game);





                String id = gameJsonObj.optString("id");
                gameEntry.setId_IGDB(Integer.parseInt(id));

                //name
                String name = gameJsonObj.optString("name");

                //Cover
                String cover = null;
                if (gameJsonObj.optJSONObject("cover") != null) {
                    cover = gameJsonObj.optJSONObject("cover").optString("image_id");
                }


                //hypes
                String hypes = gameJsonObj.optString("hypes");

                //platforms
                JSONArray platformsList = gameJsonObj.optJSONArray("platforms");
                String platforms = "";
                if (platformsList != null) {
                    for (int j = 0; j < platformsList.length(); j++) {
                        JSONObject platformsJsonObj = platformsList.getJSONObject(j);

                        String platform = platformsJsonObj.getString("name");
                        platforms += platform + ". ";
                    }
                }


                //rating
                String rating = gameJsonObj.optString("rating");

                //total rating
                String total_rating = gameJsonObj.optString("total_rating");


                //total rating count
                String total_rating_count = gameJsonObj.optString("total_rating_count");

                //summary
                String summary = gameJsonObj.optString("summary");


                //Genres
                JSONArray genresList = gameJsonObj.optJSONArray("genres");
                String genres = "";
                if (genresList != null) {
                    for (int j = 0; j < genresList.length(); j++) {
                        JSONObject genreJsonObj = genresList.getJSONObject(j);

                        String company = genreJsonObj.optString("name");
                        genres += company + ". ";
                    }
                    //Log.i(this.getClass().getName(),"game: "+genres);

                }


                //Companies involved
                JSONArray companiesList = gameJsonObj.optJSONArray("involved_companies");
                String companies = "";
                if (companiesList != null) {
                    for (int j = 0; j < companiesList.length(); j++) {
                        JSONObject genreJsonObj = companiesList.getJSONObject(j).getJSONObject("company");

                        String company = genreJsonObj.optString("name");
                        companies += company + ". ";
                    }
                    //Log.i(this.getClass().getName(),"game: "+companies);

                }

                //Screenshots
                JSONArray screenshotList = gameJsonObj.optJSONArray("screenshots");
                //String screenList_String = screenshotList != null && screenshotList.length() > 0 ? screenshotList.toString() : "";


                //videos
                JSONArray videosList = gameJsonObj.optJSONArray("videos");
                String videosList_String = videosList != null && videosList.length() > 0 ? videosList.toString() : "";
                //Log.i(this.getClass().getName(),"game: "+gameJsonObj.optJSONArray("screenshots").toString());

                //DateRelease
                String dateRelease=gameJsonObj.optString("first_release_date");
                Long first_release_date=Long.parseLong(dateRelease!=null?dateRelease:"0");

                gameEntry.setId_IGDB(Integer.parseInt(id));
                gameEntry.setName(name);
                gameEntry.setHypes(Integer.parseInt(!hypes.isEmpty() ? hypes : "0"));
                gameEntry.setPlatforms(platforms);
                gameEntry.setRating(Double.parseDouble(!rating.isEmpty() ? rating : "0.0"));
                gameEntry.setTotal_rating(Double.parseDouble(!total_rating.isEmpty() ? total_rating : "0"));
                gameEntry.setTotal_rating_count(Integer.parseInt(!total_rating_count.isEmpty() ? total_rating_count : "0"));
                gameEntry.setSummary(summary);
                gameEntry.setGenres(genres);
                gameEntry.setInvolved_companies(companies);
                gameEntry.setScreenshot_url(screenshotList.toString());
                gameEntry.setVideos_id(videosList_String);
                gameEntry.setCoverUrl(cover);
                gameEntry.setFirst_release_date(first_release_date);

                Log.i(this.getClass().getName(),"game "+gameEntry.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            admin_content_fragment();

        }else{
            Log.i(this.getClass().getName(),"Game comes empty");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    public void clickingFavoriteBtn() {

        if(favorite){
            Log.i(this.getClass().getName(),"Game is marked like favorite. Preparing to delete from DB");
        }else{
            Log.i(this.getClass().getName(),"Games is not Favorite");

            findGame(gameEntry.getId(),false);




        }

    }

    public boolean findGame(int id, final boolean update_entity){

        /*

        GamesViewModel gameViewModel_opt= ViewModelProviders.of(this).get(GamesViewModel.class);

        gameViewModel_opt.getGames().observe(this, new Observer <List<GameEntry>>() {
            @Override
            public void onChanged(List<GameEntry> gameEntries) {
                Log.i(this.getClass().getName(),"Updating list of games from LiveData in ViewModel");
                if(gameEntries!=null){
                    Log.i(this.getClass().getName(),"The game exist"+gameEntries.get(0).getName());
                    gameFound();
                }else{
                    Log.i(this.getClass().getName(),"The game not exist");
                    gameNotFound();
                }


            }
        });

         */

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int id=0;
                if(update_entity){
                    id=game_id;
                    Log.i(this.getClass().getName(),"Id from DB: "+game_id);
                }else{
                    id=gameEntry.getId();
                    Log.i(this.getClass().getName(),"Id from entity"+gameEntry.getId());
                }



               GameEntry gameDB=appDataBase.gameDao().loadGameById(id);
               if(gameDB!=null){
                   Log.i(this.getClass().getName(),"Game found. Can be deleted");
                   Log.i(this.getClass().getName(),"Name "+gameDB.getName());
                   Log.i(this.getClass().getName(),"ID "+gameDB.getId());

                   if(update_entity){
                       gameEntry=gameDB;
                       Log.i(this.getClass().getName(),"Game updated");
                       admin_content_fragment();
                   }else{
                       //insert game in DB
                       gameFound();
                   }
               }else{
                   Log.i(this.getClass().getName(),"Game Not found. Can be inserted ");
                   if(!update_entity){
                       //delete game from DB
                       gameNotFound();
                   }

               }
            }
        });
        return foundGame;
    }


    public void gameFound(){
        Log.i(this.getClass().getName(),"Game found. Can be deleted");
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDataBase.gameDao().deleteGameById(gameEntry.getId());
                    favorite=false;
                    Log.i(this.getClass().getName(),"Deleting game like favorite in DB "+gameEntry.getId());



            }
        });

    }

    public void gameNotFound(){
        Log.i(this.getClass().getName(),"Game Not found. Can be inserted ");
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {

                    int id=(int)appDataBase.gameDao().insertGame(gameEntry);
                    if(id>0){
                        favorite=true;
                        Log.i(this.getClass().getName(),"inserting game like favorite in DB "+id);
                        gameEntry.setId(id);
                        //gameEntry.setFavorite(true);

                    }
                }
            });

    }

    public void inertGame_inDB(){

    }

    public void deleteGame_FromDB(){

    }

    boolean foundGame=false;
}
