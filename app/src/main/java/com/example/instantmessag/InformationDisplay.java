package com.example.instantmessag;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import collector.BaseActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import my_class.Information;
import my_class.MyFriend;
import my_class.SessionMessage;
import my_class.User;

public class InformationDisplay extends BaseActivity {

    private Button leave_button,send_button,add_friend;
    private CircleImageView image;
    private TextView nickname,motton,sex,age,profession,hometown,mailbox,personalProfile,id_text;
    private ImageView avatar_background;
    private LinearLayout linearLayout_button;
    private User user_2;
    private User user;

//    private ImageView return_top,setting_top;

    private Button delete_friend;

    private boolean isFriend=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_display);
        leave_button=findViewById(R.id.leave_button);
        send_button=findViewById(R.id.send_button);
        avatar_background=findViewById(R.id.avatar_background);
        add_friend=findViewById(R.id.add_friend);
        linearLayout_button=findViewById(R.id.linearLayout_button);
        user= BmobUser.getCurrentUser(User.class);
        Intent intent=getIntent();
        user_2=(User) intent.getSerializableExtra("user_2");
        isFriend=intent.getBooleanExtra("isFriend",false);

        image=findViewById(R.id.image);
        nickname=findViewById(R.id.nickname_edit);
        motton=findViewById(R.id.motto_edit);
        age=findViewById(R.id.age_edit);
        profession=findViewById(R.id.profession_edit);
        hometown=findViewById(R.id.hometown_edit);
        mailbox=findViewById(R.id.mailbox_edit);
        personalProfile=findViewById(R.id.personal_profile_edit);
        sex=findViewById(R.id.sex_edit);
        id_text=findViewById(R.id.id_text);



        leave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转到留言活动
//                Intent intent=new Intent(v.getContext(),LeaveMessageActivity.class);
//                startActivity(intent);
            }
        });

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转到聊天活动
                Intent intent=new Intent(InformationDisplay.this,ChatMessageActivity.class);
                intent.putExtra("user_2",user_2);
                startActivity(intent);
            }
        });

        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<MyFriend> eq1 = new BmobQuery<MyFriend>();
                eq1.addWhereEqualTo("user_1",user);
                BmobQuery<MyFriend> eq2 = new BmobQuery<MyFriend>();
                eq2.addWhereEqualTo("user_2",user_2);
                List<BmobQuery<MyFriend>> queries = new ArrayList<BmobQuery<MyFriend>>();
                queries.add(eq1);
                queries.add(eq2);
                BmobQuery<MyFriend> mainQuery = new BmobQuery<MyFriend>();
                mainQuery.and(queries);
                mainQuery.findObjects(new FindListener<MyFriend>() {
                    @Override
                    public void done(List<MyFriend> object, BmobException e) {
                        if(e==null){
                            if (object.size()==0){
                                //
                                Intent intent=new Intent(InformationDisplay.this,FriendRequestActivity.class);
                                intent.putExtra("user_2",user_2);
                                startActivity(intent);
                            }else {
                                Toast.makeText(InformationDisplay.this,"已经是好友,可从好友列表选择",Toast.LENGTH_SHORT).show();
                                //Toast.makeText(InformationDisplay.this,"请从好友列表中选择",Toast.LENGTH_SHORT).show();
                            }
                            Log.d("search","成功：");
                            //toast("查询年龄6-29岁之间，姓名以'y'或者'e'结尾的人个数："+object.size());
                        }else{
                            Log.d("search","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
            }
        });

        {
            delete_friend=findViewById(R.id.delete_friend);
            delete_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(InformationDisplay.this);
                    dialog.setTitle("删除好友");
                    dialog.setMessage("是否要删除该好友");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.create().show();
                }
            });
        }
        //获得对象实例化信息,初始化界面
        {
            image.setImageResource(ImageList.image_id[user_2.getImage()]);
            Glide.with(InformationDisplay.this)
                    .load(ImageList.image_id[user_2.getImage()])
                    .bitmapTransform(new BlurTransformation(InformationDisplay.this,20,2))
                    .into(avatar_background);
            nickname.setText(user_2.getName());
            id_text.setText(user_2.getUsername());
            motton.setText(user_2.getMotto());
            age.setText(user_2.getAge()+"");
            profession.setText(user_2.getProfession());
            hometown.setText(user_2.getHometown());
            mailbox.setText(user_2.getMailbox());
            personalProfile.setText(user_2.getPersonalProfile());
            sex.setText(user_2.getSex());
            if(!isFriend){
                linearLayout_button.setVisibility(View.GONE);
                delete_friend.setVisibility(View.GONE);
                add_friend.setVisibility(View.VISIBLE);
            }

        }


    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    private void delete(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    BmobQuery<MyFriend> eq1 = new BmobQuery<MyFriend>();
                    eq1.addWhereEqualTo("user_1", user);
                    BmobQuery<MyFriend> eq2 = new BmobQuery<MyFriend>();
                    eq2.addWhereEqualTo("user_2", user_2);
                    List<BmobQuery<MyFriend>> queries = new ArrayList<BmobQuery<MyFriend>>();
                    queries.add(eq1);
                    queries.add(eq2);
                    BmobQuery<MyFriend> mainQuery = new BmobQuery<MyFriend>();
                    mainQuery.and(queries);
                    mainQuery.findObjects(new FindListener<MyFriend>() {
                        @Override
                        public void done(List<MyFriend> object, BmobException e) {
                            if(e==null){
                                for (int i=0;i<object.size();i++){
                                    object.get(i).delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                //Snackbar.make(mBtnDelete, "删除成功", Snackbar.LENGTH_LONG).show();
                                            } else {
                                                Log.e("BMOB", e.toString());
                                                //Snackbar.make(mBtnDelete, e.getMessage(), Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                                //toast("查询年龄6-29岁之间，姓名以'y'或者'e'结尾的人个数："+object.size());
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }
                {
                    BmobQuery<MyFriend> eq1 = new BmobQuery<MyFriend>();
                    eq1.addWhereEqualTo("user_1", user_2);
                    BmobQuery<MyFriend> eq2 = new BmobQuery<MyFriend>();
                    eq2.addWhereEqualTo("user_2", user);
                    List<BmobQuery<MyFriend>> queries = new ArrayList<BmobQuery<MyFriend>>();
                    queries.add(eq1);
                    queries.add(eq2);
                    BmobQuery<MyFriend> mainQuery = new BmobQuery<MyFriend>();
                    mainQuery.and(queries);
                    mainQuery.findObjects(new FindListener<MyFriend>() {
                        @Override
                        public void done(List<MyFriend> object, BmobException e) {
                            if(e==null){
                                for (int i=0;i<object.size();i++){
                                    object.get(i).delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                //Snackbar.make(mBtnDelete, "删除成功", Snackbar.LENGTH_LONG).show();
                                            } else {
                                                Log.e("BMOB", e.toString());
                                                //Snackbar.make(mBtnDelete, e.getMessage(), Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                                //toast("查询年龄6-29岁之间，姓名以'y'或者'e'结尾的人个数："+object.size());
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }

            }
        }).start();
    }
}
