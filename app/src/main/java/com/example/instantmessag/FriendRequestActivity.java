package com.example.instantmessag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import collector.BaseActivity;
import my_class.Application;
import my_class.User;

public class FriendRequestActivity extends BaseActivity {
    private Button cancel_button,request_button;
    private EditText edit_request;
    private User user_2,user_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
        user_1 = BmobUser.getCurrentUser(User.class);
        cancel_button=findViewById(R.id.cancel_button);
        request_button=findViewById(R.id.request_button);
        edit_request=findViewById(R.id.edit_request);
        {
            edit_request.setText("我是"+user_1.getName());
        }
        Intent intent=getIntent();
        user_2=(User)intent.getSerializableExtra("user_2");
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FriendRequestActivity.this,InformationDisplay.class);
                intent.putExtra("user_2",user_2);
                startActivity(intent);
                finish();
            }
        });
        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string=edit_request.getText().toString().trim();
                if (TextUtils.isEmpty(string)){
                    Toast.makeText(FriendRequestActivity.this,"验证消息不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    {
                        Application application=new Application();
                        application.setUser_1(user_1);
                        application.setUser_2(user_2);
                        application.setContent(string);

                        final Application application_=new Application(user_1,user_2,string);
                        BmobQuery<Application> eq1 = new BmobQuery<Application>();
                        eq1.addWhereEqualTo("user_1",user_1);
                        BmobQuery<Application> eq2 = new BmobQuery<Application>();
                        eq2.addWhereEqualTo("user_2",user_2);
                        List<BmobQuery<Application>> queries = new ArrayList<BmobQuery<Application>>();
                        queries.add(eq1);
                        queries.add(eq2);
                        BmobQuery<Application> mainQuery = new BmobQuery<Application>();
                        mainQuery.and(queries);
                        mainQuery.findObjects(new FindListener<Application>() {
                            @Override
                            public void done(List<Application> object, BmobException e) {
                                if(e==null){
                                    if (object.size()==0){
                                        application_.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(FriendRequestActivity.this,"申请发送成功",Toast.LENGTH_SHORT).show();
                                                    Log.d("application","application发送成功");
                                                } else {
                                                    Log.d("application","application发送失败"+e.toString());
                                                }
                                            }
                                        });
                                    }else {
                                        Toast.makeText(FriendRequestActivity.this,"已经发送申请,请耐心等待",Toast.LENGTH_SHORT).show();
                                    }
                                    //toast("查询年龄6-29岁之间，姓名以'y'或者'e'结尾的人个数："+object.size());
                                }else{
                                    Log.d("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                }
                            }
                        });
                    }
                    Intent intent=new Intent(FriendRequestActivity.this,InformationDisplay.class);
                    intent.putExtra("user_2",user_2);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    @Override
    protected void onStop() {
        super.onStop();
        //
        finish();
    }
}
