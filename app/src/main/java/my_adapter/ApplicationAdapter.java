package my_adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.instantmessag.ImageList;
import com.example.instantmessag.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import my_class.Application;
import my_class.Image;
import my_class.MyFriend;
import my_class.User;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {
    private List<Application> applicationList;
    private List<BmobObject> deleteList=new ArrayList<>();


    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        Button agree_button,refuse_button;
        TextView name,content;
        View view;

        public ViewHolder(View view){
            super(view);
            this.view=view;
            imageView=view.findViewById(R.id.image);
            agree_button=view.findViewById(R.id.agree_button);
            refuse_button=view.findViewById(R.id.refuse_button);
            name=view.findViewById(R.id.text_name);
            content=view.findViewById(R.id.text_content);
            //linearLayout=view.findViewById(R.id.linearLayout);
        }

    }


    public ApplicationAdapter(List<Application> list){
        applicationList =list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.application_item_layout,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        //


        holder.agree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.agree_button.setText("已同意");
                holder.refuse_button.setVisibility(View.INVISIBLE);
                holder.agree_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //使button无效化
                    }
                });

                int position=holder.getAdapterPosition();
                Application application=applicationList.get(position);
                User user_2=new User(application.getUser_1().getObjectId());
                User user = BmobUser.getCurrentUser(User.class);
                MyFriend friend_1=new MyFriend();
                MyFriend friend_2=new MyFriend();

                friend_1.setUser_1(user);
                friend_1.setUser_2(user_2);
                friend_1.setMyfriend(true);
                friend_2.setUser_1(user_2);
                friend_2.setUser_2(user);
                friend_2.setMyfriend(true);
                if (user.getObjectId().equals(user_2.getObjectId())) {
                    friend_1.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                Log.d("friend","加好友成功1");
                            }else {
                                Log.d("friend","加好友失败1");
                            }
                        }
                    });
                }else {
                    friend_1.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                Log.d("friend","加好友成功1");
                            }else {
                                Log.d("friend","加好友失败1");
                            }
                        }
                    });
                    friend_2.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                Log.d("friend","加好友成功2");
                            }else {
                                Log.d("friend","加好友失败2");
                            }
                        }
                    });
                }
                deleteList.add(application);
            }
        });
        holder.refuse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.agree_button.setVisibility(View.INVISIBLE);
                holder.refuse_button.setText("已拒绝");
                holder.refuse_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //使button无效化
                    }
                });
                int position=holder.getAdapterPosition();
                Application application=applicationList.get(position);
                deleteList.add(application);
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        Application application= applicationList.get(i);
        viewHolder.imageView.setImageResource(ImageList.image_id[application.getUser_1().getImage()]);
        viewHolder.content.setText(application.getContent());
        viewHolder.name.setText(application.getUser_1().getName());
    }

    @Override
    public int getItemCount() {
        return applicationList.size();
    }

    public void deleteListF(){
        new BmobBatch().deleteBatch(deleteList).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            //Snackbar.make(mBtnDelete, "第" + i + "个数据批量删除成功：" + result.getCreatedAt() + "," + result.getObjectId() + "," + result.getUpdatedAt(), Snackbar.LENGTH_LONG).show();
                        } else {
                            //Snackbar.make(mBtnDelete, "第" + i + "个数据批量删除失败：" + ex.getMessage() + "," + ex.getErrorCode(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                } else {
                    //Snackbar.make(mBtnDelete, "失败：" + e.getMessage() + "," + e.getErrorCode(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}

