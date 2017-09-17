package xbt.exp22;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xbt.exp22.Fragment.Fragment2;
import xbt.exp22.Fragment.FragmentAdapter;
import xbt.exp22.Fragment.SkillFragment;
import xbt.exp22.OverWatch.HeroSkill;
import xbt.exp22.http.HttpUtil;

public class HeroDetails extends AppCompatActivity {

    public static final String Hero_NAME = "hero_name";

    public static final String Hero_ID = "hero_id";

    private Handler handler;

    String imgUrl;

    private List<HeroSkill> heroSkillList = new ArrayList<>();

    private List<String> list_title;
    private List<Fragment> list_fragment;



    private FragmentAdapter fAdapter;

    private SkillFragment nFragment;
    private Fragment2 sFragment;

    private TabLayout tab_title;
    private ViewPager vp_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_details);

        Intent intent = getIntent();
        final String heroName =intent.getStringExtra(Hero_NAME);
        String heroId =intent.getStringExtra(Hero_ID);
        String url = "http://ow.blizzard.cn/heroes/" + heroId;

        //标题栏设定
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //设置标题栏标题
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collaping_toobar);
        collapsingToolbar.setTitle(heroName);

        //让返回图标显示出来
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initHeroes(url);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    ImageView overWatchImageView = (ImageView) findViewById(R.id.hero_image_view);
                    Glide.with(HeroDetails.this).load(imgUrl).into(overWatchImageView);
//                    LinearLayout skillLayout = (LinearLayout) findViewById(R.id.skill_layout);
//                    skillLayout.removeAllViews();
//                    for(int j = 0; j < heroSkillList.size();j++){
//                        HeroSkill heroSkill = heroSkillList.get(j);
//                        View view = LayoutInflater.from(HeroDetails.this).inflate(R.layout.skill_item, skillLayout, false);
//                        ImageView skillImg = (ImageView) view.findViewById(R.id.skill_img);
//                        TextView skillName = (TextView) view.findViewById(R.id.skill_name);
//                        TextView skillDescriptor = (TextView) view.findViewById(R.id.skill_descriptor);
//                        Glide.with(HeroDetails.this).load(heroSkill.getSkillImg()).into(skillImg);
//                        skillName.setText(heroSkill.getSkillName());
//                        skillDescriptor.setText(heroSkill.getSkillDescriptor());
//                        skillLayout.addView(view);
//                    }
                    //标签栏
                    tab_title = (TabLayout)findViewById(R.id.tablayout);
                    vp_pager = (ViewPager)findViewById(R.id.viewpager);
                    fragmentChange();
                }
            }
        };



    }

    //读取数据
    private void initHeroes(String Url){
        HttpUtil.sendOkHttpRequest(Url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Document doc = Jsoup.parse(responseText);//将String类型的html转换为Document
                        Elements elements1 = doc.select(".hero-image"); //读取图片url
                        Elements elements2 = doc.select(".h2,.hero-detail-role-name"); //角色类型
                        Elements elements3 = doc.select(".hero-ability"); //读取技能
                        Elements elements4 = doc.select(".h5");//读取技能名字
                        Elements elements5 = doc.select(".hero-ability-descriptor");//读取技能描述
                        Elements elements6 = doc.select(".hero-detail-description");//读取英雄描述
                        Elements elements7 = doc.select(".hero-bio-copy");//读取英雄简介
                        Elements elements8 = doc.select(".h4,.hero-detail-title");//读取英雄台词
                        Elements elements9 = doc.select(".hero-bio-backstory");//读取英雄故事

                        Log.d("message", elements9.text());
                        List<String> heroBio = new ArrayList<String>();

                        for(int i= 0; i < elements7.size(); i++) {
                            heroBio.add(elements7.get(i).text());
                        }
                        heroBio.add(elements8.get(6).text());
                        heroBio.add(elements9.text());

                        imgUrl = elements1.attr("src");

                        for(int j = 0; j < elements4.size(); j++ ) {
                            String skillImgUrl = elements3.select("img").get(j).attr("src");
                            String skillName = elements4.get(j).text();
                            String skillDescriptor = elements5.get(j).select("p").text();
                            HeroSkill heroSkill = new HeroSkill(skillImgUrl,skillName,skillDescriptor,elements2.text(),elements6.text(),heroBio);
                            heroSkillList.add(heroSkill);
                        }
//                        for (int j = 0; j < elements4.size(); j++) {
//                            if(j == 0){
//                                String skillImgUrl = elements2.get(j).attr("src");
//                                Log.d("message",skillImgUrl);
//                                String skillName = elements4.get(j).text();
//                                Log.d("message",skillName);
//                                String skillDescriptor = elements5.get(j).select("p").text();
//                                Log.d("message",skillDescriptor);
//                                HeroSkill heroSkill = new HeroSkill(skillImgUrl, skillName,skillDescriptor );
//                                heroSkillList.add(heroSkill);
//
//                            }else {
//                                String skillImgUrl = elements3.get(j).attr("src");
//                                Log.d("message",skillImgUrl);
//                                String skillName = elements4.get(j).text();
//                                Log.d("message",skillName);
//                                String skillDescriptor = elements5.get(j).select("p").text();
//                                Log.d("message",skillDescriptor);
//                                HeroSkill heroSkill = new HeroSkill(skillImgUrl, skillName,skillDescriptor );
//                                heroSkillList.add(heroSkill);
//                            }
//                        }

                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                }).start();
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HeroDetails.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<HeroSkill> getHeroSkillList(){
        return heroSkillList;
    }

    private void fragmentChange()
    {
        list_fragment = new ArrayList<>();

        nFragment = new SkillFragment();
        sFragment = new Fragment2();


        list_fragment.add(nFragment);
        list_fragment.add(sFragment);

        list_title = new ArrayList<>();
        list_title.add("技能");
        list_title.add("概况");

        fAdapter = new FragmentAdapter(getSupportFragmentManager(),list_fragment,list_title);
        vp_pager.setAdapter(fAdapter);

        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vp_pager);
    }
}
