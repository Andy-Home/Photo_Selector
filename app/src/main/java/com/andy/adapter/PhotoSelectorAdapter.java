package com.andy.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.andy.type.Path;
import com.andy.type.Photo;
import com.andy.type.PhotoSelector;
import com.andy.test1.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class PhotoSelectorAdapter extends BaseAdapter {
    private Context context;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private PhotoSelector photoSelector; //能够选择的最大图片数
    private SharedPreferences sharedPreferences;

    public PhotoSelectorAdapter(Context context, List<Bitmap> bitmaps, PhotoSelector photoSelector) {
        this.context = context;
        bitmaps.add(BitmapFactory.decodeResource(photoSelector.getActivity().getResources(), R.drawable.upload));
        this.bitmaps = bitmaps;
        this.photoSelector = photoSelector;
    }

    public void addData(Intent data){
        ContentResolver resolver = photoSelector.getActivity().getContentResolver();

        int position = sharedPreferences.getInt("position", 0);
        Uri imgUri = data.getData();
        Bitmap photo = null;
            //photo = MediaStore.Images.Media.getBitmap(resolver, imgUri);
            photo = Photo.decodeSampledBitmapFromResource(Path.getPath(context,imgUri), 500, 500);

        bitmaps.set(position, photo);
        if(position == getCount()-1 && getCount() < photoSelector.getMaxnum()){
            bitmaps.add(Photo.decodeSampledBitmapFromResource(photoSelector.getActivity().getResources(), R.drawable.upload, 100, 100));
            //bitmaps.add(BitmapFactory.decodeResource(photoSelector.getActivity().getResources(), R.drawable.upload));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public Object getItem(int position) {
        return bitmaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_photo_upload);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageBitmap(bitmaps.get(position));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                //使用SharedPreferences 保存位置信息
                sharedPreferences = photoSelector.getActivity().getSharedPreferences("photoSelector", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("position", position);
                editor.commit();

                photoSelector.getActivity().startActivityForResult(intent, PhotoSelector.PHOTOSELECT);
            }
        });
        return convertView;
    }


    class ViewHolder {
        ImageView imageView;
    }
}
