package com.andy.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.photoselector.R;
import com.andy.util.Path;
import com.andy.util.Photo;
import com.andy.type.PhotoSelector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class PhotoSelectorAdapter extends BaseAdapter {
    private Context context;
    private List<Bitmap> bitmaps = new ArrayList<>();
    public static List<String> paths = new ArrayList<>();
    private PhotoSelector photoSelector; //能够选择的最大图片数
    private SharedPreferences sharedPreferences;

    public PhotoSelectorAdapter(Context context, List<Bitmap> bitmaps, PhotoSelector photoSelector) {
        this.context = context;
        bitmaps.add(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.upload));
        this.bitmaps = bitmaps;
        this.photoSelector = photoSelector;
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
        viewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if((bitmaps.size()-1) != position){
                    dialog(position);
                }
                return true;
            }
        });

        return convertView;
    }



    class ViewHolder {
        ImageView imageView;
    }

    /**
     * 添加照片
     *
     * @param data
     */
    public void addData(Intent data){
        //取出保存的位置信息
        int position = sharedPreferences.getInt("position", 0);

        Uri imgUri = data.getData();
        String path = Path.getPath(context, imgUri);

        //当将原有文件替换时，将paths中原有路径给替换掉
        //当新添文件时，添加新的路径
        if(position < paths.size()){
            paths.set(position, path);
        }else{
            paths.add(path);
        }
        Bitmap photo = Photo.decodeSampledBitmapFromResource(path, 500, 500);


        bitmaps.set(position, photo);
        if(position == getCount()-1 && getCount() < photoSelector.getMaxnum()){
            bitmaps.add(Photo.decodeSampledBitmapFromResource(photoSelector.getActivity().getResources(), R.drawable.upload, 100, 100));
        }
        notifyDataSetChanged();
    }

    /**
     * 删除照片
     * @param position
     */
    private void deleteData(int position){
        Iterator<Bitmap> iterator = bitmaps.iterator();
        int dest = -1;
        while(iterator.hasNext()){
            iterator.next();
            if((++dest) == position){
                iterator.remove();
            }
        }
        dest = -1;
        Iterator<String> iterator1 = paths.iterator();
        while(iterator1.hasNext()){
            iterator1.next();
            if((++dest) == position){
                iterator1.remove();
            }
        }
        notifyDataSetChanged();
    }


    /**
     * 长按图片弹出的对话框
     * @param position
     */
    private void dialog(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        TextView textView = new TextView(context);
        dialog.setTitle("删除选中照片？");
        dialog.setNeutralButton("取消", null);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(position);
            }
        });
        dialog.create().show();
    }
}
