package xbt.exp22.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xbt.exp22.HeroDetails;
import xbt.exp22.OverWatch.HeroSkill;
import xbt.exp22.R;

/**
 * Created by xbt on 2017/9/16.
 */

public class Fragment2 extends Fragment {

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.f2, container, false);
        refresh(view);
        return view;
    }

    private void refresh(View view){
        HeroDetails activity = ( HeroDetails) getActivity();

        List<HeroSkill> heroSkillList = new ArrayList<>();

        heroSkillList = activity.getHeroSkillList();

        HeroSkill heroSkill = heroSkillList.get(1);

        TextView hero1 = (TextView) view.findViewById(R.id.hero1);
        TextView hero2 = (TextView) view.findViewById(R.id.hero2);
        TextView hero3 = (TextView) view.findViewById(R.id.hero3);
        TextView hero4 = (TextView) view.findViewById(R.id.hero4);
        TextView hero5 = (TextView) view.findViewById(R.id.hero5);
        TextView hero6 = (TextView) view.findViewById(R.id.hero6);

        hero1.setText(heroSkill.getHeroBio().get(0));
        hero2.setText(heroSkill.getHeroBio().get(1));
        hero3.setText(heroSkill.getHeroBio().get(2));
        hero4.setText(heroSkill.getHeroBio().get(3));
        hero5.setText(heroSkill.getHeroBio().get(4));
        hero6.setText(heroSkill.getHeroBio().get(5));

    }
}
