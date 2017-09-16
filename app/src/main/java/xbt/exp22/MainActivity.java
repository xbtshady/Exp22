package xbt.exp22;



import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
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
import xbt.exp22.RecyclerView.HeroSelectAdapter;
import xbt.exp22.http.HttpUtil;

public class MainActivity extends AppCompatActivity {

    private List<Hero> heroList = new ArrayList<>();
    private Handler handler;
    private HeroSelectAdapter heroSelectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initHeroes();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
                    recyclerView.setLayoutManager(layoutManager);
                    heroSelectAdapter = new HeroSelectAdapter(heroList);
                    recyclerView.setAdapter(heroSelectAdapter);
                }
            }
        };

    }

    //读取数据
    private void initHeroes(){
        String weatherUrl = "http://ow.blizzard.cn/heroes/";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Document doc = Jsoup.parse(responseText);//将String类型的html转换为Document
                        Elements elements1 = doc.select(".portrait"); //读取图片url
                        Elements elements2 = doc.select(".portrait-title");//读取英雄名字
                        Elements elements3 = doc.select(".hero-portrait-detailed");//读取英雄id
                        for (int j = 0; j < elements1.size(); j++) {
                            Hero hero = new Hero(elements1.get(j).attr("src"), elements2.get(j).text(),elements3.get(j).attr("data-hero-id"));
                            heroList.add(hero);
                        }
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
                        Toast.makeText(MainActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
