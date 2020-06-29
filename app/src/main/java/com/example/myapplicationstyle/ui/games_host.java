package com.example.myapplicationstyle.ui;

import android.app.IntentService;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

import com.example.myapplicationstyle.Adds;
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
    private String game_poster_url;

    private ArrayList<GameEntry> gamesList=new ArrayList<>();
    private ArrayList<GameEntry> gamesListAux;
    private static boolean auto_refresh=false;
    private static String platform_request;
    public static String category_request;
    private static boolean desc=false;
    private static String search_request="";
    BottomNavigationView bottomNavigationView;
    private AppDataBase appDataBase;
    String json_auxiliar;



    private FragmentManager fragmentManager=getSupportFragmentManager();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getName(),"onCreate");
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.games_host);
        game_poster_url=getString(R.string.uriCover)+"co20uw"+getString(R.string.jpg);
        emptyResult_text=(TextView)findViewById(R.id.empty_result_textView);
        imgBar=(ImageView)findViewById(R.id.img_bar);
        Picasso.with(this).load(game_poster_url).into(imgBar);

        setupSharedPreferences();




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

                }
                return true;
            }
        });


        Intent intent=getIntent();
        if(savedInstanceState!=null){
            json_auxiliar=savedInstanceState.getString("json_auxiliar");

            data_to_Json(json_auxiliar);

        }else if(intent!=null){
            Log.i(this.getClass().getName(),"from intent");
            if(intent.getStringExtra("search_query")!=null && !intent.getStringExtra("search_query").isEmpty()){
                search_request=intent.getStringExtra("search_query");
                Log.i(this.getClass().getName(),"Changing search");
                performUpdate(search_request);
            }else{
                getData();
            }




        }
        if(appDataBase==null){
            appDataBase=AppDataBase.getInstance(getApplicationContext());
            setupViewModel();
        }









    }

    private void setupViewModel() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //mDb.trailersDAO().delete_Trailer();
                //appDataBase.gameDao().deleteAllGames();

                //appDataBase.screenshotDAO().deleteAllScreenshots();
            }
        });
/*
 */

            GamesViewModel gamesViewModel= ViewModelProviders.of(this).get(GamesViewModel.class);
        gamesViewModel.getGames().observe(this, new Observer<List<GameEntry>>() {
            @Override
            public void onChanged(List<GameEntry> gameEntries) {
                Log.i(this.getClass().getName(),"Updating list of games from LiveData in ViewModel");

                addGamesViewModel( gameEntries);




            }
        });


    }

    Context context=this;
    public  void addGamesViewModel(List<GameEntry> gameEntries){
        Log.i(this.getClass().getName(),count+" Games in DB "+gameEntries.size());
        if(count==0){
            gamesListAux=new ArrayList<>();
            gamesListAux.addAll(gamesList);
            for(int i=0;i<gameEntries.size();i++){
                GameEntry gameEntry=gameEntries.get(i);
                gameEntry.setFavorite(true);
                gamesListAux.add(gameEntry);

                Log.i(this.getClass().getName(),"Game from DB "+gameEntries.get(i).getId()+" "+gameEntries.get(i).getName());
            }

            gamesList.clear();
            gamesList.addAll(gamesListAux);
            count++;
            //loadedData=response.toString();
            //data_to_Json(loadedData);

            setupGames(1);
        }




    }

    int count=0;

    public void Goto_Feeds(){
        Intent intent=new Intent(this, Feeds.class);
        startActivity(intent);
    }


    public void Goto_Games(boolean favorites){

        if(favorites){

        }else{
            Toast toast=Toast.makeText(this,getString(R.string.you_areHere_games),Toast.LENGTH_LONG);
            toast.show();
            gamesList.clear();
            if(gamesListAux!=null){
                gamesList.addAll(gamesListAux);
                gamesListAux.clear();
            }

            if(json_auxiliar!=null && !json_auxiliar.isEmpty()){
                search_request="";
                getData();
            }

            setupGames(1);

        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("json_auxiliar",json_auxiliar);

    }
private String loadedData;
    public void getData(){
            gamesList.clear();
             getJson.getData(this,platform_request,category_request,desc,search_request, new MainActivity.VolleyCallBack() {


            @Override
            public void succesVolley(JSONArray response) {
                Log.i(this.getClass().getName(), response.toString());
                loadedData=response.toString();
                data_to_Json(response.toString());

                //setupGames(1);
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


                    Log.i(this.getClass().getName(),"game cover_id: "+gameEntry.getCoverUrl());

                    gamesList.add(gameEntry);



                }
                    if(gamesList!=null){
                        emptyResult_text.setVisibility(View.INVISIBLE);
                        emptyResult_text.setText("");
                }else{
                        emptyResult_text.setVisibility(View.VISIBLE);
                        emptyResult_text.setText(getString(R.string.empty_result));
                    }

                    setupGames(1);

            }catch (JSONException e){
                e.printStackTrace();
            }


        }

        if(gamesList!=null && gamesList.isEmpty()){
            emptyResult_text.setText(getString(R.string.empty_result));
        }else{
            emptyResult_text.setText("");
        }

    }
    TextView emptyResult_text;

    Adds adds=new Adds();

    public void setupGames(int option){
        Games_fragment games_igdb=new Games_fragment();
        if(option==1){

            fragmentManager.beginTransaction()
                    .replace(R.id.games_fragment,games_igdb)
                    .commit();
            Log.i(this.getClass().getName(),"Game list: "+gamesList.size());

            games_igdb.setGamesData(gamesList,this);
        }

        fragmentManager.beginTransaction()
                .replace(R.id.add_fragment,adds)
                .commit();

    }

    public void setupSharedPreferences(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        platform_request=sharedPreferences.getString(getString(R.string.pref_platform_key),getString(R.string.pref_platform_all));
        category_request=sharedPreferences.getString(getString(R.string.pref_news_category),getString(R.string.pref_news_label_all));
        desc            =sharedPreferences.getBoolean(getString(R.string.order_desc),false);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(getString(R.string.pref_platform_key))){
             platform_request=sharedPreferences.getString(key,getResources().getString(R.string.pref_platform_all));
            auto_refresh=true;
        }else if(key.equals(getString(R.string.pref_news_category))){
            category_request=sharedPreferences.getString(key,getResources().getString(R.string.pref_news_all));
            auto_refresh=true;
        }else if(key.equals(getString(R.string.order_desc))){
            boolean order_desc=sharedPreferences.getBoolean(key,getResources().getBoolean(R.bool.pref_game_order));

            if(order_desc){
                //Log.i(this.getClass().getName(),"desc: True");
                desc=true;
            }else{
                //Log.i(this.getClass().getName(),"desc: false");
                desc=false;
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
        Log.i(this.getClass().getName(),"search service init");
        Intent intent=new Intent(this,searchIntent.class);
        intent.putExtra("platform_request",platform_request);
        intent.putExtra("category_request",category_request);
        intent.putExtra("desc",desc);
        intent.putExtra("search_query",query);
        intent.putExtra(searchIntent.MyResultReceiver.class.getSimpleName(), new searchIntent.MyResultReceiver(new Handler()).setOnMyResultListener(new searchIntent.MyResultReceiver.OnMyResultListener(){
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData){
                Log.i(this.getClass().getName(),"PUTEXTRA");
                String message=resultData.getString("search_query");
                if(message!=null){
                    Log.i(this.getClass().getName(),message);
                    printTest(message);
                }else{
                    Log.i(this.getClass().getName(),"It's null");
                }


            }
        }));
        startService(intent);
    }

    public void printTest(String result){
        gamesListAux=new ArrayList<>();
        gamesListAux.addAll(gamesList);
        gamesList.clear();
        Log.i(this.getClass().getName(),result);
        json_auxiliar=result;

        data_to_Json(result);
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
        count=0;
        if(gamesListAux!=null){
            Log.i(this.getClass().getName(),"onResume"+gamesListAux.size());
        }
        if(auto_refresh){
            getData();
            auto_refresh=false;
        }
        //setupGames(1);


    }

    @Override
    public void gameSelected(GameEntry game) {
        Log.i(this.getClass().getName(),game.getName()+"_"+game.getId_IGDB());


        Intent intent=new Intent(this,game_detail.class);

        intent.putExtra("game_idIGDB",game.getId_IGDB());
        intent.putExtra("game_jsonObj",game.getJsonObj());
        intent.putExtra("game_favorite",game.isFavorite());
        intent.putExtra("game_id",game.getId());

        startActivity(intent);



    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(this.getClass().getName(),"onRestart");



    }



}
