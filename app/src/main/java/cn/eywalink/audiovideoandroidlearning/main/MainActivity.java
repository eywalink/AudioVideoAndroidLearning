package cn.eywalink.audiovideoandroidlearning.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.eywalink.audiovideoandroidlearning.AudioRecordAndPlayActivity;
import cn.eywalink.audiovideoandroidlearning.DrawPictureActivity;
import cn.eywalink.audiovideoandroidlearning.R;
import cn.eywalink.audiovideoandroidlearning.camera_preview.CameraPreviewActivity;
import cn.eywalink.audiovideoandroidlearning.camera_preview.CameraPreviewSurfaceViewActivity;
import cn.eywalink.audiovideoandroidlearning.camera_preview.CameraPreviewTextureViewActivity;
import cn.eywalink.audiovideoandroidlearning.camera_preview.TestActivity;
import cn.eywalink.audiovideoandroidlearning.main.entity.Plan;
import cn.eywalink.audiovideoandroidlearning.main.utils.permissions.RxPermissions;
import cn.eywalink.audiovideoandroidlearning.media_mp4.MediaActivity;
import cn.eywalink.audiovideoandroidlearning.opengl_triangle.OpenGLTriangleActivity;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    // widget
    RecyclerView rvPlan;

    // data
    List<Plan> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();

        reqPermission();
    }

    @SuppressLint("CheckResult")
    private void reqPermission() {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                        )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean){
                        if (aBoolean) {
                        } else {
                        }
                    }
                });
    }

    private void initData(){
        String[] array = getResources().getStringArray(R.array.plan_steps);
        data.add(new Plan(array[0], DrawPictureActivity.class));
        data.add(new Plan(array[1], AudioRecordAndPlayActivity.class));
        data.add(new Plan(array[2], CameraPreviewActivity.class));
        data.add(new Plan(array[3], MediaActivity.class));
        data.add(new Plan(array[4], OpenGLTriangleActivity.class));

    }

    private void initView(){
        rvPlan = findViewById(R.id.rv_plan);
        rvPlan.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvPlan.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        rvPlan.setAdapter(new PlanAdapter(data));
    }
}
