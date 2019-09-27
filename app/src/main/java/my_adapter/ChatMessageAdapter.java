package my_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.instantmessag.ImageList;
import com.example.instantmessag.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import my_class.ChatMessage;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
    private List<ChatMessage> chatMessageList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView_left,imageView_rigth;
        View view;
        TextView content;
        LinearLayout linearLayout;


        public ViewHolder(View view){
            super(view);
            this.view=view;
            imageView_left=view.findViewById(R.id.image_left);
            imageView_rigth=view.findViewById(R.id.image_right);
            this.content=view.findViewById(R.id.text_content);
            this.linearLayout=view.findViewById(R.id.text_background_linearLayout);
        }

    }


    public ChatMessageAdapter(List<ChatMessage> list){
        chatMessageList =list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_message_layout,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        //
        holder.imageView_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getVisibility()==View.VISIBLE){
                    //进入好友信息展示活动
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        ChatMessage chatMessage= chatMessageList.get(i);
        if (chatMessage.isIs_me()){
            viewHolder.imageView_rigth.setVisibility(View.VISIBLE);
            viewHolder.imageView_rigth.setImageResource(ImageList.image_id[chatMessage.getImage()]);
            viewHolder.imageView_left.setVisibility(View.INVISIBLE);
            //viewHolder.content.setBackgroundColor(R.drawable.shape_1);

        }else {
            viewHolder.imageView_left.setVisibility(View.VISIBLE);
            viewHolder.imageView_left.setImageResource(ImageList.image_id[chatMessage.getImage()]);
            viewHolder.imageView_rigth.setVisibility(View.INVISIBLE);
            //viewHolder.content.setBackgroundColor(R.drawable.shape_2);
        }
        viewHolder.content.setText(chatMessage.getContent());
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }
}

