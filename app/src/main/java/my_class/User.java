package my_class;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser implements Serializable {

    private int image;//头像
    private String name;//昵称
    private String motto;//座右铭
    private String sex;//性别
    private int age;//年龄
    private String profession;//职业
    private String hometown;//家乡
    private String mailbox;//邮箱
    private String personalProfile;//个人说明

    public User(){
        image=0;
        name="昵称";
        motto="座右铭";
        sex="男";
        age=18;
        profession="职业";
        hometown="家乡";
        mailbox="邮箱";
        personalProfile="个人说明";
    }
    public User(String objeckId){
        image=0;
        name="昵称";
        motto="座右铭";
        sex="男";
        age=18;
        profession="职业";
        hometown="家乡";
        mailbox="邮箱";
        personalProfile="个人说明";
        setObjectId(objeckId);
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
