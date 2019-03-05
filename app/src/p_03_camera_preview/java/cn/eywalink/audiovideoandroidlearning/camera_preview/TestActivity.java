package cn.eywalink.audiovideoandroidlearning.camera_preview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lixin on 2019/3/5.
 */
public class TestActivity extends AppCompatActivity{
    private static final String TAG = TestActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv;
        setContentView(tv = new TextView(getBaseContext()));
        tv.setText("test memory leak");

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 10000){
                    Log.d(TAG, "run: " +  i++);
                }
            }
        }).start();
    }
}
