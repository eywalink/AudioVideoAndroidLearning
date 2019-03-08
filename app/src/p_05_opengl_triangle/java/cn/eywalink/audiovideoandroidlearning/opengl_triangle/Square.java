package cn.eywalink.audiovideoandroidlearning.opengl_triangle;

import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by lixin on 2019/3/8.
 */
public class Square {

    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    private ShortBuffer drawListBuffer;

    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -0.5f , 0.5f, 0.0f,
            -0.5f , -0.5f, 0.0f,
            0.5f , -0.5f, 0.0f,
            0.5f , 0.5f, 0.0f
    };

    // 设置颜色，分别为red, green, blue 和alpha (opacity)
    float color[] = { 0.63671875f, 0.76953125f, 0.62265625f, 1.0f };

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; //顶点的绘制顺序

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main(){" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main(){" +
                    "   gl_FragColor = vColor;" +
                    "}";

    private final int mProgram;


    public Square(){


        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);

        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        ByteBuffer cc = ByteBuffer.allocateDirect(index.length * 2);
        cc.order(ByteOrder.nativeOrder());
        indexBuffer = cc.asShortBuffer();
        indexBuffer.put(index);
        indexBuffer.position(0);


        //ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        //dlb.order(ByteOrder.nativeOrder());
        //drawListBuffer = dlb.asShortBuffer();
        //drawListBuffer.put(drawOrder);
        //drawListBuffer.position(0);

        mProgram = GLSLUtil.loadProgram(vertexShaderCode, fragmentShaderCode);

    }

    private  int mPositionHandle;
    private int mColorHandle;

    // 顶点个数
    private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
    // 定点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * 4;// 每个定点4个字节

    static short index[] = {
            0, 1, 2, 0, 3, 2
    };

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

        //索引法绘制正方形
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, index.length, GLES20.GL_UNSIGNED_SHORT, indexBuffer);

        // 关闭
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
