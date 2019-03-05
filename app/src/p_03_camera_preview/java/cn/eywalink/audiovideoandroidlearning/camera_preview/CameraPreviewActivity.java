package cn.eywalink.audiovideoandroidlearning.camera_preview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.eywalink.audiovideoandroidlearning.main.utils.permissions.RxPermissions;
import io.reactivex.functions.Consumer;

/**
 * Created by lixin on 2019/3/5.
 */
public class CameraPreviewActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout;
        Button btn1 = new Button(getBaseContext());
        Button btn2  = new Button(getBaseContext());

        setContentView(linearLayout = new LinearLayout(getBaseContext()));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(btn1);
        linearLayout.addView(btn2);

        btn1.setText("surfaceview");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CameraPreviewSurfaceViewActivity.class));
            }
        });

        btn2.setText("TextureView");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CameraPreviewTextureViewActivity.class));
            }
        });

        initCamera();
    }

    @SuppressLint("CheckResult")
    private void initCamera(){
        new RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean){
                        if (aBoolean) {

                        } else {
                            Toast.makeText(getBaseContext(), "You can not use camera",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
