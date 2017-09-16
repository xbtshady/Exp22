package xbt.exp22.OverWatch;

/**
 * Created by xbt on 2017/9/15.
 */

public class Hero {

    private String imageUrl;

    private String name;

    private String heroId;

    public Hero(String imageUrl, String name, String heroId) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.heroId = heroId;
    }

    public String getUrl() {
        return imageUrl;
    }

    public void setUrl(String url) {
        this.imageUrl = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId) {
        this.heroId = heroId;
    }
}
