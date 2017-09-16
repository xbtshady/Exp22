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
import xbt.exp22.OverWatch.Hero;
import xbt.exp22.R;

/**
 * Created by xbt on 2017/9/16.
 */

public class HeroSelectAdapter  extends RecyclerView.Adapter<HeroSelectAdapter.ViewHolder> {

    private Context mContext;

    private List<Hero> mHero;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView heroImage;
        TextView heroName;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            heroImage = (ImageView) view.findViewById(R.id.hero_image_card);
            heroName = (TextView) view.findViewById(R.id.hero_name_card);
        }
    }

    public HeroSelectAdapter(List<Hero> HeroList){
        mHero = HeroList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.hero_select,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Hero hero = mHero.get(position);
                Intent intent = new Intent(mContext,HeroDetails.class);
                intent.putExtra(HeroDetails.Hero_NAME, hero.getName());
                intent.putExtra(HeroDetails.Hero_ID, hero.getHeroId());
                mContext.startActivity(intent);
            }
        });
        return   holder;
    }
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Hero hero = mHero.get(position);
        viewHolder.heroName.setText(hero.getName());
        Glide.with(mContext).load(hero.getUrl()).into(viewHolder.heroImage);
    }

    public int getItemCount() {
        return mHero.size();
    }
}
