package com.example.myapplicationstyle.ui.reviews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationstyle.DataBase.ReviewEntry;
import com.example.myapplicationstyle.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.vh> {
    onClick_ReviewAdapater onClick_reviewAdapater;

    public ReviewAdapter(onClick_ReviewAdapater onClick_reviewAdapater) {
        this.onClick_reviewAdapater = onClick_reviewAdapater;
    }

    public interface onClick_ReviewAdapater{
        void onClick_Review(ReviewEntry review);
    }

    private ArrayList<ReviewEntry> reviewList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        int layout=R.layout.review_view_holder;

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layout,parent,false);
        return new vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vh holder, int position) {
        ReviewEntry reviewEntry=reviewList.get(position);
        holder.critic.setText(reviewEntry.getViews()+ context.getString(R.string.review_views));
        holder.title.setText(reviewEntry.getTitle());

        long milliseconds=reviewEntry.getCreated_at();
        Log.i(this.getClass().getName(),milliseconds+"");
        String date=getDate(milliseconds *1000);
        holder.date.setText(date);

    }

    @Override
    public int getItemCount() {
        if(reviewList==null){
            return 0;
        }else{
            return reviewList.size();
        }

    }

    public void setData(ArrayList<ReviewEntry> reviewsList) {
        this.reviewList=reviewsList;
    }

    public static String getDate(long milliSeconds)
    {


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        String myDate = dateFormat.format(new java.util.Date(milliSeconds));
        return myDate;
    }

    public class vh extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView critic;
        TextView title;
        TextView date;

        public vh(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            critic=(TextView)itemView.findViewById(R.id.review_critic_value);
            title=(TextView)itemView.findViewById(R.id.review_title_value);
            date=(TextView)itemView.findViewById(R.id.review_date_view_holder);
        }

        @Override
        public void onClick(View v) {
            ReviewEntry reviewEntry=reviewList.get(getAdapterPosition());
            onClick_reviewAdapater.onClick_Review(reviewEntry);
        }
    }
}
