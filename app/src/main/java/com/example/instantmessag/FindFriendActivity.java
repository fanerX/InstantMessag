package com.example.instantmessag;


import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import collector.BaseActivity;
import my_adapter.ApplicationAdapter;
import my_adapter.MyFriendsAdapter;
import my_class.Application;
import my_class.MyFriend;
import my_class.User;

import static java.lang.Thread.sleep;

public class FindFriendActivity extends BaseActivity {

    private Toolbar toolbar;

    private List<MyFriend> friendList=new ArrayList<>();
    private List<Application> applicationList=new ArrayList<>();

    private RecyclerView recyclerView,recyclerView_select;
    private Button search_button;
    private EditText editText;
    private MyFriendsAdapter adapter;
    private ApplicationAdapter applicationAdapter;
    //private User user;

    LinearLayoutManager layoutManager,linearLayoutManager_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.close);
        }


        search_button=findViewById(R.id.search_button);
        editText=findViewById(R.id.edit_search);

        recyclerView=findViewById(R.id.recycler_view_find);


        linearLayoutManager_2=new LinearLayoutManager(FindFriendActivity.this);
        recyclerView_select=findViewById(R.id.recycler_view_select);
        recyclerView_select.setLayoutManager(linearLayoutManager_2);

        applicationAdapter=new ApplicationAdapter(applicationList);
        recyclerView_select.setAdapter(applicationAdapter);

        layoutManager=new LinearLayoutManager(FindFriendActivity.this);
        recyclerView.setLayoutManager(layoutManager);



        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过点击获得搜索内容，在云端进行搜索、下载
                searchFriend(editText.getText().toString().trim());
            }
        });
        //对申请信息进行初始化
        initApplication();

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

    @Override
    protected void onStop() {
        super.onStop();
        applicationAdapter.deleteListF();
        finish();
    }

    private void initApplication(){
        //对申请信息进行初始化
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Application> query = new BmobQuery<Application>();
        query.addWhereEqualTo("user_2", new BmobPointer(user));
        query.include("user_1");
        query.findObjects(new FindListener<Application>() {
            @Override
            public void done(List<Application> object, BmobException e) {
                if (e == null) {
                    if (object.size()==0){
                        recyclerView_select.setVisibility(View.GONE);
                        return;
                    }
                    Application application;
                    for (int i=0;i<object.size();i++){
                        application=object.get(i);
                        applicationList.add(application);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            applicationAdapter.notifyItemChanged(applicationList.size());
                        }
                    });
                    Log.d("Application","好友申请获得成功"+applicationList.size());
                } else {
                    Log.d("Application","好友申请获得失败："+e.getMessage());
                }
            }

        });
    }
    private void searchFriend(final String string){
        if (TextUtils.isEmpty(string)){
            //提示搜索内容不能为空
            Toast.makeText(FindFriendActivity.this,"搜索内容不能为空",Toast.LENGTH_SHORT).show();
        }else {
            //搜索、下载信息
            //initFind();
            //setMyfriend(false);
            //对搜索进行初始化

            new Thread(new Runnable() {
                @Override
                public void run() {
                    friendList.clear();
                    BmobQuery<User> eq1 = new BmobQuery<User>();
                    eq1.addWhereEqualTo("name", string);
                    BmobQuery<User> eq2 = new BmobQuery<User>();
                    eq2.addWhereEqualTo("username", string);
                    List<BmobQuery<User>> queries = new ArrayList<BmobQuery<User>>();
                    queries.add(eq1);
                    queries.add(eq2);
                    BmobQuery<User> mainQuery = new BmobQuery<User>();
                    mainQuery.or(queries);
                    mainQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> objects, BmobException e) {
                            if(e==null){
                                MyFriend friend;
                                User user;
                                if (objects.size()==0){
                                    Toast.makeText(FindFriendActivity.this,"没有搜索到符合的人",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                for (int i=0;i<objects.size();i++){
                                    user=objects.get(i);
                                    friend=new MyFriend();
                                    friend.setUser_2(user);
                                    friend.setMyfriend(false);
                                    friendList.add(friend);
                                }
                                Log.d("search","搜索成功，找到"+friendList.size());

                                //search 结束
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            sleep(1000);
                                        } catch (InterruptedException e1) {
                                            e1.printStackTrace();
                                        }
                                        adapter=new MyFriendsAdapter(friendList);
                                        recyclerView.setAdapter(adapter);
                                        //adapter.notifyItemChanged(friendList.size());
                                        //recyclerView.scrollToPosition(0);
                                    }
                                });

                            }else{
                                Log.d("search","搜索失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                }

            }).start();
        }
    }
}
