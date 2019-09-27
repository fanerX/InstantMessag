package my_adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.instantmessag.ChatMessageActivity;
import com.example.instantmessag.ImageList;
import com.example.instantmessag.R;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import my_class.SessionMessage;
import my_class.User;

public class SessionMessageAdapter extends RecyclerView.Adapter<SessionMessageAdapter.ViewHolder> {
    private List<SessionMessage> messageList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        CircleImageView imageView;
        TextView name;
        //TextView message;
        TextView unreadCount;
        TextView hint_delete;
        public ViewHolder(View view){
            super(view);
            this.view=view;
            imageView=view.findViewById(R.id.image);
            name=view.findViewById(R.id.text_name);
            //message=view.findViewById(R.id.text_message);
            unreadCount=view.findViewById(R.id.unread_number);
            hint_delete=view.findViewById(R.id.hint_delete);
        }
    }


    public SessionMessageAdapter(List<SessionMessage> list){
        messageList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_session_message,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int position
                //Toast.makeText(v.getContext(),"跳转到聊天活动",Toast.LENGTH_SHORT).show();
                int position=holder.getAdapterPosition();
                //
                holder.unreadCount.setVisibility(View.INVISIBLE);
                User user_2=messageList.get(position).getUser_1();
                //boolean isFriend=true;
                //Toast.makeText(v.getContext(),"跳转到聊天活动或好友申请活动",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(v.getContext(), ChatMessageActivity.class);
                intent.putExtra("user_2",user_2);
                //intent.putExtra("isFriend",isFriend);
                v.getContext().startActivity(intent);
//                Intent intent =new Intent(v.getContext(), ChatMessageActivity.class);
//                intent.putExtra("friend",i);
//                v.getContext().startActivity(intent);
            }
        });

        holder.hint_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();

                //网络删除，双方同步删除
                delete(messageList.get(position));
                messageList.remove(position);



                notifyItemRemoved(position);
                Log.d("remove","adapter"+messageList.size());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        SessionMessage message=messageList.get(i);
        viewHolder.imageView.setImageResource(ImageList.image_id[message.getImage()]);
        viewHolder.name.setText(message.getUser_1().getName());
        //viewHolder.message.setText(message.getContent());
        if (message.isHaveUnread()){
            viewHolder.unreadCount.setVisibility(View.VISIBLE);
        } else {
            viewHolder.unreadCount.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private void delete(final SessionMessage message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                {
                    BmobQuery<SessionMessage> eq1 = new BmobQuery<SessionMessage>();
                    eq1.addWhereEqualTo("user_1", message.getUser_1());
                    BmobQuery<SessionMessage> eq2 = new BmobQuery<SessionMessage>();
                    eq2.addWhereEqualTo("user_2", message.getUser_2());
                    List<BmobQuery<SessionMessage>> queries = new ArrayList<BmobQuery<SessionMessage>>();
                    queries.add(eq1);
                    queries.add(eq2);
                    BmobQuery<SessionMessage> mainQuery = new BmobQuery<SessionMessage>();
                    mainQuery.and(queries);
                    mainQuery.findObjects(new FindListener<SessionMessage>() {
                        @Override
                        public void done(List<SessionMessage> object, BmobException e) {
                            if(e==null){
                                for (int i=0;i<object.size();i++){
                                    object.get(i).delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                //Snackbar.make(mBtnDelete, "删除成功", Snackbar.LENGTH_LONG).show();
                                            } else {
                                                Log.e("BMOB", e.toString());
                                                //Snackbar.make(mBtnDelete, e.getMessage(), Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                                //toast("查询年龄6-29岁之间，姓名以'y'或者'e'结尾的人个数："+object.size());
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }
                {
                    BmobQuery<SessionMessage> eq1 = new BmobQuery<SessionMessage>();
                    eq1.addWhereEqualTo("user_1", message.getUser_2());
                    BmobQuery<SessionMessage> eq2 = new BmobQuery<SessionMessage>();
                    eq2.addWhereEqualTo("user_2", message.getUser_1());
                    List<BmobQuery<SessionMessage>> queries = new ArrayList<BmobQuery<SessionMessage>>();
                    queries.add(eq1);
                    queries.add(eq2);
                    BmobQuery<SessionMessage> mainQuery = new BmobQuery<SessionMessage>();
                    mainQuery.and(queries);
                    mainQuery.findObjects(new FindListener<SessionMessage>() {
                        @Override
                        public void done(List<SessionMessage> object, BmobException e) {
                            if(e==null){
                                for (int i=0;i<object.size();i++){
                                    object.get(i).delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                //Snackbar.make(mBtnDelete, "删除成功", Snackbar.LENGTH_LONG).show();
                                            } else {
                                                Log.e("BMOB", e.toString());
                                                //Snackbar.make(mBtnDelete, e.getMessage(), Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                                //toast("查询年龄6-29岁之间，姓名以'y'或者'e'结尾的人个数："+object.size());
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }

            }
        }).start();
    }
}
