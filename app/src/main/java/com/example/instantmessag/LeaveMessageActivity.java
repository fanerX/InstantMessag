package com.example.instantmessag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import collector.BaseActivity;
import my_class.Letter;

public class LeaveMessageActivity extends BaseActivity {
    private Letter letter;

    private Button cancel_button,leave_button;
    private EditText content_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_message);

        letter=new Letter();
        cancel_button=findViewById(R.id.cancel_button);
        leave_button=findViewById(R.id.leave_button);
        content_edit=findViewById(R.id.edit_leave_message);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出本活动
                finish();
            }
        });
        leave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //letter.setImage();
                String string=content_edit.getText().toString();
                if (TextUtils.isEmpty(string)){
                    //提示内容不能为空
                }else {
                    letter.setContent(string);
                    //提示留言成功
                    finish();
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
