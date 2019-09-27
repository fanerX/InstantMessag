package my_adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.example.instantmessag.R;

import java.util.List;
public class MyPagerAdapter extends PagerAdapter {
    private Context mContext;
    private FragmentManager fragmentManager;
    private List<Fragment> mData;

    public MyPagerAdapter(FragmentManager fragmentManager ,Context mContext ,List<Fragment> list) {
        this.fragmentManager=fragmentManager;
        this.mContext=mContext;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
