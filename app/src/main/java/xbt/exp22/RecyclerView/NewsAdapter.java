package xbt.exp22.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import xbt.exp22.HeroDetails;
import xbt.exp22.News.News;
import xbt.exp22.OverWatch.Hero;
import xbt.exp22.R;

/**
 * Created by xbt on 2017/9/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>  {

        private Context mContext;

        private List<News> mNews;

        static class ViewHolder extends RecyclerView.ViewHolder{

            CardView cardView;
            ImageView newsImage;
            TextView newsTitle;

            public ViewHolder(View view){
                super(view);
                cardView = (CardView) view;
                newsImage = (ImageView) view.findViewById(R.id.news_image);
                newsTitle = (TextView) view.findViewById(R.id.news_title);
            }
        }

        public NewsAdapter(List<News> newssList){
            mNews = newssList;
        }

        public xbt.exp22.RecyclerView.NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(mContext == null){
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.news,parent,false);
            return   new ViewHolder(view);
        }

        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            News news = mNews.get(position);
            viewHolder.newsTitle.setText(news.getNewsTitle());
            Glide.with(mContext).load(news.getNewsImg()).into(viewHolder.newsImage);
        }

        public int getItemCount() {
            return mNews.size();
        }
}
