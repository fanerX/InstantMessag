package my_class;


import com.example.instantmessag.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyDataGet {
    static String  string_motto="如果分手的恋人还能做朋友，要不从没爱过，要不还在爱着。";
    public static int[] myStrings= new int[]{
            R.string.string_1,R.string.string_2,R.string.string_3,R.string.string_4,R.string.string_5,
            R.string.string_6,R.string.string_7,R.string.string_8,R.string.string_9,R.string.string_10,
            R.string.string_11,R.string.string_12
    };
    public static String[] myString_string=new String[]{
            "你总是这样轻言放弃的话，无论多久都只会原地踏步",
            "生命在于折腾，生命不息，折腾不止",
            "你什么时候放下，什么时候就没有烦恼",
            "有一种无奈叫做，你明明是只候鸟，却喜欢上只能给你冬天的人。",
            "积极者相信只有推动自己才能推动世界，只要推动自己就能推动世界。",
            "找不到路，就自己走一条出来。",
            "没有回忆就去创造回忆，没有道路就去开辟道路。",
            "穿山甲到底说了什么？",
            "一瓶250ml的吊水，一共是3111滴。",
            "这不是神经病，是理想",
            "有些人，一旦遇见，便一眼万年；有些心动，一旦开始，便覆水难收。",
            "愿人生如剑、立起寒光四射、躺倒四射寒光"
    };

    public MyDataGet(){

    }

    public String getAMotto(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=getBuildHttpClient();
                    Request request=new Request.Builder()
                            .url("https://v1.hitokoto.cn/?c=f&encode=text")
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    string_motto= response.body().string();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
        return string_motto;
    }
    private OkHttpClient getBuildHttpClient() {
        return new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(30, TimeUnit.SECONDS).build();
    }


}
