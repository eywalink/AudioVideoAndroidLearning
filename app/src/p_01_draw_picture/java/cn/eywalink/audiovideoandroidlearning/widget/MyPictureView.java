package cn.eywalink.audiovideoandroidlearning.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.eywalink.audiovideoandroidlearning.R;

/**
 * Created by lixin on 2019/2/22.
 */
public class MyPictureView extends View {

    private Bitmap bitmap = null;

    public MyPictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyPictureView);

        int drawableId = ta.getResourceId(R.styleable.MyPictureView_android_src, 0);

        if (drawableId!=0) {
            bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        }
        ta.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap!=null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }
}
