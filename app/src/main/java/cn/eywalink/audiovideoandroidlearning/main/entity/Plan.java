package cn.eywalink.audiovideoandroidlearning.main.entity;

/**
 * Created by lixin on 2019/2/22.
 */
public class Plan {

    public Plan(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }

    String name;
    Class activity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
