package cn.eywalink.audiovideoandroidlearning;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import cn.eywalink.audiovideoandroidlearning.main.utils.permissions.RxPermissions;
import io.reactivex.functions.Consumer;

/**
 * Created by lixin on 2019/2/25.
 */
public class AudioRecordAndPlayActivity extends AppCompatActivity {

    CheckBox cbRecord;
    Button btnPlay;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);

        new RxPermissions(this)
                .request(Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean){
                        if (aBoolean) {

                        } else {
                            Toast.makeText(getBaseContext(), "You can not use record unless you agree the permission request",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        initView();
    }

    private void initView(){
        cbRecord = findViewById(R.id.cb_record);
        btnPlay = findViewById(R.id.btn_play);

        cbRecord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AudioRecorder.getInstance().createDefaultAudio("test");
                    AudioRecorder.getInstance().startRecord();
                }else {
                    AudioRecorder.getInstance().stopRecord();
                }
            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int frequence = 16000;
                        int channelConfig = AudioFormat.CHANNEL_OUT_STEREO;
                        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

                        int bufferSize = AudioTrack.getMinBufferSize(frequence, channelConfig, audioEncoding);
                        byte[] buffer = new byte[bufferSize];
                        try {

                            File file = new File(FileUtils.getPcmFileAbsolutePath("test"));
                            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));


                            //InputStream is = getResources().openRawResource(R.raw.temp);
                            //定义输入流，将音频写入到AudioTrack类中，实现播放
                            //DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
                            //实例AudioTrack
                            AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,
                                    frequence,
                                    channelConfig,
                                    audioEncoding,
                                    bufferSize,
                                    AudioTrack.MODE_STREAM);
                            //开始播放
                            track.play();
                            while(dataInputStream.available()>0){
                                int i = 0;
                                while(dataInputStream.available()>0 && i<buffer.length){
                                    buffer[i] = dataInputStream.readByte();
                                    i++;
                                }
                                track.write(buffer, 0, buffer.length);
                            }
                            //播放结束
                            track.stop();
                            dataInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                    }
                }).start();
            }
        });



    }
}
