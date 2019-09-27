package my_class;

import cn.bmob.v3.BmobObject;

public class MyFriend extends BmobObject {
    private boolean isMyfriend;
    private User user_1;
    private User user_2;


    public MyFriend(){
        user_1=new User();
        user_2=new User();
        isMyfriend=false;
    }

    public User getUser_2() {
        return user_2;
    }

    public User getUser_1() {
        return user_1;
    }

    public void setUser_1(User user_1) {
        this.user_1 = user_1;
    }

    public void setUser_2(User user_2) {
        this.user_2 = user_2;
    }

    public boolean isMyfriend() {
        return isMyfriend;
    }

    public void setMyfriend(boolean myfriend) {
        isMyfriend = myfriend;
    }

}
