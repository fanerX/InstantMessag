package my_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.instantmessag.ImageList;
import com.example.instantmessag.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import my_adapter.LetterAdapter;
import my_class.Letter;
import my_class.MyDataGet;
import my_overrride.OnClickEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_3 extends Fragment {

    private List<Letter> letterList=new ArrayList<>();
    private RecyclerView recyclerView;

    private ImageView refresh,control;
    private TextView content_daily;
    private MyDataGet myDataGet=new MyDataGet();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment_3, container, false);
        //recyclerView初始化部分
        initLetter();
        //初始化内容
        myDataGet.getAMotto();


        recyclerView=view.findViewById(R.id.recycler_view_letter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LetterAdapter letterAdapter=new LetterAdapter(letterList);
        recyclerView.setAdapter(letterAdapter);
        //经典一语，刷新、控制
        refresh=view.findViewById(R.id.refresh_image);
        refresh.setVisibility(View.INVISIBLE);
        control=view.findViewById(R.id.control_image);
        content_daily=view.findViewById(R.id.text_daily_sentence);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content_daily.getVisibility()==View.GONE){
                    content_daily.setVisibility(View.VISIBLE);
                    refresh.setVisibility(View.VISIBLE);
                    control.setImageResource(R.drawable.pack_up);
                }else{
                    content_daily.setVisibility(View.GONE);
                    refresh.setVisibility(View.INVISIBLE);
                    control.setImageResource(R.drawable.drop_down);
                }
            }
        });
        refresh.setOnClickListener(new OnClickEvent(900) {
            @Override
            public void singleClick(View v) {
                showDataText(myDataGet.getAMotto());
            }
        });
        showDataText(myDataGet.getAMotto());
        return view;
    }
    private void initLetter(){
        Letter letter;
        for (int i=0;i<23;i++){
            letter=new Letter();
            letter.setImage(ImageList.image_id[i]);
            letterList.add(letter);
        }
    }

    private void showDataText(final String string_){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                content_daily.setText(string_);
            }
        });
    }
}
