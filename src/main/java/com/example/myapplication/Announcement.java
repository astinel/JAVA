package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Announcement extends AppCompatActivity {

    private WebView webView;        //웹뷰 변수
    private String url;     //주소 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        webView = (WebView)findViewById((R.id.notice));     //웹뷰 찾음
        webView.getSettings().setJavaScriptEnabled(true);       //자바스크립트 사용 허용
        webView.setWebChromeClient(new WebChromeClient());      //크롬으로 웹 접속
        webView.setWebViewClient(new WebViewClientClass());     //웹뷰내에서 사이트 이동
        normal(null);       //일반 공지 띄움
    }
        //일반 공지
    public void normal(View v){
        url = "http://www.u1.ac.kr/_prog/_board/?code=ydu_12";
        webView.loadUrl(url);
    }
        //학사 공지
    public void college(View v){
        url = "http://www.u1.ac.kr/_prog/_board/?code=ydu_13";
        webView.loadUrl(url);
    }
        //장학 공지
    public void encouragement(View v){
        url = "http://www.u1.ac.kr/_prog/_board/?code=ydu_14";
        webView.loadUrl(url);
    }
        //취업 공지
    public void job(View v){
        url = "http://www.u1.ac.kr/_prog/_board/?code=ydu_15";
        webView.loadUrl(url);
    }
        //입찰 공지
    public void tender(View v){
        url = "http://www.u1.ac.kr/_prog/_board/?code=ydu_16";
        webView.loadUrl(url);
    }
        //채용 공지
    public void employ(View v){
        url = "http://www.u1.ac.kr/_prog/_board/?code=ydu_17";
        webView.loadUrl(url);
    }
        //뒤로가기 키를 누를시 뒤로이동
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