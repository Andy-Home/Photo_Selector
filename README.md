#PhotoSelector

## 功能 ##
 - 单击照片选择器‘+’号，添加照片
 - 单击照片替换照片 
 - 长按照片可以删除照片
 - 上传照片


## 使用方法 ##

 **Maven**
``` maven
 <dependency>
  <groupId>com.andy.photoselector</groupId>
  <artifactId>photoselector</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

**Gradle**
``` gradle
compile 'com.andy.photoselector:photoselector:1.0.1'
```

**Ivy**
```Ivy
<dependency org='com.andy.photoselector' name='photoselector' rev='1.0.1'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
```
 
 
 
## 使用案例 ##
**java文件**：
``` java
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
                    photoSelector.uploadPic("http://10.0.2.2:8080/MyProject/photoSelector/savePic");
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
```
**xml文件** 
``` xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.andy.test1.MainActivity">

    <com.andy.component.PhotoSelectorGridView
        android:id="@+id/photo_selector"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:numColumns="4" />

    <Button
        android:id="@+id/commit"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:text="提交"
        android:textSize="16dp" />
</RelativeLayout>
```
## 类方法 ##
- PhotoSelectorGridView ：继承GridView，可以像使用GridView一样使用它
 
- PhotoSelectorAdapter ：PhotoSelectorGridView 数据源
    *  void addData(Intent data) ：在回调方法中使用，当用户选择照片返回照片选择器页面后，在回调方法中使用该方法用来添加用户选择的照片
    * PhotoSelectorAdapter(Context, List<Bitmap>, PhotoSelector) ：数据源初始化

- PhotoSelector ：主要类
    * void setActivity(Activity) : 设置使用照片选择器的页面的Activity
    * Activity getActivity() : 获取设置的Activity
    * void setMaxnum(int) : 设置用户显示的最大图片数
    * int getMaxnum() : 获取用户显示的最大图片数
    * String uploadPicAndTxt(String url, Map<String, String> map) ：上传图片与文本数据
    * String uploadPic(String url) ：上传图片
