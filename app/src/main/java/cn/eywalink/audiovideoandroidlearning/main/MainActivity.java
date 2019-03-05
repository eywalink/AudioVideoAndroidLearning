package cn.eywalink.audiovideoandroidlearning.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    }

    private void initData(){
        String[] array = getResources().getStringArray(R.array.plan_steps);
        data.add(new Plan(array[0], DrawPictureActivity.class));
        data.add(new Plan(array[1], AudioRecordAndPlayActivity.class));
        data.add(new Plan(array[2], CameraPreviewActivity.class));
        data.add(new Plan(array[3], TestActivity.class));

    }

    private void initView(){
        rvPlan = findViewById(R.id.rv_plan);
        rvPlan.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvPlan.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        rvPlan.setAdapter(new PlanAdapter(data));
    }
}
