package cn.eywalink.audiovideoandroidlearning.opengl_triangle;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL;

import cn.eywalink.audiovideoandroidlearning.main.utils.AssetsUtils;

/**
 * Created by lixin on 2019/3/8.
 */
public class Triangle {

    private FloatBuffer vertexBuffer;

    // 数组中每个顶点的坐标数
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // 按逆时针方向顺序
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    // 设置颜色，分别为red, green, blue 和alpha (opacity)
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    private final int mProgram;


    private  int mPositionHandle;
    private int mColorHandle;

    // 顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    // 定点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * 4;// 每个定点4个字节

    /*
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
                    */

    public Triangle() {

        // // 为存放形状的坐标，初始化顶点字节缓冲
        // (坐标数 * 4)float占四字节
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length*4);
        // 使用设备的本点字节序
        bb.order(ByteOrder.nativeOrder());

        // 从ByteBuffer创建一个浮点缓冲
        vertexBuffer = bb.asFloatBuffer();

        //把坐标们加入FloatBuffer中
        vertexBuffer.put(triangleCoords);

        // 设置 buffer 从第一个 position 读
        vertexBuffer.position(0);

        //mProgram = GLSLUtil.loadProgram(vertexShaderCode, fragmentShaderCode);
        mProgram = GLSLUtil.loadProgram(AssetsUtils.getFromAssets("vertex.glsl"),
                AssetsUtils.getFromAssets("fragment.glsl"));
    }



    public void draw(){
        // 添加program到 opengl es环境中
        GLES20.glUseProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 开启
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // 获取fragment 的handle
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // 设置回执三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // 绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // 关闭
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }



}
