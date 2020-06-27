package com.example.myapplicationstyle.ui.Feeds;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationstyle.DataBase.FeeedEntry;
import com.example.myapplicationstyle.DataBase.ReviewEntry;
import com.example.myapplicationstyle.R;
import com.example.myapplicationstyle.ui.Activity_settings;
import com.example.myapplicationstyle.ui.games_host;
import com.example.myapplicationstyle.ui.reviews.Review;
import com.example.myapplicationstyle.ui.reviews.ReviewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Feeds extends AppCompatActivity implements FeedAdapter.onClickFeedAdapter, SharedPreferences.OnSharedPreferenceChangeListener {

    private ArrayList<FeeedEntry> FeedList =new ArrayList<>();
    private int game_idIGDB;
    private String game_name;
    private String game_cover;
    private String feedsList_jsonString;

    //game reference
    private ImageView cover;
    private TextView name;

    //Adapter
    private RecyclerView feed_recyclerView;
    private FeedAdapter feeds_adapter;
    private GridLayoutManager layout;
    private String cover_id;
    String category_request="";
    static boolean refresh=false;
    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeds_layout);
        ActionBar actionBar=this.getSupportActionBar();

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.app_name));
        }

        feed_recyclerView=(RecyclerView)findViewById(R.id.feeds_recycler_view);
        feeds_adapter=new FeedAdapter(this);
        cover=(ImageView)findViewById(R.id.game_cover_review);
        name=(TextView)findViewById(R.id.game_name_review);
        layout=new GridLayoutManager(this,1);
        feed_recyclerView.setAdapter(feeds_adapter);
        feed_recyclerView.setLayoutManager(layout);

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


        setUp();
        getData();


    }

    public void Goto_Feeds(){
        Toast toast=Toast.makeText(this,getString(R.string.you_areHere_feeds),Toast.LENGTH_LONG);
        toast.show();
    }

    public void Goto_Games(boolean favorites){
        Intent intent=new Intent(this,games_host.class);
        if(favorites){
            intent.putExtra("favorites",true);
        }
        startActivity(intent);
    }

    public void setUp(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        //platform_request=sharedPreferences.getString(getString(R.string.pref_platform_key),getString(R.string.pref_platform_label_ps4));
        category_request=sharedPreferences.getString(getString(R.string.pref_news_category),getString(R.string.pref_news_label_all));
        //desc            =sharedPreferences.getBoolean(getString(R.string.order_desc),false);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void getData() {
        FeedList.clear();
        getFeeds.getData(this, category_request, 0, new Review.VolleyCallBack() {
            @Override
            public void succesVolley(JSONArray response) {
                if(!response.toString().isEmpty() && response!=null){
                    feedsJsonTo_FeedEntryList(response.toString());
                }


            }
        });
    }

    public void feedsJsonTo_FeedEntryList(String data){
        try{

            JSONArray feedsJsonArray=new JSONArray(data);
            for(int i=0;i<feedsJsonArray.length();i++){
                JSONObject feedJsonObj=feedsJsonArray.getJSONObject(i);


                String youtube_id=feedJsonObj.optString("content");
                if(youtube_id.contains("?v=")){
                    int index=youtube_id.lastIndexOf("=");
                    String video_youtubeId=youtube_id.substring((index+1),youtube_id.length());
                    if(!video_youtubeId.isEmpty()){

                        FeeedEntry feeedEntry=new FeeedEntry();
                        int id_IGDB=feedJsonObj.optInt("id");
                        int category=feedJsonObj.optInt("category");
                        JSONArray gamesRelated=feedJsonObj.getJSONArray("games");
                        String games_related="";
                        for(int j=0;j<gamesRelated.length();j++){
                            JSONObject game=gamesRelated.getJSONObject(j);
                            String name=game.optString("name");
                            games_related=games_related.concat(name+". ");
                        }

                        Long created_at=feedJsonObj.optLong("created_at");

                        //usually empty
                        String title=feedJsonObj.optString("title");
                        feeedEntry.setId_IGDB(id_IGDB);
                        feeedEntry.setTitle(title);
                        feeedEntry.setCategory(category);
                        feeedEntry.setGames_related(games_related);
                        feeedEntry.setVideo_youtubeId(video_youtubeId);
                        feeedEntry.setCreatedAt(created_at);

                        FeedList.add(feeedEntry);



                    }



                }
            }
            feeds_adapter.setData(FeedList);
            feeds_adapter.notifyDataSetChanged();

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onClickFeed(FeeedEntry feeedEntry) {
        Log.i(this.getClass().getName(),"vnd.youtube:"+feeedEntry.getVideo_youtubeId());
        Uri youtube=Uri.parse(getString(R.string.youtube_uri)+feeedEntry.getVideo_youtubeId());
        Intent intent=new Intent(Intent.ACTION_VIEW,youtube);
        startActivity(intent);
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getName(),"onResume");
        if(refresh){
            Log.i(this.getClass().getName(),category_request+" category_request");
            refresh=false;

            getData();
            category_request="";

        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.pref_news_category))){
            category_request=sharedPreferences.getString(key,"");
            refresh=true;

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            Intent intent=new Intent(this, Activity_settings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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



                toast.show();
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchView.onActionViewCollapsed();





                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    public void searchVideoGame(String query){
        Intent intent=new Intent(this, games_host.class);
        intent.putExtra("search_game",query);
        startActivity(intent);
    }
}
