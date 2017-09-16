package xbt.exp22.OverWatch;

/**
 * Created by xbt on 2017/9/16.
 */

public class HeroSkill {

    private String skillImg;

    private String skillName;

    private String skillDescriptor;

    public HeroSkill(String skillImg, String skillName,String skillDescriptor ) {

        this.skillDescriptor = skillDescriptor;
        this.skillImg = skillImg;
        this.skillName = skillName;
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

}
