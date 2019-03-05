package cn.eywalink.audiovideoandroidlearning.camera_preview;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by lixin on 2019/3/5.
 */
public enum CameraUtil {
    INSTANCE;

    public static CameraUtil get(){
        return INSTANCE;
    }

    Camera camera;

    public CameraUtil openCamera(boolean isBackCameraOn) {
        int cameraCount;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        // 遍历可用摄像头
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (!isBackCameraOn) {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    releaseCamera();
                    camera = Camera.open(i);
                    break;
                }
            } else {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    releaseCamera();
                    camera = Camera.open(i);
                    break;
                }
            }
        }
        return this;
    }

    public void releaseCamera() {
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    /**
     * 在SurfaceView中预览相机内容
     *
     */
    public void setStartPreview(SurfaceHolder surfaceHolder, Camera.PreviewCallback cb) {
        if (camera == null){return;}
        if (surfaceHolder == null){return;}
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.setDisplayOrientation(90);
            camera.setPreviewCallback(cb);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在SurfaceTexture中预览相机内容
     *
     */
    public void setStartPreview(SurfaceTexture surfaceTexture, Camera.PreviewCallback cb) {
        if (camera == null){return;}
        if (surfaceTexture == null){return;}
        try {
            camera.setPreviewTexture(surfaceTexture);
            camera.setDisplayOrientation(90);
            camera.setPreviewCallback(cb);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
