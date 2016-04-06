package com.andy.test1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.andy.type.PhotoSelector;
import com.andy.adapter.PhotoSelectorAdapter;
import com.andy.component.PhotoSelectorGridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private PhotoSelectorGridView photoSelectorGridView;
    private PhotoSelectorAdapter photoSelectorAdapter;
    private PhotoSelector photoSelector = new PhotoSelector();
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

}
