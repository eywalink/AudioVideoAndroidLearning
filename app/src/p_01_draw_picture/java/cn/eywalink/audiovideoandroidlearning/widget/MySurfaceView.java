package cn.eywalink.audiovideoandroidlearning.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by lixin on 2019/2/22.
 */
public class MySurfaceView extends SurfaceView

        implements SurfaceHolder.Callback
{


    private Bitmap bitmap;

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void showImage(@DrawableRes int resId){
        bitmap = BitmapFactory.decodeResource(getResources(), resId);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas(null);
            synchronized (holder) {
                onDraw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
