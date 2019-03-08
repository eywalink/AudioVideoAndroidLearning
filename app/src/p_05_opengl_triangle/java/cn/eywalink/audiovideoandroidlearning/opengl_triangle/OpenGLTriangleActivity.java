package cn.eywalink.audiovideoandroidlearning.opengl_triangle;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by lixin on 2019/3/8.
 */
public class OpenGLTriangleActivity extends Activity{

    MyGLSurfaceView glsv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glsv = new MyGLSurfaceView(getBaseContext());
        setContentView(glsv);

    }


}
