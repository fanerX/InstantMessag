package my_adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instantmessag.ImageList;
import com.example.instantmessag.InformationDisplay;
import com.example.instantmessag.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import my_class.MyFriend;
import my_class.User;


public class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.ViewHolder> {
    private List<MyFriend> friendsList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        CircleImageView imageView;
        TextView name;
        TextView motto;
        public ViewHolder(View view){
            super(view);
            this.view=view;
            imageView=view.findViewById(R.id.image);
            name=view.findViewById(R.id.text_name);
            motto =view.findViewById(R.id.text_motto);
        }

    }


    public MyFriendsAdapter(List<MyFriend> list){
        friendsList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_friend,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                User user_2=friendsList.get(position).getUser_2();
                boolean isFriend=friendsList.get(position).isMyfriend();
                //Toast.makeText(v.getContext(),"跳转到聊天活动或好友申请活动",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(v.getContext(), InformationDisplay.class);
                intent.putExtra("user_2",user_2);
                intent.putExtra("isFriend",isFriend);
                v.getContext().startActivity(intent);
            }
        });

        //
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        MyFriend friend=friendsList.get(i);
        viewHolder.imageView.setImageResource(ImageList.image_id[friend.getUser_2().getImage()]);
        viewHolder.name.setText(friend.getUser_2().getName());
        viewHolder.motto.setText(friend.getUser_2().getMotto());
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}

