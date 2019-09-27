package my_class;

import com.example.instantmessag.R;

public class Letter {
    private int image;
    private String name;
    private String time;
    private String content;


    public Letter(int image,String name,String time,String content){
        this.image=image;
        this.name=name;
        this.time=time;
        this.content=content;
    }

    public Letter(){
        image= R.drawable.avatar;
        name="昵称";
        time="2019年7月20日";
        content="留言内容";
    }


    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}
