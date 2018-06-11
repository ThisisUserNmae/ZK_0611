package com.bwei.rk_0611;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    private static final String TAG = "HttpUtils====";
    private static HttpUtils httpUtils = new HttpUtils();
    private static final int SUCESS = 0;
    private static final int ERROR = 1;
    private MyHandler myHandler = new MyHandler();
    private HttpListener httpListener;

    private HttpUtils() {
    }

    public static HttpUtils getHttpUtils() {
        synchronized (HttpUtils.class){
            if (httpUtils == null) {
                httpUtils = new HttpUtils();
            }
            return httpUtils;
        }

    }

    public void get(final String url) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL u = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) u.openConnection();
                    con.setConnectTimeout(5000);
                    if (con.getResponseCode() == 200) {
                        InputStream inputStream = con.getInputStream();
                        String json = input2String(inputStream);
                        Log.d(TAG, "run: " + json);
                        Message message = myHandler.obtainMessage();
                        message.what = SUCESS;
                        message.obj = json;
                        myHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCESS:
                    String json = (String) msg.obj;
                    httpListener.getSuccess(json);
                    break;
            }
        }
    }

    public static String input2String(InputStream inputStream) throws IOException {
        String str;
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        StringBuffer stringBuffer = new StringBuffer();
        while ((str = br.readLine()) != null) {
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    public interface HttpListener {
        void getSuccess(String json);
    }

    public void setHttpListener(HttpListener httpListener) {
        this.httpListener = httpListener;
    }
}
