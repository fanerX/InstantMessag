package com.example.instantmessag;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "d33b7d7b89f0bb4c2a3cd15fa7ab60e4");
    }

}
