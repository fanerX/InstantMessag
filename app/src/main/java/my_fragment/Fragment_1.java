package my_fragment;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instantmessag.ChatMessageActivity;
import com.example.instantmessag.ImageList;
import com.example.instantmessag.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import my_adapter.SessionMessageAdapter;
import my_class.ChatMessage;
import my_class.MyFriend;
import my_class.SessionMessage;
import my_class.User;
import my_view.SlideRecyclerView;

import static android.content.Context.VIBRATOR_SERVICE;
import static java.lang.Thread.sleep;

public class Fragment_1 extends Fragment {
    private MediaPlayer mediaPlayer;

    private User user;
    private static boolean isReceive=true;
    private static boolean isEnd=true;
    private int at_i;

    private  List<SessionMessage> messageList=new ArrayList<>();
    private  List<User> userList=new ArrayList<>();
    private SlideRecyclerView recyclerView;
    private SessionMessageAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initToUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment_1, container, false);
        user = BmobUser.getCurrentUser(User.class);
        at_i=0;

//        initMessages();

        mediaPlayer=MediaPlayer.create(getContext(),R.raw.ring);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView=view.findViewById(R.id.recycler_view_session_message);
        adapter =new SessionMessageAdapter(messageList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void initMessages(){
        messageList.clear();
        userList.clear();
        BmobQuery<SessionMessage> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("user_2", user);
        categoryBmobQuery.include("user_1.");
        categoryBmobQuery.findObjects(new FindListener<SessionMessage>() {
            @Override
            public void done(List<SessionMessage> object, BmobException e) {
                if (e == null) {
                    SessionMessage message;
                    for (int i=0;i<object.size();i++){
                        message=object.get(i);
                        userList.add(message.getUser_1());
                        message.setHaveUnread(false);
                        messageList.add(message);
                    }
                    adapter.notifyItemChanged(object.size());
                    //Snackbar.make(mBtnEqual, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    //Snackbar.make(mBtnEqual, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });







    }

    @Override
    public void onStop() {
        super.onStop();
        isReceive=false;
    }

    @Override
    public void onStart() {
        super.onStart();
        isReceive=true;
        initMessages();
        startReceive();
    }





    private void startReceive(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Message","线程开始");
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (isReceive){
                    if (isEnd){
                        isEnd=false;
                        BmobQuery<ChatMessage> query = new BmobQuery<ChatMessage>();
                        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
                        query.addWhereEqualTo("user_2",user);
                        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
                        query.include("user_1");
                        query.findObjects(new FindListener<ChatMessage>() {
                            @Override
                            public void done(List<ChatMessage> list, BmobException e) {
                                if(e==null){
                                    ChatMessage message;
                                    boolean isRing=false;
                                    for (int i=0;i<list.size();i++){
                                        message=list.get(i);
                                        if (!inUserList(message.getUser_1())){
                                            userList.add(message.getUser_1());
                                            messageList.add(new SessionMessage(message));

                                            final int position=i;

                                            adapter.notifyItemInserted(position);
                                        }else {
                                            int position=atMessageListPosition(message.getUser_1());
                                            //Collections.swap(messageList, position, 0);
                                            adapter.notifyItemChanged(position);
                                        }
//                                        if (!message.isRing()){
//                                            isRing=true;
//                                        }
                                        isRing=true;
                                    }
                                    if (isRing){
                                        mediaPlayer.start();
                                        Vibrator vibrator = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
                                        vibrator.vibrate(1000);
//                                        getActivity().runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Vibrator vibrator = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
//                                                vibrator.vibrate(1000);
//                                                Uri defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_RINGTONE);
//                                                Ringtone ringtone = RingtoneManager.getRingtone(getContext(), defaultRingtoneUri);
//                                                ringtone.play();
//
//                                                try {
//                                                    sleep(1000);
//                                                } catch (InterruptedException e1) {
//                                                    e1.printStackTrace();
//                                                }
//                                                ringtone.stop();
//                                            }
//
//                                        });



                                    }


                                    isEnd=true;
                                }
                            }
                        });
                    }
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                Log.d("Message","线程结束");
            }
        }).start();
    }

    private boolean inUserList( User user){
        for(User user1:userList){
            if (user1.getObjectId().equals(user.getObjectId())){
                return true;
            }
        }
        return false;
    }

    private int atMessageListPosition(User user_1){
        for (int i=0;i<messageList.size();i++){
            if (messageList.get(i).getUser_1().getObjectId().equals(user_1.getObjectId())){
                messageList.get(i).setHaveUnread(true);
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
