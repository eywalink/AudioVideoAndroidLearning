package cn.eywalink.audiovideoandroidlearning.opengl_triangle;

import android.opengl.GLES20;

/**
 * Created by lixin on 2019/3/8.
 */
public class GLSLUtil {


    public static int loadShader(int type, String shaderCode) {
        //根据type创建顶点着色器或者片元着色器
        //创建一个vertex shader类型(GLES20.GL_VERTEX_SHADER)
        //或一个fragment shader类型(GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        //将资源加入到着色器中，并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }



    public static int loadProgram(String vertexShaderCode, String fragmentShaderCode){
        // 编译shader代码
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // 创建空的OpenGL ES Program
        int mProgram = GLES20.glCreateProgram();

        // 将vertex shader(顶点着色器)添加到program
        GLES20.glAttachShader(mProgram, vertexShader);

        // 将fragment shader（片元着色器）添加到program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // link
        GLES20.glLinkProgram(mProgram);
        return mProgram;
    }
}
