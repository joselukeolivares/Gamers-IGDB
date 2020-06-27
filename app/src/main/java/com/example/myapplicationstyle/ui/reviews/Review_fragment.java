package com.example.myapplicationstyle.ui.reviews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationstyle.DataBase.ReviewEntry;
import com.example.myapplicationstyle.R;
import com.example.myapplicationstyle.ui.videos.Videos_Adapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Review_fragment extends Fragment implements ReviewAdapter.onClick_ReviewAdapater {
    private ArrayList<ReviewEntry> reviewsList=new ArrayList<>();
    private int game_idIGDB;
    private String game_name;
    private String game_cover;
    private String reviewsJsonArray;

    //game reference
    private ImageView cover;
    private TextView name;

    //Adapter
    private RecyclerView reviews_recyclerView;
    private ReviewAdapter reviews_adapter;
    private GridLayoutManager layout;
    private String cover_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.review_fragment,container,false);

        reviews_adapter=new ReviewAdapter(this);
        cover=(ImageView)rootView.findViewById(R.id.game_cover_review);
        name=(TextView)rootView.findViewById(R.id.game_name_review);
        reviews_recyclerView=(RecyclerView)rootView.findViewById(R.id.reviews_recycler_view);
        layout=new GridLayoutManager(inflater.getContext(),1);
        reviews_recyclerView.setLayoutManager(layout);
        reviews_recyclerView.setAdapter(reviews_adapter);


        if(reviewsJsonArray!=null&&!reviewsJsonArray.isEmpty()){
            name.setText(game_name);
            String uri=getString(R.string.uriCover)+game_cover+getString(R.string.jpg);
            Picasso.with(getContext()).load(uri).into(cover);

            build_reviewList(reviewsJsonArray);
        }else{
            Log.i(this.getClass().getName(),"reviewsJsonArray is null or empty");
        }



        return rootView;
    }

    private void build_reviewList(String reviewsJsonArray) {

        try{
            JSONArray reviewList_json=new JSONArray(reviewsJsonArray);
            for(int i=0;i<reviewList_json.length();i++){
                JSONObject review_jsonObj=reviewList_json.getJSONObject(i);

                ReviewEntry reviewEntry=new ReviewEntry();
                int id_IGDB=review_jsonObj.optInt("id");

                String category=review_jsonObj.optString("category");

                String content=review_jsonObj.optString("content");

                String introduction=review_jsonObj.optString("introduction");

                int likes=review_jsonObj.optInt("likes");

                String negative_points=review_jsonObj.optString("negative_points");

                String positive_points=review_jsonObj.optString("positive_points");

                String title=review_jsonObj.optString("title");

                int user=review_jsonObj.optInt("user");

                int views=review_jsonObj.optInt("views");

                long created_at=review_jsonObj.optLong("created_at");

                reviewEntry.setId_IGDB(id_IGDB);
                reviewEntry.setCategory(category!=null?category:"");
                reviewEntry.setContent(content!=null?content:"");
                reviewEntry.setIntroduction(introduction!=null?introduction:"");
                reviewEntry.setLikes(likes);
                reviewEntry.setNegative_points(negative_points!=null?negative_points:"");
                reviewEntry.setPositive_points(positive_points!=null?positive_points:"");
                reviewEntry.setTitle(title!=null?title:"");
                reviewEntry.setUser(user+"");
                reviewEntry.setViews(views);
                reviewEntry.setCreated_at(created_at);

                reviewsList.add(reviewEntry);


            }

            if(reviewsList.size()>0){
                reviews_adapter.setData(reviewsList);
                reviews_adapter.notifyDataSetChanged();
            }else{
                Log.i(this.getClass().getName(),"reviews_adapter size is 0");
            }

        }catch (JSONException e){
            e.printStackTrace();
        }


    }


    public void setData(int game_idIGDB,String game_name, String game_cover, String reviewsJsonArray_string) {
        this.reviewsJsonArray=reviewsJsonArray_string;
        this.game_name=game_name;
        this.game_idIGDB=game_idIGDB;
        this.game_cover=game_cover;
        Log.i(this.getClass().getName(),this.reviewsJsonArray);

    }

    @Override
    public void onClick_Review(ReviewEntry review) {

        review_seleted_infragmentObj.onClickReviewFragment(review);

    }

    public interface review_seleted_Infragment{
        void onClickReviewFragment(ReviewEntry reviewEntry);
    }

    review_seleted_Infragment review_seleted_infragmentObj;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            review_seleted_infragmentObj=(review_seleted_Infragment)context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
