package cn.eywalink.audiovideoandroidlearning.camera_preview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.widget.Toast;

import cn.eywalink.audiovideoandroidlearning.main.utils.permissions.RxPermissions;
import io.reactivex.functions.Consumer;

/**
 * Created by lixin on 2019/3/4.
 */
public class CameraPreviewTextureViewActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener{
    private static final String TAG = CameraPreviewTextureViewActivity.class.getSimpleName();

    // widget
    private TextureView tv;

    // data
    private boolean requestOk = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(tv = new TextureView(getBaseContext()));
        initView();
    }

    private void initView(){
        setTitle("CameraPreview--TextureView");
        tv.setSurfaceTextureListener(this);
        initCamera();
    }

    @SuppressLint("CheckResult")
    private void initCamera(){
        new RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean){
                        requestOk = aBoolean;
                        if (aBoolean) {
                        } else {
                            Toast.makeText(getBaseContext(), "You can not use camera",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture, int i, int i1) {
        new CameraHandlerThread(surfaceTexture).openCamera(new CameraCallback());
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        CameraUtil.get().releaseCamera();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    private static class CameraCallback implements Camera.PreviewCallback{

        @Override
        public void onPreviewFrame(byte[] bytes, Camera camera) {
            Log.d(TAG, Thread.currentThread().getName() + " onPreviewFrame: " + bytes);
        }
    }

}
