package com.example.administrator.day0425rikaoti;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.xlistview.XListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements XListView.IXListViewListener {

    private XListView xListView;
    //上拉下拉使用页码
    private int page = 1;

    private Beanadapter adapter;

    private List<Bean.ListBean> list = new ArrayList<Bean.ListBean>();

    MyHandler handler = new MyHandler(this);

    static class MyHandler extends Handler{
        //
        WeakReference<MainActivity> mWeakReference;
        public MyHandler(MainActivity activity){
            mWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mWeakReference.get();
            if (activity==null){
                return;
            }
            switch (msg.what){
                    case 1:
                        List<Bean.ListBean> listbean =(List<Bean.ListBean>) msg.obj;
                        activity.update(listbean);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xListView = (XListView) findViewById(R.id.xlistview);
        xListView.setXListViewListener(this);
        //设置上拉方法
        xListView.setPullLoadEnable(true);
        //设置下拉方法
        xListView.setPullRefreshEnable(true);

        adapter = new Beanadapter(list,this);

        getinda(page);

    }

    private void update(List<Bean.ListBean> data){
        list.addAll(data);
        adapter.notifyDataSetChanged();

        }
    //请求数据
    private void getinda(int page) {
        Map map = new HashMap();
        map.put("page", page);
        map.put("postkey", "1503d");

        new IAcsyTask(handler).execute(map);

    }



    //下拉加载
    @Override
    public void onRefresh() {

    }
    //上拉加载
    @Override
    public void onLoadMore() {

    }
}
