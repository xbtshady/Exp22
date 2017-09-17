package xbt.exp22.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xbt.exp22.HeroDetails;
import xbt.exp22.OverWatch.HeroSkill;
import xbt.exp22.R;

/**
 * Created by xbt on 2017/9/17.
 */

public class SkillFragment extends Fragment {

    View view1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view1 =  inflater.inflate(R.layout.skill_fragment, container, false);
        refresh(view1);
        return view1;
    }

    public void refresh(View view1){

        HeroDetails activity = ( HeroDetails) getActivity();

        List<HeroSkill> heroSkillList = new ArrayList<>();

        heroSkillList = activity.getHeroSkillList();

        LinearLayout skillLayout = (LinearLayout) view1.findViewById(R.id.skill_layout);

        TextView heroStyle = (TextView) view1.findViewById(R.id.hero_style);
        TextView heroSmileDescriptor = (TextView) view1.findViewById(R.id.heroSmileDescriptor);

        heroStyle.setText(heroSkillList.get(1).getHeroStyle());
        heroSmileDescriptor.setText(heroSkillList.get(1).getHeroSmileDescriptor());

        skillLayout.removeAllViews();

        for(int j = 0; j < heroSkillList.size();j++){
            HeroSkill heroSkill = heroSkillList.get(j);
            View view2 = LayoutInflater.from(getContext()).inflate(R.layout.skill_item, skillLayout, false);
            ImageView skillImg = (ImageView) view2.findViewById(R.id.skill_img);
            TextView skillName = (TextView) view2.findViewById(R.id.skill_name);
            TextView skillDescriptor = (TextView) view2.findViewById(R.id.skill_descriptor);
            Glide.with(getContext()).load(heroSkill.getSkillImg()).into(skillImg);
            skillName.setText(heroSkill.getSkillName());
            skillDescriptor.setText(heroSkill.getSkillDescriptor());
            skillLayout.addView(view2);
        }
    }
}
