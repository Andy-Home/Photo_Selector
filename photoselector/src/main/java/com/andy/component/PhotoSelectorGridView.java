package com.andy.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class PhotoSelectorGridView extends GridView {
    public PhotoSelectorGridView(Context context) {
        super(context);
    }

    public PhotoSelectorGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoSelectorGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
