package cn.eywalink.audiovideoandroidlearning.main.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cn.eywalink.audiovideoandroidlearning.App;

public class AssetsUtils {


    // todo 回头可以做个缓存
    public static String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( App.getContext().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            final StringBuilder body = new StringBuilder();
            while((line = bufReader.readLine()) != null) {
                body.append(line);
                //body.append("\n");
            }

            return body.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
