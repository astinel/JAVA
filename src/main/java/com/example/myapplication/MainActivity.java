package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this,"확인",Toast.LENGTH_SHORT);
    }

    //유원대학교 사이트 이동
    public void GotoU1(View v){
        Intent inter = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.u1.ac.kr/html/kr/"));
        startActivity(inter);
    }

    //공지로 이동
    public void GotoAnnouncement(View v){
        Intent inter = new Intent(getApplicationContext(), Announcement.class);
        startActivity(inter);
    }

    //시간표로 이동
    public void GotoTimetable(View v){
        Intent inter = new Intent(getApplicationContext(), Timetable.class);
        startActivity(inter);
    }

    //버스 시간표로 이동
    public void GotoBusTimetable(View v){
        Intent inter = new Intent(getApplicationContext(), BusTimetable.class);
        startActivity(inter);
    }

    //설정으로 이동
    public void GotoOption(View v){
        Intent inter = new Intent(getApplicationContext(), Option.class);
        startActivity(inter);
    }

    //이름 출력
    public void PrintName(View v){
        Toast toa = Toast.makeText(this,"21628001 강경운\n21628003 강전혁\n21628019 양보성",Toast.LENGTH_LONG);
        toa.setGravity(Gravity.CENTER,0,50);
        toa.show();
    }

    public void GotoAlarm(View v){
        Intent inter = new Intent(getApplicationContext(),Alarm.class);
        startActivity(inter);
    }

    public void Timetable2(View v){
        Intent inter = new Intent(getApplicationContext(),Timetable2.class);
        startActivity(inter);
    }
    /* 식단표 이동
    public void GotoDietTable(View v){
        Intent inter = new Intent(getApplicationContext(), DietTable.class);
        startActivity(inter);
    }*/

    /* 사진 바꾸기
    public void Change(View V){
        if(check==true)        ima.setImageResource(R.drawable.background1);
        else        ima.setImageResource(R.drawable.u1);
        check=!check;
    }*/
}