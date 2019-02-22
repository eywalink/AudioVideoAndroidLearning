package cn.eywalink.audiovideoandroidlearning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

import cn.eywalink.audiovideoandroidlearning.R;
import cn.eywalink.audiovideoandroidlearning.widget.MySurfaceView;

/**
 * Created by lixin on 2019/2/22.
 */
public class DrawPictureActivity extends AppCompatActivity {

    MySurfaceView  sv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_picture);
        initView();
    }

    private void initView() {
        sv = findViewById(R.id.sv_picture);
        sv.showImage(R.drawable.ic_boruto);

    }



}
