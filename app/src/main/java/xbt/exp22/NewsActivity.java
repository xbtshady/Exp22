package xbt.exp22;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import xbt.exp22.News.News;
import xbt.exp22.OverWatch.Hero;
import xbt.exp22.RecyclerView.HeroSelectAdapter;
import xbt.exp22.RecyclerView.NewsAdapter;
import xbt.exp22.http.HttpUtil;

public class NewsActivity extends AppCompatActivity {

    private List<News> newsList = new ArrayList<>();
    private Handler handler;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.news_recycle);
        initHeroes();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    GridLayoutManager layoutManager = new GridLayoutManager(NewsActivity.this,1);
                    recyclerView.setLayoutManager(layoutManager);
                    newsAdapter = new NewsAdapter(newsList);
                    recyclerView.setAdapter(newsAdapter);
                }
            }
        };
    }
    //读取数据
    private void initHeroes(){
        String weatherUrl = "http://ow.blizzard.cn/article/news/";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Document doc = Jsoup.parse(responseText);//将String类型的html转换为Document
                        Elements elements1 = doc.select(".bg"); //读取图片url
                        Elements elements2 = doc.select(".h6"); //读取title
                        Log.d("message",elements1.get(0).attr("href"));//读取链接
                        Log.d("message",geturl(elements1.get(0).attr("style")));//读取图片连接
                        Log.d("message",elements2.get(0).text());//读取标题
                        for (int j = 0; j < elements1.size(); j++) {
                            News news = new News(geturl(elements1.get(j).attr("style")), elements2.get(j).text(),elements1.get(j).attr("href"));
                            newsList.add(news);
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
                        Toast.makeText(NewsActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public String geturl(String url){
        String result = new String();
        int flag = 0;
        char[] s =url.toCharArray();
        for (char b:s){
            if (b == ')'){
                break;
            }
            if (flag == 1){
                result = result + b;
            }
            if(b == '('){
                flag = 1;
            }
        }
        return result;
    }
}
