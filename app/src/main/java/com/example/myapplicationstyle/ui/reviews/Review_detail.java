package com.example.myapplicationstyle.ui.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationstyle.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class Review_detail extends AppCompatActivity {

    private int game_idIGDB;
    private String game_name;
    private String game_cover;
    private int review_views;
    private long date;
    private String title;
    private String summary;
    private String positive_point;
    private String negative_point;

    //UI
    private ImageView cover;
    private TextView name;
    private TextView views_textView;
    private TextView date_textView;
    private TextView summary_textView;
    private TextView positivePoint_label_textView;
    private TextView positivePoint_value_textView;
    private TextView negativePoint_value_textview;
    private TextView negativePoint_label_textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.review_detail);
         cover=(ImageView)findViewById(R.id.game_cover_review);
         name=(TextView)findViewById(R.id.game_name_review);
         views_textView=(TextView)findViewById(R.id.review_number_reviews_value);
         date_textView=(TextView)findViewById(R.id.review_date_value);
         summary_textView=(TextView)findViewById(R.id.review_content_value);
         positivePoint_value_textView=(TextView)findViewById(R.id.review_positivePoint_value);
         negativePoint_value_textview=(TextView)findViewById(R.id.review_negativePoint_value);
        positivePoint_label_textView=(TextView)findViewById(R.id.review_positivePoint_label);
        negativePoint_label_textView=(TextView)findViewById(R.id.review_negativePoint_label);

        Intent intent=getIntent();
        if(intent!=null){
            game_idIGDB=intent.getIntExtra("game_idIGDB",0);
            game_cover=intent.getStringExtra("game_cover");
            game_name=intent.getStringExtra("name");
            review_views=intent.getIntExtra("review_views",0);

            date=intent.getLongExtra("date",0);
            title=intent.getStringExtra("title");
            summary=intent.getStringExtra("summary");
            positive_point=intent.getStringExtra("positive_point");
            negative_point=intent.getStringExtra("negative_point");

            buildUI();

        }

    }

    public static String getDate(long milliSeconds)
    {


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        String myDate = dateFormat.format(new java.util.Date(milliSeconds));
        return myDate;
    }

    private void buildUI() {

        String uri=getString(R.string.uriCover)+game_cover+getString(R.string.jpg);
        Picasso.with(this).load(uri).into(cover);

        name.setText(game_name);
        views_textView.setText(review_views+" Views");

        //Log.i(this.getClass().getName(),milliseconds+"");

        date_textView.setText(getDate(date *1000));
        summary_textView.setText(summary);
        if(!positive_point.isEmpty()){
            positivePoint_label_textView.setVisibility(View.VISIBLE);
            positivePoint_value_textView.setText(positive_point);

        }else{
            positivePoint_label_textView.setVisibility(View.INVISIBLE);
            positivePoint_value_textView.setText("");
        }
        if(!negative_point.isEmpty()){
            negativePoint_label_textView.setVisibility(View.VISIBLE);
            negativePoint_value_textview.setText(negative_point);

        }else{
            negativePoint_label_textView.setVisibility(View.INVISIBLE);
            negativePoint_value_textview.setText("");
        }




    }
}
