package com.rock.event;

/**
 * Created by Rock on 2017/3/28.
 */

public class TestEvent {

    public final int WHAT;

    private String msg;


    public TestEvent(int what) {
        WHAT = what;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
