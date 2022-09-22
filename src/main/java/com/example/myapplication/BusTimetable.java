package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BusTimetable extends AppCompatActivity {

    private WebView webView;            //웹뷰 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_timetable);

        String url = "http://www.u1.ac.kr/html/kr/life/life_060201.html";       //버스 시간표 주소

        webView = (WebView)findViewById((R.id.notice));     //웹뷰 찾음
        webView.getSettings().setJavaScriptEnabled(true);       //자바스크립트 허용
        webView.setWebChromeClient(new WebChromeClient());      // 크롬으로 웹 접속
        webView.setWebViewClient(new WebViewClientClass());     //웹뷰에서 사이트 이동
        webView.loadUrl(url);       //주소로 이동
    }

    //뒤로가기 키를 누룰시 뒤로 이동
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)&& webView.canGoBack()){
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    //앱 내에서 사이트 이동
    static class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }
    }
}