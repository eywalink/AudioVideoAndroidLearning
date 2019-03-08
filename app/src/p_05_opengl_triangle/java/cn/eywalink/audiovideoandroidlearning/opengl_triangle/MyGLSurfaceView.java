package cn.eywalink.audiovideoandroidlearning.opengl_triangle;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by lixin on 2019/3/8.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        // open gl es 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        setRenderer(mRenderer);
    }
}
