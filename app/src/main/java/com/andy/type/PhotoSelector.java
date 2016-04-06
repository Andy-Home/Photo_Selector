package com.andy.type;

import android.app.Activity;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
public class PhotoSelector {
    public static final int PHOTOSELECT = 1;
    private int Maxnum = 8;     //默认图片的最大选择数为8；
    private Activity activity;  //用于在Adapter中调用startActivityForResult

    public Activity getActivity() {
        return activity;
    }

    /**
     * 必填项
     * @param activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getMaxnum() {
        return Maxnum;
    }

    /**
     * 设置最大图片数
     * @param maxnum
     */
    public void setMaxnum(int maxnum) {
        Maxnum = maxnum;
    }

}
