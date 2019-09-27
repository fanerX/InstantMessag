package com.example.instantmessag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import collector.BaseActivity;
import my_class.Application;
import my_class.MyDataGet;
import my_class.User;
import my_fragment.Fragment_1;
import my_fragment.Fragment_2;
import my_fragment.Fragment_3;

import static java.lang.Thread.sleep;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ImageView button_1,button_2,button_3;//button_4;

    private boolean isReceive=true;




    //private static boolean is_open=false;

    //private ViewPager viewPager;
    //private List<Fragment_1> fragmentList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_open_white);
        }

//
        {
            MyDataGet myDataGet=new MyDataGet();
            myDataGet.getAMotto();
        }


        //为底部菜单栏添加点击事件
        button_1=findViewById(R.id.button_1);
        button_2=findViewById(R.id.button_2);
        button_3=findViewById(R.id.button_3);
        //button_4=findViewById(R.id.button_4);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        //button_4.setOnClickListener(this);
        replaceFragment(new Fragment_1());


    }
    //为底部菜单栏添加点击事件
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_1:
                replaceFragment(new Fragment_1());
                toolbar.setTitle("消息");
                break;
            case R.id.button_2:
                replaceFragment(new Fragment_2());
                toolbar.setTitle("联系人");
                break;
            case R.id.button_3:
                replaceFragment(new Fragment_3());
                toolbar.setTitle("留言板");
                break;

//            case R.id.button_4:
//                replaceFragment(new Fragment_4());
//                toolbar.setTitle("娱乐");
//                break;
                default:
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_layout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_friend:
                Intent intent=new Intent(MainActivity.this,FindFriendActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

                default:
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        //initApplication();
        isReceive=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isReceive=false;
    }

//    private void initApplication(){
//        //对申请信息进行初始化
//
//        User user = BmobUser.getCurrentUser(User.class);
//        final BmobQuery<Application> query = new BmobQuery<Application>();
//        query.addWhereEqualTo("user_2", new BmobPointer(user));
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (isReceive){
//                    query.findObjects(new FindListener<Application>() {
//                        @Override
//                        public void done(List<Application> object, BmobException e) {
//                            if (e == null) {
//                                if (object.size()==0){
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            MenuItem item=toolbar.findViewById(R.id.add_friend);
//                                            item.setIcon(R.drawable.add_white);
//                                        }
//                                    });
//                                }else {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            MenuItem item=toolbar.findViewById(R.id.add_friend);
//                                            item.setIcon(R.drawable.add_red);
//                                        }
//                                    });
//                                }
//
//                                Log.d("Application","好友申请获得成功");
//                            } else {
//                                Log.d("Application","好友申请获得失败："+e.getMessage());
//                            }
//                        }
//
//                    });
//                }
//                try {
//                    sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
}
