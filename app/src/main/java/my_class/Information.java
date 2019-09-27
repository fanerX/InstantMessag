package my_class;

public class Information {
    private int image;//头像
    private String name;//昵称
    private String motto;//座右铭
    private String sex;//性别
    private int age;//年龄
    private String profession;//职业
    private String hometown;//家乡
    private String mailbox;//邮箱
    private String personalProfile;//个人说明

    public Information(){
        image=0;//头像
        name="昵称";//
        motto="座右铭";//座右铭
        sex="性别";//性别
        age=18;//年龄
        profession="职业";//职业
        hometown="家乡";//家乡
        mailbox="邮箱";//邮箱
        personalProfile="个人说明";//个人说明
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setPersonalProfile(String personalProfile) {
        this.personalProfile = personalProfile;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public String getMotto() {
        return motto;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getHometown() {
        return hometown;
    }

    public String getMailbox() {
        return mailbox;
    }

    public String getPersonalProfile() {
        return personalProfile;
    }

    public String getProfession() {
        return profession;
    }
}
