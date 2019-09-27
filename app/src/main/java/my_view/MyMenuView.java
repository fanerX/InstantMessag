package my_view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instantmessag.EditInformation;
import com.example.instantmessag.ImageList;
import com.example.instantmessag.LoginActiviyt;
import com.example.instantmessag.R;

import cn.bmob.v3.BmobUser;
import collector.ActivityCollector;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import my_class.User;

import static android.content.Context.MODE_PRIVATE;

public class MyMenuView extends LinearLayout {
    private Button exit_button,edit_information;
    private CircleImageView image;
    private TextView nickname,motto,age,profession,hometown,mailbox,personalProfile,male;
    private ImageView avatar_background;



    public MyMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.my_information_menu_layout,this);
                exit_button=findViewById(R.id.exit_button);
        edit_information=findViewById(R.id.edit_information);
                //findViewById(R.id.edit_information);
        image=findViewById(R.id.avatar_image);
        nickname=findViewById(R.id.text_name);
        motto=findViewById(R.id.text_motto);
        age=findViewById(R.id.age_edit);
        male=findViewById(R.id.sex_edit);
        profession=findViewById(R.id.profession_edit);
        hometown=findViewById(R.id.hometown_edit);
        mailbox=findViewById(R.id.mailbox_edit);
        personalProfile=findViewById(R.id.personal_profile_edit);
        avatar_background=findViewById(R.id.avatar_background);

        initImage(context);


        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //BmobUser.logOut();//账户退出
                Log.d("exit","退出登录");
                SharedPreferences.Editor editor=v.getContext().getSharedPreferences("Login",MODE_PRIVATE).edit();
                editor.putBoolean("isLogin",false);
                editor.apply();
                ActivityCollector.finishAll();
                Intent intent =new Intent(v.getContext(), LoginActiviyt.class);
                v.getContext().startActivity(intent);
            }
        });
        edit_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent intent=new Intent(v.getContext(), EditInformation.class);
                v.getContext().startActivity(intent);
            }
        });
    }
    private void initImage(Context context){
        User user = BmobUser.getCurrentUser(User.class);
        SharedPreferences preferences=context.getSharedPreferences(user.getUsername()+"",MODE_PRIVATE);
        boolean is_login=preferences.getBoolean("isLogin",false);
        int image_int=preferences.getInt("my_image",0);
        //my_image_int=image_int;
        //int image;//头像
        String name_=preferences.getString("name","昵称");//昵称
        String motto_=preferences.getString("motto","座右铭");//座右铭
        String sex_=preferences.getString("sex","男");//
        int age_=preferences.getInt("age",18);//年龄
        String profession_=preferences.getString("profession","职业");//职业
        String hometown_=preferences.getString("hometown","家乡");//家乡
        String mailbox_=preferences.getString("mailbox","邮箱");//邮箱
        String personalProfile_=preferences.getString("personalProfile","个人说明");//个人说明


        //image.setImageResource(ImageList.image_id[image_int]);
        //头像

        Glide.with(getContext())
                .load(ImageList.image_id[image_int])
                .into(image);

        Glide.with(getContext())
                .load(ImageList.image_id[image_int])
                .bitmapTransform(new BlurTransformation(getContext(),20,2))
                .into(avatar_background);


        nickname.setText(name_);
        motto.setText(motto_);
        age.setText(age_+"");
        profession.setText(profession_);
        hometown.setText(hometown_);
        mailbox.setText(mailbox_);
        personalProfile.setText(personalProfile_);
        male.setText(sex_);
        //EditText nickname,motton,age,profession,hometown,mailbox,personalProfile;
        //RadioButton male;

    }


}
