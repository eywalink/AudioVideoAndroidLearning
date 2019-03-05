package cn.eywalink.audiovideoandroidlearning.camera_preview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import cn.eywalink.audiovideoandroidlearning.main.utils.permissions.RxPermissions;
import io.reactivex.functions.Consumer;

/**
 * Created by lixin on 2019/3/4.
 */
public class CameraPreviewSurfaceViewActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private static final String TAG = CameraPreviewSurfaceViewActivity.class.getSimpleName();

    // widget
    private SurfaceView sv;

    // data
    private boolean requestOk = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sv = new SurfaceView(getBaseContext());
        setContentView(sv);
        initView();
    }

    private void initView(){
        setTitle("CameraPreview--SurfaceView");
        //sv = findViewById(R.id.sv_camera);
        sv.getHolder().addCallback(this);
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
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (requestOk){
            CameraUtil.get().openCamera(true)
                    .setStartPreview(surfaceHolder, new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    Log.d(TAG, "onPreviewFrame: sufaceview" );
                }
            });
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        CameraUtil.get().releaseCamera();
    }

}



