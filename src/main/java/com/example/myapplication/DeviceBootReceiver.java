package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class DeviceBootReceiver extends BroadcastReceiver {
    private static  final String TAG = "DeviceBootReceiver";
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        //StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append("Action: " + intent.getAction() + "\n");
        //stringBuilder.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");

        Toast.makeText(context, "재부팅 확인 From u1", Toast.LENGTH_SHORT).show();

        if(preferences.getBoolean("알람",false)){
            if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {

                //기기가 부팅이 완료 될때, 알람을 초기화
                Intent alarmIntent = new Intent(context, Alarm_Reciver.class);      //알람 리시버 클래스 찾음
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                SharedPreferences sharedPreferences = context.getSharedPreferences("alarm", MODE_PRIVATE);      //알람 공유 데이터 가져옴
                long millis = sharedPreferences.getLong("time", Calendar.getInstance().getTimeInMillis());      //시간 불러옴

                Calendar calendar = Calendar.getInstance();     //현재 시간 가져옴
                Calendar nextTime = new GregorianCalendar();      //년,월,일,시간,분,초 시간 클래스
                nextTime.setTimeInMillis(sharedPreferences.getLong("time", millis));      //현재 시간을 직관적으로 형태 바꿈

                if(calendar.after(nextTime)){     //현재시간이 설정된 시간보다 뒤에 있을때
                    millis = calendar.getTimeInMillis();
                    nextTime = Alarm.nextalarm(context,calendar.getTimeInMillis());
                }
                Date date = nextTime.getTime();
                Alarm.notice(context,nextTime);

                //Date currentDateTime = nextTime.getTime();
                String date_text = new SimpleDateFormat("MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(date);
                Toast.makeText(context.getApplicationContext(),date_text + "에 알람이 설정되었습니다.", Toast.LENGTH_LONG).show();

                if (manager != null)
                    manager.setRepeating(AlarmManager.RTC_WAKEUP, nextTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
            }

        }
    }
}














