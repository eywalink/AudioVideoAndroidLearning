package cn.eywalink.audiovideoandroidlearning.camera_preview;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;


/**
 * Created by lixin on 2019/3/5.
 */
public class CameraHandlerThread extends HandlerThread {
    private static final String TAG = CameraHandlerThread.class.getSimpleName();

    SurfaceTexture surfaceTexture;
    SurfaceHolder surfaceHolder;

    Handler mHandler;

    public CameraHandlerThread( SurfaceTexture surfaceTexture) {
        super("CameraHandlerThread");
        this.surfaceTexture = surfaceTexture;
        start();
        mHandler = new Handler(getLooper());
    }

    public CameraHandlerThread(SurfaceHolder surfaceHolder) {
        super("CameraHandlerThread");
        this.surfaceHolder = surfaceHolder;
        start();
        mHandler = new Handler(getLooper());
    }

    void openCamera(final Camera.PreviewCallback cb) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (CameraHandlerThread.this.surfaceTexture != null) {
                    CameraUtil.get().openCamera(true)
                            .setStartPreview(surfaceTexture, cb);
                    //notify();
                } else if (CameraHandlerThread.this.surfaceHolder != null) {
                        CameraUtil.get().openCamera(true)
                                .setStartPreview(surfaceHolder, cb);
                    //notify();
                }


            }
        });

        /*
        try {
            wait();
        } catch (InterruptedException e) {
            Log.w(TAG, "wait was interrupted");
        }
        */
    }




}
