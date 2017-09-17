package xbt.exp22.OverWatch;

import java.util.List;

/**
 * Created by xbt on 2017/9/16.
 */

public class HeroSkill {

    private String skillImg;

    private String skillName;

    private String skillDescriptor;

    private String heroStyle;

    private String heroSmileDescriptor;

    private List<String> heroBio;

    public HeroSkill(String skillImg, String skillName,String skillDescriptor,String heroStyle, String heroSmileDescriptor,List<String> heroBio ) {
        this.skillDescriptor = skillDescriptor;
        this.skillImg = skillImg;
        this.skillName = skillName;
        this.heroStyle = heroStyle;
        this.heroSmileDescriptor = heroSmileDescriptor;
        this.heroBio = heroBio;

    }

    public String getSkillImg() {
        return skillImg;
    }

    public void setSkillImg(String skillImg) {
        this.skillImg = skillImg;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillDescriptor() {
        return skillDescriptor;
    }

    public void setSkillDescriptor(String skillDescriptor) {
        this.skillDescriptor = skillDescriptor;
    }

    public String getHeroStyle() {
        return heroStyle;
    }

    public void setHeroStyle(String heroStyle) {
        this.heroStyle = heroStyle;
    }

    public String getHeroSmileDescriptor() {
        return heroSmileDescriptor;
    }

    public void setHeroSmileDescriptor(String heroSmileDescriptor) {
        this.heroSmileDescriptor = heroSmileDescriptor;
    }

    public List<String> getHeroBio() {
        return heroBio;
    }

    public void setHeroBio(List<String> heroBio) {
        this.heroBio = heroBio;
    }
}
