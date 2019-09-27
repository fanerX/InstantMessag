package my_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.instantmessag.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import my_adapter.MyFriendsAdapter;
import my_class.MyFriend;
import my_class.User;


public class Fragment_2 extends Fragment {
    private List<MyFriend> friends=new ArrayList<>();
    private RecyclerView recyclerView;
    private MyFriendsAdapter adapter;
    private User user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment_2, container, false);
        user=BmobUser.getCurrentUser(User.class);

        recyclerView=view.findViewById(R.id.recycler_view_friends);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MyFriendsAdapter(friends);
        recyclerView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        initMyFriends();
    }

    private void initMyFriends(){
        friends.clear();
        BmobQuery<MyFriend> query = new BmobQuery<MyFriend>();
        query.addWhereEqualTo("user_1", new BmobPointer(user));
        //query.order("-updatedAt");
        //包含作者信息
        query.include("user_2");
        query.findObjects(new FindListener<MyFriend>() {
            @Override
            public void done(List<MyFriend> object, BmobException e) {
                if (e == null) {
                    MyFriend friend;
                    for (int i=0;i<object.size();i++){
                        friend=object.get(i);
                        friends.add(friend);

                    }
                    adapter.notifyItemChanged(friends.size());
                    atToFriend(1);
                    Log.d("MyFriend","查询成功"+friends.size());
                } else {
                    Log.d("MyFriend","查询失败："+e.getMessage());
                }
            }

        });
        //adapter.notifyItemChanged(friends.size());
        //Collections.sort(friends);
    }

    private void atToFriend(final int at){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(at-1);
            }
        });
    }
}
