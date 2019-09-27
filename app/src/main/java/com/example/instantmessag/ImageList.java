package com.example.instantmessag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import java.util.ArrayList;
import java.util.List;

import collector.BaseActivity;
import my_adapter.ChatMessageAdapter;
import my_adapter.ImageAdapter;
import my_class.Image;

public class ImageList extends BaseActivity {
    private List<Image> imageList=new ArrayList<>();
    private RecyclerView recyclerView;

    public static int[] image_id=new int[]{
            R.drawable.avatar,
            R.drawable.image_1,R.drawable.image_2,R.drawable.image_3,R.drawable.image_4,R.drawable.image_5,
            R.drawable.image_6,R.drawable.image_7,R.drawable.image_8,R.drawable.image_9,R.drawable.image_10,
            R.drawable.image_11,R.drawable.image_12,R.drawable.image_13,R.drawable.image_14,R.drawable.image_15,
            R.drawable.image_16,R.drawable.image_17,R.drawable.image_18,R.drawable.image_19,R.drawable.image_20,
            R.drawable.image_21,R.drawable.image_22,R.drawable.image_23,R.drawable.image_24,R.drawable.image_25,
            R.drawable.image_26,R.drawable.image_27,R.drawable.image_28,R.drawable.image_29,R.drawable.image_30,
            R.drawable.image_31,R.drawable.image_32,R.drawable.image_33,R.drawable.image_34,R.drawable.image_35,
            R.drawable.image_36,R.drawable.image_37,R.drawable.image_38,R.drawable.image_39,R.drawable.image_40,
            R.drawable.image_41,R.drawable.image_42,R.drawable.image_43,R.drawable.image_44,R.drawable.image_45,
            R.drawable.image_46,R.drawable.image_47,R.drawable.image_48,R.drawable.image_49,R.drawable.image_50,
            R.drawable.image_51,R.drawable.image_52,R.drawable.image_53,R.drawable.image_54,R.drawable.image_55,
            R.drawable.image_56,R.drawable.image_57,R.drawable.image_58,R.drawable.image_59,R.drawable.image_60,
            R.drawable.image_61,R.drawable.image_62,R.drawable.image_63,R.drawable.image_64,R.drawable.image_65,
            R.drawable.image_66,R.drawable.image_67,R.drawable.image_68,R.drawable.image_69,R.drawable.image_70,
            R.drawable.image_71,R.drawable.image_72,R.drawable.image_73,R.drawable.image_74,R.drawable.image_75,
            R.drawable.image_76,R.drawable.image_77,R.drawable.image_78,R.drawable.image_79,R.drawable.image_80,
            R.drawable.image_81,R.drawable.image_82,R.drawable.image_83,R.drawable.image_84,R.drawable.image_85,
            R.drawable.image_86,R.drawable.image_87,R.drawable.image_88,R.drawable.image_89,R.drawable.image_90,
            R.drawable.image_91,R.drawable.image_92,R.drawable.image_93,R.drawable.image_94,R.drawable.image_95,
            R.drawable.image_96,R.drawable.image_97,R.drawable.image_98,R.drawable.image_99,R.drawable.image_100,
            R.drawable.image_101,R.drawable.image_102,R.drawable.image_103,R.drawable.image_104,R.drawable.image_105,
            R.drawable.image_106,R.drawable.image_107,R.drawable.image_108,R.drawable.image_109,R.drawable.image_110,
            R.drawable.image_111,R.drawable.image_112,R.drawable.image_113,R.drawable.image_114,R.drawable.image_115,
            R.drawable.image_116,R.drawable.image_117,R.drawable.image_118,R.drawable.image_119,R.drawable.image_120,
            R.drawable.image_121,R.drawable.image_122,R.drawable.image_123,R.drawable.image_124,R.drawable.image_125,
            R.drawable.image_126,R.drawable.image_127,R.drawable.image_128,R.drawable.image_129,R.drawable.image_130,
            R.drawable.image_131
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        initImages();
        recyclerView=findViewById(R.id.recycler_view_image);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ImageAdapter adapter=new ImageAdapter(imageList);
        recyclerView.setAdapter(adapter);


    }

    private void initImages(){
        Image image;
        for (int i:image_id){
            image=new Image(i);
            imageList.add(image);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent=new Intent(ImageList.this,EditInformation.class);
        startActivity(intent);
        finish();
    }
}
