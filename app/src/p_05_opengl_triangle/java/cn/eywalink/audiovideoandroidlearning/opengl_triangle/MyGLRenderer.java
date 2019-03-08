package cn.eywalink.audiovideoandroidlearning.opengl_triangle;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lixin on 2019/3/8.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer{
    private Triangle mTriangle;
    private Square square;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // Set the background frame color
        // 黑色 alpha 是 1
        //红、绿、蓝和 alpha 值，指定值范围均为[ 0.0f,1.0f ]
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
        square = new Square();

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // 调用glViewPort函数来决定视见区域，告诉OpenGL应把渲染之后的图形绘制在窗体的哪个部位。
        // 当视见区域是整个窗体时，OpenGL将把渲染结果绘制到整个窗口。
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // Redraw background color 底色
        // https://blog.csdn.net/hebbely/article/details/69951068
        // 指定当前被激活为写操作的颜色缓存 ?
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        square.draw();
        mTriangle.draw();

    }

}
