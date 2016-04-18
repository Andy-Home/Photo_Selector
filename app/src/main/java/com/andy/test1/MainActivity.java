package com.andy.test1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andy.type.PhotoSelector;
import com.andy.adapter.PhotoSelectorAdapter;
import com.andy.component.PhotoSelectorGridView;
import com.andy.util.Upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity implements View.OnClickListener {
    private PhotoSelectorGridView photoSelectorGridView;
    private PhotoSelectorAdapter photoSelectorAdapter;
    private PhotoSelector photoSelector = new PhotoSelector();
    private Button commit;
    private List<Bitmap> bitmaps = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setAdapter();
    }

    private void init() {
        photoSelectorGridView = (PhotoSelectorGridView) findViewById(R.id.photo_selector);
        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(this);
        photoSelector.setMaxnum(8);
        photoSelector.setActivity(this);
    }

    private void setAdapter() {
        photoSelectorAdapter = new PhotoSelectorAdapter(this, bitmaps, photoSelector);
        photoSelectorGridView.setAdapter(photoSelectorAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoSelector.PHOTOSELECT:
                    photoSelectorAdapter.addData(data);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                try {
                    photoSelector.uploadPic("http://172.20.120.58:8080/MyProject/photoSelector/savePic");
                    //photoSelector.uploadPic("http://10.0.2.2:8080/MyProject/photoSelector/savePic");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
