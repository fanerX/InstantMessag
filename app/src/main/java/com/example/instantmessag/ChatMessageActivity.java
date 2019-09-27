package com.example.instantmessag;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import collector.BaseActivity;
import my_adapter.ChatMessageAdapter;
import my_class.ChatMessage;
import my_class.SessionMessage;
import my_class.User;

import static java.lang.Thread.sleep;


public class ChatMessageActivity extends BaseActivity {


    private static boolean isReceive=true;
    private static boolean isEnd=true;

    private MediaPlayer mediaPlayer;

    private boolean have_session_message=true;

    private List<ChatMessage> chatMessageList=new ArrayList<>();
    private RecyclerView recyclerView;

    private Toolbar toolbar;
    private Button send_button;
    private EditText editText;
    private ChatMessageAdapter adapter;

    private int at_i;
    private int my_image=0;

    private User user_2,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        mediaPlayer =MediaPlayer.create(this,R.raw.ring);



        user = BmobUser.getCurrentUser(User.class);
        isEnd=true;

        //初始化我的头像
        {
            //SharedPreferences preferences=getSharedPreferences(user.getUsername()+"",MODE_PRIVATE);
            my_image=user.getImage();
        }
        //
        {
            Intent intent=getIntent();
            user_2=(User) intent.getSerializableExtra("user_2");
        }

        //initChatMessage();

        initSessionMessage();

        recyclerView=findViewById(R.id.recycler_view_chat);
        LinearLayoutManager layoutManager=new LinearLayoutManager(ChatMessageActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ChatMessageAdapter(chatMessageList);
        recyclerView.setAdapter(adapter);


        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(user_2.getName());
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.close);
        }



        send_button=findViewById(R.id.send_button);
        editText=findViewById(R.id.edit_message);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String  content=editText.getText().toString();
                editText.setText("");
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (!have_session_message){
                            makeSessionMessage();
                            have_session_message=true;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!have_session_message){
                                    makeSessionMessage();
                                    have_session_message=true;
                                }
                                if(!TextUtils.isEmpty(content)){
                                    //发送信息
                                    ChatMessage chatMessage=new ChatMessage();
                                    chatMessage.setIs_me(true);
                                    chatMessage.setContent(content);
                                    chatMessage.setImage(my_image);
                                    chatMessageList.add(chatMessage);
                                    adapter.notifyItemChanged(chatMessageList.size());

                                    //滚动到新消息的位置
                                    atToMessage(chatMessageList.size());
                                    //网络发送
                                    User user = BmobUser.getCurrentUser(User.class);

                                    String user_id_2=user_2.getObjectId();
//                                    if (user.getObjectId().equals("d9b18956c3")){
//                                        user_id_2="44e68439ee";
//                                    }else {
//                                        user_id_2="d9b18956c3";
//                                    }
                                    User user_2=new User();

                                    user_2.setObjectId(user_id_2);
                                    chatMessage.setUser_1(user);
                                    chatMessage.setUser_2(user_2);

                                    chatMessage.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null) {
                                                //Snackbar.make(mFabAddPost, "发布帖子成功：" + s, Snackbar.LENGTH_LONG).show();
                                                Log.d("Message","发布成功");
                                            } else {
                                                Log.d("Message", e.toString());
                                                //Snackbar.make(mFabAddPost, e.getMessage(), Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                }





                            }
                        });

                    }
                }).start();


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        isReceive=true;
        at_i=0;
        startReceive();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isReceive=false;
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_empty,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
    private void deleteMessage(final String objectId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChatMessage message=new ChatMessage();
                message.setObjectId(objectId);
                message.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.d("Message","删除成功");
                            at_i--;
                        }else{
                            Log.d("Message","删除失败"+e.toString());
                        }
                    }
                });

            }
        }).start();

    }

    private void atToMessage(final int at){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    recyclerView.scrollToPosition(at-1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startReceive(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Message","线程开始");

                initSessionMessage();

                while (isReceive){
                    if (isEnd){
                        isEnd=false;
                        BmobQuery<ChatMessage> eq1 = new BmobQuery<ChatMessage>();
                        eq1.addWhereEqualTo("user_1",user_2);
                        BmobQuery<ChatMessage> eq2 = new BmobQuery<ChatMessage>();
                        eq2.addWhereEqualTo("user_2",user);
                        List<BmobQuery<ChatMessage>> queries = new ArrayList<BmobQuery<ChatMessage>>();
                        queries.add(eq1);
                        queries.add(eq2);
                        BmobQuery<ChatMessage> mainQuery = new BmobQuery<ChatMessage>();
                        mainQuery.and(queries);
                        mainQuery.findObjects(new FindListener<ChatMessage>() {
                            @Override
                            public void done(List<ChatMessage> objects, BmobException e) {
                                if(e==null){
                                    boolean flag=false;
                                    ChatMessage message;
                                    for (int i=at_i;i<objects.size();i++){
                                        //message=new ChatMessage();
                                        message=objects.get(i);
                                        message.setIs_me(false);
                                        message.setImage(user_2.getImage());
                                        chatMessageList.add(message);
                                        at_i++;
                                        flag=true;
                                        deleteMessage(objects.get(i).getObjectId());
                                        //
                                        //
                                    }
                                    //for ending


                                    isEnd=true;
                                    if (flag){
                                        adapter.notifyItemChanged(chatMessageList.size()-1);
                                        atToMessage(chatMessageList.size());
                                        //响铃
                                        ring();
                                    }


                                    //toast("查询年龄6-29岁之间，姓名以'y'或者'e'结尾的人个数："+object.size());
                                }else{
                                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                }
                            }
                        });


                        //

                    }
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                Log.d("Message","线程结束");

            }
        }).start();
    }


    private void initSessionMessage(){
        if (user.getObjectId().equals(user_2.getObjectId())){
            return;
        }
        BmobQuery<SessionMessage> eq1 = new BmobQuery<SessionMessage>();
        eq1.addWhereEqualTo("user_1", user);
        BmobQuery<SessionMessage> eq2 = new BmobQuery<SessionMessage>();
        eq2.addWhereEqualTo("user_2", user_2);
        List<BmobQuery<SessionMessage>> queries = new ArrayList<BmobQuery<SessionMessage>>();
        queries.add(eq1);
        queries.add(eq2);
        BmobQuery<SessionMessage> mainQuery = new BmobQuery<SessionMessage>();
        mainQuery.and(queries);
        mainQuery.findObjects(new FindListener<SessionMessage>() {
            @Override
            public void done(List<SessionMessage> object, BmobException e) {
                if(e==null){
                    if (object.size()==0){
                        have_session_message=false;
                    }else {
                        have_session_message=true;
                    }
                    //toast("查询年龄6-29岁之间，姓名以'y'或者'e'结尾的人个数："+object.size());
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void makeSessionMessage(){
        SessionMessage sessionMessage_1 = new SessionMessage();
        sessionMessage_1.setImage(my_image);
        sessionMessage_1.setUser_1(user);
        sessionMessage_1.setUser_2(user_2);
        sessionMessage_1.setHaveUnread(false);
        sessionMessage_1.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //mObjectId = objectId;
                    //Snackbar.make(mBtnSave, "新增成功：" + mObjectId, Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    //Snackbar.make(mBtnSave, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
        SessionMessage sessionMessage_2 = new SessionMessage();
        sessionMessage_2.setImage(user_2.getImage());
        sessionMessage_2.setUser_1(user_2);
        sessionMessage_2.setUser_2(user);
        sessionMessage_2.setHaveUnread(false);
        sessionMessage_2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //mObjectId = objectId;
                    //Snackbar.make(mBtnSave, "新增成功：" + mObjectId, Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    //Snackbar.make(mBtnSave, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void ring(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
//                vibrator.vibrate(1000);
//                Uri defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(ChatMessageActivity.this, RingtoneManager.TYPE_RINGTONE);
//                Ringtone ringtone = RingtoneManager.getRingtone(ChatMessageActivity.this, defaultRingtoneUri);
//                ringtone.play();
//                try {
//                    sleep(1000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//                ringtone.stop();
//            }
//
//        }).start();

//        File file=new File("\\raw\\ring.mp3");
//        try {
//            mediaPlayer.setDataSource(file.getPath());
//            mediaPlayer.prepare();
//            if (!mediaPlayer.isPlaying()){
//                mediaPlayer.start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //mediaPlayer.prepareAsync();
//            if (!mediaPlayer.isPlaying()){
//                mediaPlayer.start();
//            }
        mediaPlayer.start();
        Log.d("ChatRing", "ring: ring");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
