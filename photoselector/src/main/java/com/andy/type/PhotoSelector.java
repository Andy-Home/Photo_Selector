package com.andy.type;

import android.app.Activity;

import com.andy.util.Path;
import com.andy.util.UploadThread;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 为照片选择设置参数
 * <p/>
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
     *
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
     *
     * @param maxnum
     */
    public void setMaxnum(int maxnum) {
        Maxnum = maxnum;
    }

    /**
     * 上传图片和其他文本数据
     *
     * @param url 链接地址
     * @param map 请求参数
     * @return
     * @throws IOException
     */
    public String uploadPicAndTxt(String url, Map<String, String> map) throws IOException, ExecutionException, InterruptedException {
        UploadThread uploadThread = new UploadThread(activity.getApplicationContext(), map, Path.getAllPaths());
        String result = uploadThread.execute(url).get();
        return result;
    }

    /**
     * 上传图片
     *
     * @param url 链接地址
     * @return
     * @throws IOException
     */
    public String uploadPic(String url) throws IOException, ExecutionException, InterruptedException {
        UploadThread uploadThread = new UploadThread(activity.getApplicationContext(), null, Path.getAllPaths());
        String result = uploadThread.execute(url).get();
        return result;
    }

}
