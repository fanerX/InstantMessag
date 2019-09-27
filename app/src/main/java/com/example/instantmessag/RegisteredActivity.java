package com.example.instantmessag;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import collector.BaseActivity;
import my_class.User;

public class RegisteredActivity extends BaseActivity {
    private EditText edit_id,edit_password;
    private Button registered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        edit_id=findViewById(R.id.edit_id);
        edit_password=findViewById(R.id.edit_password);
        registered =findViewById(R.id.registered_button);
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                User user = new User();
                user.setUsername(edit_id.getText().toString().trim());
                user.setPassword(edit_password.getText().toString().trim());
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            //Snackbar.make(getc, "注册成功", Snackbar.LENGTH_LONG).show();
                            Log.d("Registered","注册成功");
                            Intent intent=new Intent(RegisteredActivity.this,LoginActiviyt.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisteredActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                            //Snackbar.make(view, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent=new Intent(RegisteredActivity.this,LoginActiviyt.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(RegisteredActivity.this,LoginActiviyt.class);
        startActivity(intent);
        finish();
    }
}
