package com.example.instantmessag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import my_class.User;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        RelativeLayout layoutsplash=(RelativeLayout)findViewById(R.id.activity_animation);//布局文件是相对布局
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(1500);

        layoutsplash.startAnimation(alphaAnimation);
        //设置动画监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {	//当动画结束后就跳转到其他活动中
                isLogin();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
    @Override
    protected void onPause() {//当动画结束后，就把该动画活动销毁
        super.onPause();
        finish();
    }
    private void isLogin(){
        SharedPreferences preferences=getSharedPreferences("Login",MODE_PRIVATE);
        boolean is_login=preferences.getBoolean("isLogin",false);

        //

        if(is_login){
            //
            String id=preferences.getString("id","123456");
            String password=preferences.getString("password","123456");
            login(id,password);
        }else {
            Intent intent=new Intent(AnimationActivity.this,LoginActiviyt.class);
            startActivity(intent);
        }
    }
    private void login(String id,String password){
        User user = new User();
        //此处替换为你的用户名
        user.setUsername(id);
        //此处替换为你的密码
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
                    User user = BmobUser.getCurrentUser(User.class);
                    //Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                    Log.d("LoginActiviyt","登录成功");


                    Intent intent=new Intent(AnimationActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    // Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.d("LoginActiviyt","登录失败"+ e.getMessage());
                    Toast.makeText(AnimationActivity.this,"无网络连接",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AnimationActivity.this,LoginActiviyt.class);
                    startActivity(intent);

                }
            }
        });
    }


}

