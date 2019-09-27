package my_class;

import cn.bmob.v3.BmobObject;

public class Application extends BmobObject {
    private User user_1;
    private User user_2;
    private String content;
    public Application(){
        user_1=new User();
        user_2=new User();
        content="加个好友吧";

    }

    public Application(User user_1,User user_2,String content){
        this.user_1=user_1;
        this.user_2=user_2;
        this.content=content;
    }



    public User getUser_1() {
        return user_1;
    }

    public User getUser_2() {
        return user_2;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser_2(User user_2) {
        this.user_2 = user_2;
    }

    public void setUser_1(User user_1) {
        this.user_1 = user_1;
    }
}
