package com.example.instantmessag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import collector.BaseActivity;
import my_class.User;

public class LoginActiviyt extends BaseActivity {
    private EditText edit_id,edit_password;
    private TextView register,forgetPassword;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activiyt);
        edit_id=findViewById(R.id.edit_id);
        edit_password=findViewById(R.id.edit_password);
        register=findViewById(R.id.register);
        forgetPassword=findViewById(R.id.forget_password);
        login_button=findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                login(edit_id.getText().toString().trim(),edit_password.getText().toString().trim());
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent intent=new Intent(LoginActiviyt.this,RegisteredActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }



    private void setInformation(){
        User user = BmobUser.getCurrentUser(User.class);
        SharedPreferences.Editor editor_1=getSharedPreferences("Login",MODE_PRIVATE).edit();
        editor_1.putString("id",edit_id.getText().toString().trim());
        editor_1.putString("password",edit_password.getText().toString().trim());
        editor_1.putBoolean("isLogin",true);
        editor_1.apply();





        {
            //User user = BmobUser.getCurrentUser(User.class);
            int my_image_int=user.getImage();
            String nickname_s=user.getName();
            String motto_s=user.getMotto();
            int age_s=user.getAge();
            String sex_s=user.getSex();
            String profession_s=user.getProfession();
            String hometown_s=user.getHometown();
            String mailbox_s=user.getMailbox();
            String personalProfile_s=user.getPersonalProfile();
            SharedPreferences.Editor editor=getSharedPreferences(user.getUsername()+"",MODE_PRIVATE).edit();
            editor.putInt("my_image",my_image_int);
            editor.putString("name",nickname_s);
            editor.putString("motto",motto_s);
            editor.putString("sex",sex_s);
            editor.putInt("age",age_s);
            editor.putString("profession",profession_s);
            editor.putString("hometown",hometown_s);
            editor.putString("mailbox",mailbox_s);
            editor.putString("personalProfile",personalProfile_s);
            editor.apply();
        }



        //User user_r = BmobUser.getCurrentUser(User.class);
        //user.setAge(20);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //Snackbar.make(view, "更新用户信息成功：" + user.getAge(), Snackbar.LENGTH_LONG).show();
                    Log.e("error", "更新用户信息成功");
                } else {
                    //Snackbar.make(view, "更新用户信息失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.e("error", e.getMessage());
                }
            }
        });

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
//                    if (BmobUser.isLogin()) {
//                        //User user = BmobUser.getCurrentUser(User.class);
//                        //Snackbar.make(view, "已经登录：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
//                        Toast.makeText(LoginActiviyt.this,"警告！该账户已登陆",Toast.LENGTH_SHORT).show();
//                    } else {
//                        //Snackbar.make(view, "尚未登录", Snackbar.LENGTH_LONG).show();
//
//                    }
                    Log.d("LoginActiviyt","登录成功");
                    setInformation();
                    Intent intent=new Intent(LoginActiviyt.this,MainActivity.class);
                    startActivity(intent);

                } else {
                    // Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.d("LoginActiviyt","登录失败"+ e.getMessage());
                    Toast.makeText(LoginActiviyt.this,"账户或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}
