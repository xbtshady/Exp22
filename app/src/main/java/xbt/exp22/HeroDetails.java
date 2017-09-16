package xbt.exp22;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import xbt.exp22.OverWatch.Hero;
import xbt.exp22.OverWatch.HeroSkill;
import xbt.exp22.http.HttpUtil;

public class HeroDetails extends AppCompatActivity {

    public static final String Hero_NAME = "hero_name";

    public static final String Hero_ID = "hero_id";

    private Handler handler;

    String imgUrl;

    private List<HeroSkill> heroSkillList = new ArrayList<>();

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
                    LinearLayout skillLayout = (LinearLayout) findViewById(R.id.skill_layout);
                    skillLayout.removeAllViews();
                    for(int j = 0; j < heroSkillList.size();j++){
                        HeroSkill heroSkill = heroSkillList.get(j);
                        View view = LayoutInflater.from(HeroDetails.this).inflate(R.layout.skill_item, skillLayout, false);
                        ImageView skillImg = (ImageView) view.findViewById(R.id.skill_img);
                        TextView skillName = (TextView) view.findViewById(R.id.skill_name);
                        TextView skillDescriptor = (TextView) view.findViewById(R.id.skill_descriptor);
                        Glide.with(HeroDetails.this).load(heroSkill.getSkillImg()).into(skillImg);
                        skillName.setText(heroSkill.getSkillName());
                        skillDescriptor.setText(heroSkill.getSkillDescriptor());
                        skillLayout.addView(view);
                    }
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
//                        Elements elements2 = doc.select(".hero-ability-icon m-weapon"); //读取普通攻击
                        Elements elements3 = doc.select(".hero-ability"); //读取技能
                        Elements elements4 = doc.select(".h5");//读取技能名字
                        Elements elements5 = doc.select(".hero-ability-descriptor");//读取技能描述

                        imgUrl = elements1.attr("src");
//                        for(int j = 0; j < elements2.size(); j++ ) {
//                            String skillName = elements2.get(j).attr("src");
//                            Log.d("message",skillName);
//                        }

                        for(int j = 0; j < elements4.size(); j++ ) {
                            String skillImgUrl = elements3.select("img").get(j).attr("src");
                            String skillName = elements4.get(j).text();
                            String skillDescriptor = elements5.get(j).select("p").text();
                            HeroSkill heroSkill = new HeroSkill(skillImgUrl,skillName,skillDescriptor);
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
}
