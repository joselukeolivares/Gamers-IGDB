package com.example.myapplicationstyle.ui.reviews;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceScreen;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplicationstyle.DataBase.ReviewEntry;
import com.example.myapplicationstyle.MainActivity;
import com.example.myapplicationstyle.R;
import com.example.myapplicationstyle.getJson;
import com.example.myapplicationstyle.ui.Activity_settings;
import com.example.myapplicationstyle.ui.games_host;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Review extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener,Review_fragment.review_seleted_Infragment{
    private int category;
    private int game_idIGDB;
    private String category_request="EMPTY";
    private boolean refresh=false;
    private String reviewsJsonArray_String;

    private String game_name;
    private String game_cover;
    private JSONObject game_jsonObj;
    private String screenshotJsonArray;
    private TextView empty_label;


    FragmentManager fragmentManager=getSupportFragmentManager();
    Review_fragment review_fragment=new Review_fragment();


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.pref_news_category))){
            category_request=sharedPreferences.getString(key,getResources().getString(R.string.pref_news_all));
            refresh=true;

        }

    }

    @Override
    public void onClickReviewFragment(ReviewEntry reviewEntry) {
        Log.i(this.getClass().getName(),reviewEntry.getTitle());

        Intent intent=new Intent(this,Review_detail.class);
        intent.putExtra("game_idIGDB",game_idIGDB);
        intent.putExtra("game_cover",game_cover);
        intent.putExtra("name",game_name);
        intent.putExtra("review_views",reviewEntry.getViews());
        intent.putExtra("date",reviewEntry.getCreated_at());
        intent.putExtra("title",reviewEntry.getTitle());
        intent.putExtra("summary",reviewEntry.getContent());
        intent.putExtra("positive_point",reviewEntry.getPositive_points());
        intent.putExtra("negative_point",reviewEntry.getNegative_points());
        startActivity(intent);
    }

    public interface VolleyCallBack{
        void succesVolley(JSONArray response);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.reviews);
        empty_label=(TextView)findViewById(R.id.empty_result_textView_review);

        ActionBar actionBar=this.getSupportActionBar();

        if(actionBar!=null){
            //actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.app_name));
        }

        Intent intent=getIntent();
        if(intent!=null){

            game_idIGDB=intent.getIntExtra("game_idIGDB",0);
            game_cover=intent.getStringExtra("game_cover");
            game_name=intent.getStringExtra("name");

            setUp();
            if(game_idIGDB!=0){
                getReviews();

            }else{
                Log.i(this.getClass().getName(),"game_idIGDB is 0");
            }


        }



    }

    private void admin_review_Fragment() {
        fragmentManager.beginTransaction()
                .replace(R.id.review_container,review_fragment)
                .commit();

        review_fragment.setData(game_idIGDB,game_name,game_cover,reviewsJsonArray_String);

    }

    public void getReviews(){
        refresh=false;
        getReviews.getData(this,category,game_idIGDB, new Review.VolleyCallBack() {


            @Override
            public void succesVolley(JSONArray response) {
                //Log.i(this.getClass().getName(), response.toString());

                //Log.i(this.getClass().getName(),response.toString());
                reviewsJsonArray_String=response.toString();
                if(reviewsJsonArray_String!=null){
                    if(!reviewsJsonArray_String.isEmpty() && reviewsJsonArray_String.length()>5){
                        empty_label.setVisibility(View.INVISIBLE);

                    }else{
                        empty_label.setVisibility(View.VISIBLE);
                    }

                    admin_review_Fragment();
                }


            }
        });
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
                /*
                search_request=query;

                search_request="";
                toast.show();
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchView.onActionViewCollapsed();
                performUpdate(query);

                 */


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    public void setUp(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        //platform_request=sharedPreferences.getString(getString(R.string.pref_platform_key),getString(R.string.pref_platform_label_ps4));
        category_request=sharedPreferences.getString(getString(R.string.pref_news_category),getString(R.string.pref_news_label_all));
        //desc            =sharedPreferences.getBoolean(getString(R.string.order_desc),false);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.i(this.getClass().getName(),category_request);

    }
}
