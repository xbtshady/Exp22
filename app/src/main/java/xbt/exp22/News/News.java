package xbt.exp22.News;

/**
 * Created by xbt on 2017/9/17.
 */

public class News {
    private String newsImg;

    private String newsTitle;

    private String newsUrl;

    public String getNewsImg() {
        return newsImg;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public News(String newsImg,String newsTitle,String newsUrl){
        this.newsImg = newsImg;
        this.newsTitle = newsTitle;
        this.newsUrl = newsUrl;
    }

}
