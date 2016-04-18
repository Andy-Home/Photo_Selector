package com.andy.util;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class UploadThread extends AsyncTask<String, Integer, String> {
    private Context context;
    private Map<String, String> map = null;
    private List<String> paths;

    public UploadThread(Context context, Map<String, String> map, List<String> paths) {
        this.context = context;
        this.map = map;
        this.paths = paths;
    }

    @Override
    protected String doInBackground(String... params) {
        if(map == null){
            try {
                Upload.uploadPic(params[0], paths);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                Upload.uploadPicAndTxt(params[0], map, paths);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Toast toast = Toast.makeText(context, "上传成功", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
