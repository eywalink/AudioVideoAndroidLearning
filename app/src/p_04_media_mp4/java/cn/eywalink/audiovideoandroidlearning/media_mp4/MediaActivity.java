package cn.eywalink.audiovideoandroidlearning.media_mp4;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by lixin on 2019/3/6.
 */
public class MediaActivity extends Activity {
    private static final String TAG = MediaActivity.class.getSimpleName();

    // wiget
    Button btnMux;
    Button btnRecord;
    LinearLayout ll;

    // data
    String dir  = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/mp4/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(ll = new LinearLayout(getBaseContext()));
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(btnRecord = new Button(getBaseContext()));
        btnRecord.setText("第一步：生成录音");
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AudioRecordActivity.class));
            }
        });

        ll.addView(btnMux = new Button(getBaseContext()));
        btnMux.setText("第一步：根据录音生成新的mp4文件");
        btnMux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    File file = new File(dir);
                    //创建目录
                    boolean result = false;
                    if (!file.exists()) {
                        result = file.mkdirs();
                    }
                    Toast.makeText(getBaseContext(), "正在生产，在sd卡的mp4目录看 new.mp4结果", Toast.LENGTH_LONG).show();
                    muxingAudioAndVideo(dir + "new.mp4");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void muxingAudioAndVideo(String outputVideoPath
    ) throws IOException {
        MediaMuxer mMediaMuxer = new MediaMuxer(outputVideoPath,
                MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);

        // 视频的MediaExtractor
        MediaExtractor mVideoExtractor = new MediaExtractor();
        AssetFileDescriptor afd2 = getAssets().openFd("sample_2.mp4");
        //mVideoExtractor.setDataSource(videoPath);
        mVideoExtractor.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(),afd2.getLength());
        int videoTrackIndex = -1;
        int trackCount0 = mVideoExtractor.getTrackCount();
        for (int i = 0; i < trackCount0; i++) {
            MediaFormat format = mVideoExtractor.getTrackFormat(i);
            if (format.getString(MediaFormat.KEY_MIME).startsWith("video/")) {
                mVideoExtractor.selectTrack(i);
                //
                videoTrackIndex = mMediaMuxer.addTrack(format);
                break;
            }
        }

        // 音频的MediaExtractor
        MediaExtractor mAudioExtractor = new MediaExtractor();
        //AssetFileDescriptor afd3 = getAssets().openFd("sample_3.aac");
        //mAudioExtractor.setDataSource(afd3.getFileDescriptor());
        mAudioExtractor.setDataSource(dir + "record.aac");
        int audioTrackIndex = -1;
        int trackCount = mAudioExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            MediaFormat format = mAudioExtractor.getTrackFormat(i);
            if (format.getString(MediaFormat.KEY_MIME).startsWith("audio/")) {
                mAudioExtractor.selectTrack(i);
                // 小米手机 mine = audio/mp4a-latm支持，audio/mpeg不支持。
                audioTrackIndex = mMediaMuxer.addTrack(format);
            }
        }

        // 添加完所有轨道后start
        mMediaMuxer.start();

        // 封装视频track
        if (-1 != videoTrackIndex) {
            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            info.presentationTimeUs = 0;
            ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
            while (true) {
                int sampleSize = mVideoExtractor.readSampleData(buffer, 0);
                if (sampleSize < 0) {
                    break;
                }

                info.offset = 0;
                info.size = sampleSize;
                info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME;
                info.presentationTimeUs = mVideoExtractor.getSampleTime();
                mMediaMuxer.writeSampleData(videoTrackIndex, buffer, info);

                mVideoExtractor.advance();
            }
        }

        // 封装音频track
        if (-1 != audioTrackIndex) {
            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            info.presentationTimeUs = 0;
            ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
            while (true) {
                int sampleSize = mAudioExtractor.readSampleData(buffer, 0);
                if (sampleSize < 0) {
                    break;
                }

                info.offset = 0;
                info.size = sampleSize;
                info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME;
                info.presentationTimeUs = mAudioExtractor.getSampleTime();
                mMediaMuxer.writeSampleData(audioTrackIndex, buffer, info);

                mAudioExtractor.advance();
            }
        }

        // 释放MediaExtractor
        mVideoExtractor.release();
        mAudioExtractor.release();

        // 释放MediaMuxer
        mMediaMuxer.stop();
        mMediaMuxer.release();
    }
}
