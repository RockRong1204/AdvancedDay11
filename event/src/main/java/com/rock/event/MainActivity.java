package com.rock.event;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * EventBus 是一个组件间通信的框架
 * 是一个发布者与订阅者的模型
 * <p>
 * 使用分三步
 * 1.定义事件（我们需要传递的数据）
 * 2.注册订阅者
 * - 使用@Subscribe注解修饰方法，接收事件
 * - 在生命周期中注册与反注册EventBus
 * 3.发布事件
 *
 * 特殊需求 粘性事件
 * 发送消息的时候，订阅者并不存在，当订阅者进行订阅的时候，订阅者会接收到最新的一条消息
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Subscribe
    public void onEvent(TestEvent event) {
        switch (event.WHAT) {
            case 180:
                Toast.makeText(this, "老司机翻车了", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "" + event.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 提供了判断是否已经注册
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.teach_add_fragment:
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.add(R.id.teach_container,new TestFragment());
                transaction.add(R.id.teach_container_two,new TestTwoFragment());
                transaction.commit();
                break;
            case R.id.teach_submit:
                TestEvent event = new TestEvent(110);
                event.setMsg("我是马路杀手");
                EventBus.getDefault().post(event);
                break;
            case R.id.teach_post_sticky:
                TestEvent testEvent = new TestEvent(150);
                testEvent.setMsg("啦啦啦");
                EventBus.getDefault().postSticky(testEvent);
                break;
            case R.id.teach_jump:
                Intent intent = new Intent(this, SecondActivity.class);
                TestEvent eventAcrossComponent = new TestEvent(180);
                eventAcrossComponent.setMsg("老司机开车了，快上车");
                EventBus.getDefault().postSticky(eventAcrossComponent);
                startActivity(intent);
                break;
        }
    }
}
