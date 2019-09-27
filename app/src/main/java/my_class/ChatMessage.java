package my_class;

import cn.bmob.v3.BmobObject;

public class ChatMessage extends BmobObject {
    protected boolean is_me;
    protected int image;
    protected String content;

    //private boolean ring;

    protected User user_1;
    protected User user_2;


    public User getUser_1() {
        return user_1;
    }

    public User getUser_2() {
        return user_2;
    }


    public void setUser_1(User user_1) {
        this.user_1 = user_1;
    }

    public void setUser_2(User user_2) {
        this.user_2 = user_2;
    }

    public ChatMessage(){
        image= 0;
        content="聊天内容";
        //ring=false;
        is_me=false;
    }

    public ChatMessage(int i){//
        image= 0;
        content="聊天内容";
        is_me=false;
        //user_1=new User();
    }
    public ChatMessage(ChatMessage message){
        image=message.getImage();
        content=message.getContent();
        is_me=false;
        user_1=message.getUser_1();
    }
    public ChatMessage(int image,String content,boolean is_me){
        this.image=image;
        this.content=content;
        this.is_me=is_me;
    }



    public int getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public boolean isIs_me() {
        return is_me;
    }

    public void setIs_me(boolean is_me) {
        this.is_me = is_me;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
