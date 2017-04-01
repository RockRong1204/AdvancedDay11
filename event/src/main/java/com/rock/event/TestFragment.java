package com.rock.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Rock on 2017/3/28.
 */

public class TestFragment extends Fragment implements View.OnClickListener {
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        button = new Button(getActivity());

        button.setText("Fragment");

        button.setOnClickListener(this);

        return button;
    }

    @Override
    public void onClick(View v) {
        TestEvent event = new TestEvent(120);
        event.setMsg("隔壁驾校派过来的");
        EventBus.getDefault().post(event);
    }


    /**
     * 接收事件
     */

    @Subscribe
    public void onEvent(TestEvent event) {
        button.setText(event.getMsg() + "");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
