package com.bwei.rk_0611;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity---";

    private FlowLayout main_flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initDatas();

    }

    private void initDatas() {

        HttpUtils httpUtils = HttpUtils.getHttpUtils();

        httpUtils.get(HttpConfig.utl);

        httpUtils.setHttpListener(new HttpUtils.HttpListener() {
            @Override
            public void getSuccess(String json) {

                Log.d(TAG, "getSuccess: "+json);

                Gson g = new Gson();

                FlowLayoutBean flowLayoutBean = g.fromJson(json, FlowLayoutBean.class);

                List<FlowLayoutBean.DataBean> data = flowLayoutBean.getData();

                for (int i = 0; i < data.size(); i++) {

                    TextView tv = new TextView(MainActivity.this);

                    tv.setText(data.get(i).getName());

                    main_flow.addView(tv);

                }

            }
        });

    }

    private void initViews() {

        main_flow = findViewById(R.id.main_flow);

    }
}
