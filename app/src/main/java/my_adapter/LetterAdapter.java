package my_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.instantmessag.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import my_class.Letter;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.ViewHolder> {
    private List<Letter> letterList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        CircleImageView imageView;
        TextView name;
        TextView time;
        TextView content;

        public ViewHolder(View view){
            super(view);
            this.view=view;
            imageView=view.findViewById(R.id.image);
            name=view.findViewById(R.id.text_name);
            time=view.findViewById(R.id.text_time);
            content=view.findViewById(R.id.text_content);
        }

    }


    public LetterAdapter(List<Letter> list){
        letterList =list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_letter,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"这是一个留言",Toast.LENGTH_SHORT).show();
            }
        });
        //

        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        Letter letter= letterList.get(i);
        viewHolder.imageView.setImageResource(letter.getImage());
        viewHolder.name.setText(letter.getName());
        viewHolder.time.setText(letter.getTime());
        viewHolder.content.setText(letter.getContent());
    }

    @Override
    public int getItemCount() {
        return letterList.size();
    }
}

