package com.example.myapplicationstyle.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplicationstyle.DataBase.AppDataBase;
import com.example.myapplicationstyle.DataBase.GameEntry;
import com.example.myapplicationstyle.MainActivity;
import com.example.myapplicationstyle.R;
import com.example.myapplicationstyle.getJson;
import com.example.myapplicationstyle.ui.Feeds.Feeds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class games_host  extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener,Games_fragment.games_fragment_ItemSelected{


    private Toolbar mToolbar;
    private  ImageView imgBar;
    private String game_poster_url="https://images.igdb.com/igdb/image/upload/t_cover_big/co20uw.jpg";
    private ArrayList<GameEntry> gamesList=new ArrayList<>();
    private static boolean auto_refresh=false;
    private static String platform_request;
    public static String category_request;
    private static boolean desc=false;
    private static String search_request="";
    BottomNavigationView bottomNavigationView;
    private AppDataBase appDataBase;



    private FragmentManager fragmentManager=getSupportFragmentManager();
    Games_fragment games_igdb=new Games_fragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getName(),"onCreate");
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.games_host);

        imgBar=(ImageView)findViewById(R.id.img_bar);
        Picasso.with(this).load(game_poster_url).into(imgBar);






        mToolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                 case R.id.news_bottom_option:
                    Goto_Feeds();
                    return true;
                    case R.id.games_bottom_option:
                        Goto_Games(false);
                        return true;
                    case R.id.favorites_bottom_option:
                        Goto_Games(true);
                        return true;
                }
                return true;
            }
        });
        setupSharedPreferences();

        Intent intent=getIntent();
        if(intent!=null){
            Log.i(this.getClass().getName(),"from intent");
            if(intent.getStringExtra("search_query")!=null){
                search_request=intent.getStringExtra("search_query");
                Log.i(this.getClass().getName(),"Changing search");
            }
        }

        appDataBase=AppDataBase.getInstance(getApplicationContext());
        setupViewModel();

        getData();





    }

    private void setupViewModel() {
            GamesViewModel gamesViewModel= ViewModelProviders.of(this).get(GamesViewModel.class);
        gamesViewModel.getGames().observe(this, new Observer<List<GameEntry>>() {
            @Override
            public void onChanged(List<GameEntry> gameEntries) {
                Log.i(this.getClass().getName(),"Updating list of games from LiveData in ViewModel");

                addGamesViewModel( gameEntries);

            }
        });

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //mDb.trailersDAO().delete_Trailer();
                //appDataBase.gameDao().deleteAllGames();
                //mDb.movieDAO().deleteAllMovies();
                //appDataBase.screenshotDAO().deleteAllScreenshots();
            }
        });
    }

    public  void addGamesViewModel(List<GameEntry> gameEntries){
        for(int i=0;i<gameEntries.size();i++){
            gamesList.add(gameEntries.get(i));
        }
    }

    public void Goto_Feeds(){
        Intent intent=new Intent(this, Feeds.class);
        startActivity(intent);
    }


    public void Goto_Games(boolean favorites){

        if(favorites){
            //filterGames_favorites();
        }else{
            Toast toast=Toast.makeText(this,getString(R.string.you_areHere_games),Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    public void getData(){
            gamesList.clear();
             getJson.getData(this,platform_request,category_request,desc,search_request, new MainActivity.VolleyCallBack() {


            @Override
            public void succesVolley(JSONArray response) {
                //Log.i(this.getClass().getName(), response.toString());

                data_to_Json(response.toString());
                setupGames(1);
            }
        });


    }

    public void data_to_Json(String data){

        if(data!=null){
            try{
                JSONArray games=new JSONArray(data);

                for(int i=0;i<games.length();i++){

                    JSONObject gameJsonObj=games.getJSONObject(i);
                    GameEntry gameEntry=new GameEntry();
                    gameEntry.setJsonObj(gameJsonObj.toString());

                    String id =gameJsonObj.optString("id");
                    gameEntry.setId_IGDB(Integer.parseInt(id));

                    //name
                    String name=gameJsonObj.optString("name");

                    //Cover
                    String cover=null;
                    if(gameJsonObj.optJSONObject("cover")!=null){
                        cover=gameJsonObj.optJSONObject("cover").optString("image_id");
                    }

                    //Log.i(this.getClass().getName(),"game: "+cover);
                    /*
                    //hypes
                    String hypes=gameJsonObj.optString("hypes");

                    //platforms
                    JSONArray platformsList=gameJsonObj.optJSONArray("platforms");
                    String platforms="";
                    if(platformsList!=null){
                        for(int j=0;j<platformsList.length();j++){
                            JSONObject platformsJsonObj=platformsList.getJSONObject(j);

                            String platform=platformsJsonObj.getString("name");
                            platforms+=platform+". ";
                        }
                    }


                    //rating
                    String rating=gameJsonObj.optString("rating");

                    //total rating
                    String total_rating=gameJsonObj.optString("total_rating");


                    //total rating count
                    String total_rating_count=gameJsonObj.optString("total_rating_count");

                    //summary
                    String summary=gameJsonObj.optString("summary");


                    //Genres
                    JSONArray genresList=gameJsonObj.optJSONArray("genres");
                    String genres="";
                    if(genresList!=null){
                        for(int j=0;j<genresList.length();j++){
                            JSONObject genreJsonObj=genresList.getJSONObject(j);

                            String company=genreJsonObj.optString("name");
                            genres+=company+". ";
                        }
                        //Log.i(this.getClass().getName(),"game: "+genres);

                    }


                    //Companies involved
                    JSONArray companiesList=gameJsonObj.optJSONArray("involved_companies");
                    String companies="";
                    if(companiesList!=null){
                        for(int j=0;j<companiesList.length();j++){
                            JSONObject genreJsonObj=companiesList.getJSONObject(j).getJSONObject("company");

                            String company=genreJsonObj.optString("name");
                            companies+=company+". ";
                        }
                        //Log.i(this.getClass().getName(),"game: "+companies);

                    }

                    //Screenshots
                    JSONArray screenshotList=gameJsonObj.optJSONArray("screenshots");
                    String screenList_String=screenshotList!=null&&screenshotList.length()>0?screenshotList.toString():"";


                    //videos
                    JSONArray videosList=gameJsonObj.optJSONArray("videos");
                    String videosList_String=videosList!=null&&videosList.length()>0?videosList.toString():"";
                    //Log.i(this.getClass().getName(),"game: "+gameJsonObj.optJSONArray("screenshots").toString());
                    */


                    gameEntry.setId_IGDB(Integer.parseInt(id));
                    gameEntry.setName(name);
                    gameEntry.setCoverUrl(cover);
                    /*
                    gameEntry.setHypes(Integer.parseInt(!hypes.isEmpty()?hypes:"0"));
                    gameEntry.setPlatforms(platforms);
                    gameEntry.setRating(Double.parseDouble(!rating.isEmpty()?rating:"0.0"));
                    gameEntry.setTotal_rating(Double.parseDouble(!total_rating.isEmpty()?total_rating:"0"));
                    gameEntry.setTotal_rating_count(Integer.parseInt(!total_rating_count.isEmpty()?total_rating_count:"0"));
                    gameEntry.setSummary(summary);
                    gameEntry.setGenres(genres);
                    gameEntry.setInvolved_companies(companies);
                    gameEntry.setScreenshot_url(screenList_String);
                    gameEntry.setVideos_id(videosList_String);

                     */


                    //Log.i(this.getClass().getName(),"game cover_id: "+gameEntry.getCoverUrl());

                    gamesList.add(gameEntry);



                }



            }catch (JSONException e){
                e.printStackTrace();
            }


        }

    }

    public void setupGames(int option){

        if(option==1){

            fragmentManager.beginTransaction()
                    .replace(R.id.games_fragment,games_igdb)
                    .commit();
            Log.i(this.getClass().getName(),"Game list: "+gamesList.size());
            games_igdb.setGamesData(gamesList);
        }

    }

    public void setupSharedPreferences(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        platform_request=sharedPreferences.getString(getString(R.string.pref_platform_key),getString(R.string.pref_platform_label_ps4));
        category_request=sharedPreferences.getString(getString(R.string.pref_news_category),getString(R.string.pref_news_label_all));
        desc            =sharedPreferences.getBoolean(getString(R.string.order_desc),false);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(getString(R.string.pref_platform_key))){
            String platform=sharedPreferences.getString(key,getResources().getString(R.string.pref_platform_all));

        }else if(key.equals(getString(R.string.pref_news_category))){
            String category=sharedPreferences.getString(key,getResources().getString(R.string.pref_news_all));

        }else if(key.equals(getString(R.string.order_desc))){
            boolean order_desc=sharedPreferences.getBoolean(key,getResources().getBoolean(R.bool.pref_game_order));

            if(order_desc){
                //Log.i(this.getClass().getName(),"desc: True");
            }else{
                //Log.i(this.getClass().getName(),"desc: false");
            }

            auto_refresh=true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.games_menu,menu);



        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast toast=Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG);
                search_request=query;

                search_request="";
                toast.show();
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchView.onActionViewCollapsed();
                performUpdate(query);


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    public void performUpdate(String query){
        Intent intent=new Intent(this,games_host.class);
        intent.putExtra("search_query",query);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            Intent intent=new Intent(this,Activity_settings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getName(),"onResume");
        if(auto_refresh){
            Log.i(this.getClass().getName(),"Auto refresh working");
            auto_refresh=false;
            getData();
        }
    }

    @Override
    public void gameSelected(GameEntry game) {
        Log.i(this.getClass().getName(),game.getName()+"_"+game.getId_IGDB());


        Intent intent=new Intent(this,game_detail.class);

        intent.putExtra("game_idIGDB",game.getId_IGDB());
        intent.putExtra("game_jsonObj",game.getJsonObj());
        intent.putExtra("game_favorite",game.isFavorite());

        startActivity(intent);



    }


}
