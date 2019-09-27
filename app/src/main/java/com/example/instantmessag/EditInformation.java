package com.example.instantmessag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import collector.ActivityCollector;
import collector.BaseActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import my_class.Image;
import my_class.Information;
import my_class.User;

public class EditInformation extends BaseActivity {
    private CircleImageView image;
    private Button imageButton,saveButton,cancelButton;
    private EditText nickname,motton,age,profession,hometown,mailbox,personalProfile;
    private RadioButton male,female;
    int my_image_int=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        image=findViewById(R.id.image);
        imageButton=findViewById(R.id.image_button);
        saveButton=findViewById(R.id.save_button);
        cancelButton=findViewById(R.id.cancel_button);
        nickname=findViewById(R.id.nickname_edit);
        motton=findViewById(R.id.motto_edit);
        age=findViewById(R.id.age_edit);
        profession=findViewById(R.id.profession_edit);
        hometown=findViewById(R.id.hometown_edit);
        mailbox=findViewById(R.id.mailbox_edit);
        personalProfile=findViewById(R.id.personal_profile_edit);
        male=findViewById(R.id.male_radio_button);
        female=findViewById(R.id.female_radio_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开图片列表
                Intent intent=new Intent(EditInformation.this,ImageList.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消操作，返回主活动
                finish();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定保存数据

                String nickname_s=nickname.getText().toString().trim();
                String motto_s=motton.getText().toString().trim();
                //
                String age_s=age.getText().toString().trim();

                String profession_s=profession.getText().toString().trim();
                String hometown_s=hometown.getText().toString().trim();
                String mailbox_s=mailbox.getText().toString().trim();
                String personalProfile_s=personalProfile.getText().toString().trim();
                String sex_s;
                if (male.isChecked()){
                    sex_s="男";
                }else {
                    sex_s="女";
                }
                if (TextUtils.isEmpty(nickname_s)||TextUtils.isEmpty(motto_s)||TextUtils.isEmpty(age_s)||TextUtils.isEmpty(profession_s)||
                        TextUtils.isEmpty(hometown_s)||TextUtils.isEmpty(mailbox_s)||TextUtils.isEmpty(personalProfile_s)||TextUtils.isEmpty(sex_s)){
                    //提示输入不能为空
                    Toast.makeText(EditInformation.this,"输入不能为空",Toast.LENGTH_SHORT).show();
                }else {

                    User user = BmobUser.getCurrentUser(User.class);

                    //个人数据加载到本地
                    SharedPreferences.Editor editor=getSharedPreferences(user.getUsername()+"",MODE_PRIVATE).edit();
                    editor.putString("name",nickname_s);
                    editor.putString("motto",motto_s);
                    editor.putString("sex",sex_s);
                    editor.putInt("age",Integer.valueOf(age_s));
                    editor.putString("profession",profession_s);
                    editor.putString("hometown",hometown_s);
                    editor.putString("mailbox",mailbox_s);
                    editor.putString("personalProfile",personalProfile_s);
                    editor.apply();

                    //数据更新到网络
                    user.setImage(my_image_int);
                    user.setName(nickname_s);
                    user.setMotto(motto_s);
                    user.setAge(Integer.valueOf(age_s));
                    user.setSex(sex_s);
                    user.setProfession(profession_s);
                    user.setHometown(hometown_s);
                    user.setMailbox(mailbox_s);
                    user.setPersonalProfile(personalProfile_s);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //Snackbar.make(view, "更新用户信息成功：" + user.getAge(), Snackbar.LENGTH_LONG).show();
                                Log.e("error", "更新用户信息成功");
                            } else {
                                //Snackbar.make(view, "更新用户信息失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                                Log.e("error", e.getMessage());
                                Toast.makeText(EditInformation.this,"更新用户信息失败(未保存到网络)",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    //保存成功，关闭本活动
                    Toast.makeText(EditInformation.this,"保存成功",Toast.LENGTH_SHORT).show();
                    ActivityCollector.finishAll();
                    Intent intent=new Intent(EditInformation.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        //初始化输入框数据（以前的数据）
        initImage();
    }

    private void initImage(){
        User user = BmobUser.getCurrentUser(User.class);
        SharedPreferences preferences=getSharedPreferences(user.getUsername()+"",MODE_PRIVATE);
        boolean is_login=preferences.getBoolean("isLogin",false);
        int image_int=preferences.getInt("my_image",0);
        my_image_int=image_int;
        //int image;//头像
        String name_=preferences.getString("name","昵称");//昵称
        String motto_=preferences.getString("motto","座右铭");//座右铭
        String sex_=preferences.getString("sex","男");//
        int age_=preferences.getInt("age",18);//年龄
        String profession_=preferences.getString("profession","职业");//职业
        String hometown_=preferences.getString("hometown","家乡");//家乡
        String mailbox_=preferences.getString("mailbox","邮箱");//邮箱
        String personalProfile_=preferences.getString("personalProfile","个人说明");//个人说明

        image.setImageResource(ImageList.image_id[image_int]);
        nickname.setText(name_);
        motton.setText(motto_);
        age.setText(age_+"");
        profession.setText(profession_);
        hometown.setText(hometown_);
        mailbox.setText(mailbox_);
        personalProfile.setText(personalProfile_);
        if (sex_.equals("男")){
            male.setChecked(true);
            female.setChecked(false);
        }else{
            female.setChecked(true);
            male.setChecked(false);
        }

    }

}
