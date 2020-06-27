package com.example.myapplicationstyle.ui.Feeds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationstyle.DataBase.FeeedEntry;
import com.example.myapplicationstyle.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.vh>  {

    ArrayList<FeeedEntry> feedList=new ArrayList<>();
    Context context;
    onClickFeedAdapter onClickFeedAdapterObj;

    public FeedAdapter(onClickFeedAdapter onClickFeedAdapterObj) {
        this.onClickFeedAdapterObj=onClickFeedAdapterObj;
    }

    public interface onClickFeedAdapter{
        void onClickFeed(FeeedEntry feeedEntry);
    }

    @NonNull
    @Override
    public vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        int layout= R.layout.feed_view_holder;

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layout,parent,false);

        return new vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vh holder, int position) {
        FeeedEntry feeedEntry=feedList.get(position);
        int resource=0;
        String category_label=context.getString(R.string.category);
        int category=feeedEntry.getCategory();
        if( category==1){
            resource=R.drawable.ic_article_24px;
            category_label=context.getString(R.string.pulse_value);
        }else if(category==2){
            resource=R.drawable.ic_next_plan_24px;
            category_label=context.getString(R.string.coming_soon_value);
        }else if(category==3){
            resource=R.drawable.ic_fiber_new_24px;
            category_label=context.getString(R.string.newTrailer_value);

        }else if(category==4){
            resource=R.drawable.ic_supervised_user_circle_24px;
            category_label=context.getString(R.string.user_contribuited_item_value);
        }else if(category==5){
            resource=R.drawable.ic_supervisor_account_24px;
            category_label=context.getString(R.string.user_contribuition_item_value);

        }else if(category==6){
            resource=R.drawable.ic_article_24px;
            category_label=context.getString(R.string.page_contributed_item_value);

        }
        holder.icon_category.setBackgroundResource(resource);
        holder.category.setText(category_label);
        String date=getDate(feeedEntry.getCreatedAt() *1000);
        holder.date.setText(date);
        holder.games_related.setText(feeedEntry.getGames_related());


    }

    public static String getDate(long milliSeconds)
    {


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        String myDate = dateFormat.format(new java.util.Date(milliSeconds));
        return myDate;
    }

    @Override
    public int getItemCount() {
        if(feedList!=null){
            return   feedList.size();
        }else{
            return 0;
        }
    }

    public void setData(ArrayList<FeeedEntry> feedList) {
        this.feedList=feedList;
    }

    public class vh extends RecyclerView.ViewHolder implements View.OnClickListener
             {
          ImageView icon_category;
          TextView category;
          TextView games_related;
          TextView date;

        public vh(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            icon_category=(ImageView)itemView.findViewById(R.id.feed_category_icon);
            category=(TextView)itemView.findViewById(R.id.category_value);
            games_related=(TextView)itemView.findViewById(R.id.feed_games_related);
            date=(TextView)itemView.findViewById(R.id.feed_createdAt);

        }

        @Override
        public void onClick(View v) {
            FeeedEntry feeedEntry=feedList.get(getAdapterPosition());
            onClickFeedAdapterObj.onClickFeed(feeedEntry);

        }


    }
}
