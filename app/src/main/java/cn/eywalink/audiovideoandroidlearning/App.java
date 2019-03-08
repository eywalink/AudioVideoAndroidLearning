package cn.eywalink.audiovideoandroidlearning;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by lixin on 2019/3/5.
 */
public class App extends Application {

    static Context context;


    @Override public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }

    public static Context getContext(){
        return context;
    }
}
