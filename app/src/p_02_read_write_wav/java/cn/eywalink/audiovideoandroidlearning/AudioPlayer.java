package cn.eywalink.audiovideoandroidlearning;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * Created by lixin on 2019/3/2.
 */
public enum  AudioPlayer {
    INSTANCE;

    AudioTrack track = null;

    public static AudioPlayer getInstance() {
        return INSTANCE;
    }


    public void init(int sampleRateInHz, int channelConfig, int audioFormat){
        int minBufSize = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);

        track = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRateInHz,//44100,
                channelConfig,//AudioFormat.CHANNEL_OUT_STEREO,
                audioFormat, //AudioFormat.ENCODING_PCM_16BIT,
                minBufSize,
                AudioTrack.MODE_STREAM);

        //开始播放
        track.setStereoVolume(1.0f, 1.0f);
        track.play();

    }

    public void readFile(){

    }

    public void play(){

    }

}
