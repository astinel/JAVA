package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Option extends AppCompatActivity {
    SharedPreferences preference;       //공유 데이터 변수
    SharedPreferences.Editor editor;        //공유 데이터 편집기

    Boolean bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        preference = PreferenceManager.getDefaultSharedPreferences(this);       //공유 데이터 변수 클래스
        editor = preference.edit();        //공유 데이터 편집 모드

        bool = preference.getBoolean("알람",false);
        changetext();
    }

    private void changetext(){
        TextView text;

        text = findViewById(R.id.alarm);
        if(bool)        text.setText("알람 끄기");
        else            text.setText("알람 켜기");
    }

    public void ago5(View v){
        int a = 5;

        editor.putInt("ago",a);
        Toast.makeText(this,"수업 " + a + "분전으로 알람이 설정됩니다.",Toast.LENGTH_SHORT).show();
    }

    public void ago10(View v){
        int a = 10;

        editor.putInt("ago",10);
        Toast.makeText(this,"수업 " + a + "분전으로 알람이 설정됩니다.",Toast.LENGTH_SHORT).show();
    }

    public void alarm(View v){
        bool=!bool;
        changetext();
        editor.putBoolean("알람",bool);
    }

    //초기화 함수
    public void reset(View v){
        editor.clear();     //데이터 초기화
        Toast.makeText(this,"초기화합니다.", Toast.LENGTH_LONG).show();
        finish();       //액티비티 종료
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        editor.apply();     //데이터 적용
    }
}