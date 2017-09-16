package xbt.exp22.ListView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import xbt.exp22.OverWatch.Hero;
import xbt.exp22.R;

/**
 * Created by xbt on 2017/9/15.
 */

public class HeroAdapter extends ArrayAdapter<Hero> {

    private int resourceId;

    public HeroAdapter(Context context, int textViewResourceId, List<Hero> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Hero hero = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView heroImg = (ImageView) view.findViewById(R.id.hero_img);
        TextView  heroName = (TextView) view.findViewById(R.id.hero_name);
        Glide.with(getContext()).load(hero.getUrl()).into(heroImg);
        heroName.setText(hero.getName());
        return view;
    }

}
