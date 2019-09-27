package my_class;

import cn.bmob.v3.BmobObject;

public class SessionMessage extends BmobObject {

    protected int image;
    //protected String content;
    private boolean haveUnread;
    private User user_1,user_2;

    public SessionMessage(){
        //super(1);
        haveUnread=true;
    }
    public SessionMessage(ChatMessage message){
        //super(message);
        user_1=message.getUser_1();
        //user_2=message.getUser_2();
        image=message.getImage();
        //content=message.getContent();
        haveUnread=true;
    }


//    public String getContent() {
//        return content;
//    }

    public User getUser_2() {
        return user_2;
    }

    public User getUser_1() {
        return user_1;
    }

    public int getImage() {
        return image;
    }

//    public void setContent(String content) {
//        this.content = content;
//    }

    public void setUser_1(User user_1) {
        this.user_1 = user_1;
    }

    public void setUser_2(User user_2) {
        this.user_2 = user_2;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public boolean isHaveUnread() {
        return haveUnread;
    }

    public void setHaveUnread(boolean haveUnread) {
        this.haveUnread = haveUnread;
    }
}
