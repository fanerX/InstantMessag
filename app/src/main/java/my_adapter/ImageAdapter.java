package my_adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instantmessag.EditInformation;
import com.example.instantmessag.R;
import java.util.List;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import my_class.Image;
import my_class.User;

import static android.content.Context.MODE_PRIVATE;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<Image> imageList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;

        public ViewHolder(View view){
            super(view);
            imageView=view.findViewById(R.id.image);
        }

    }


    public ImageAdapter(List<Image> list){
        imageList =list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_layout,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        //
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                User user = BmobUser.getCurrentUser(User.class);
                SharedPreferences.Editor editor=v.getContext().getSharedPreferences(user.getUsername()+"",MODE_PRIVATE).edit();
                editor.putInt("my_image",position);
                editor.apply();
                Intent intent=new Intent(v.getContext(), EditInformation.class);
                Log.d("my_image","my_image:"+position);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        Image image= imageList.get(i);
        viewHolder.imageView.setImageResource(image.getImage());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}
