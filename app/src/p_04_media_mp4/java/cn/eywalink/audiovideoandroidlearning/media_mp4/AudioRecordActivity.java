package cn.eywalink.audiovideoandroidlearning.media_mp4;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.io.File;
import java.io.IOException;

/**
 * Created by lixin on 2019/3/6.
 */
public class AudioRecordActivity extends Activity{

    // Widget
    CheckBox cb;

    //
    MediaRecorder mr = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(cb = new CheckBox(getBaseContext()));
        cb.setText("点击录音");
        cb.setChecked(false);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    /*
                    new Thread(new Runnable() {
                        @Override
                        public void run() */
                    {
                        String dir  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mp4/";
                        File file = new File(dir);
                        if (!file.exists()) {file.mkdirs();}

                        if (mr == null){
                            mr = new MediaRecorder();
                            mr.reset();
                            mr.setAudioSource(MediaRecorder.AudioSource.MIC);
                            mr.setAudioEncodingBitRate(16000);
                            mr.setAudioSamplingRate(8000);
                            mr.setAudioChannels(2);

                            mr.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
                            mr.setOutputFile(dir+"record.aac");
                            // 这个要放在最后设置，不然出问题
                            // java.lang.IllegalStateException
                            //        at android.media.MediaRecorder.setAudioEncoder(Native Method)
                            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                        }
                        try {
                            mr.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mr.start();
                    }
                    //}
                    //).start();

                    cb.setText("点击停止");
                }else {
                    if (mr!=null) {
                        mr.stop();
                        mr.release();
                        mr = null;
                    }
                    cb.setText("点击录音");

                }
            }
        });

    }
}
