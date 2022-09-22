package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Alarm extends AppCompatActivity {

    Calendar calendar;
    Date date;      //날짜 변수

    final static int D = 86400000;
    final static int H = D / 24;
    final static int M = H / 60;

    //시간 조정 함수
    public static Calendar TimeControl(Calendar calendar){
        calendar = Calendar.getInstance();
        long millis = calendar.getTimeInMillis();
        millis += M/6;
        calendar.setTimeInMillis(millis);

        return calendar;
    }

    //알림 출력 함수
    public static NotificationCompat.Builder notice(Context context,Calendar calendar){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingI = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

        Date date = calendar.getTime();

        String str1 = new SimpleDateFormat("M월 d일 E요일 a h시 m분", Locale.getDefault()).format(date);
        String str2 = "";
        int d, h, m, s, ms;     //일, 시간, 분, 초

        ms = (int)(calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) + 500;     //시간 차이를 초 형태로 나타낸수를 대입
        s = (ms / 1000) % 60;
        d = ms / D;    ms -=d * D;     //일 구하고 남은 초 구함
        h = ms / H;   ms -= h * H;       //시간 구하고 남은 초 구함
        m = ms / M;     //분을 구함
        if(d > 0)       str2 += d + "일 ";
        if(h > 0)       str2 += h + "시간 ";
        if(m > 0)       str2 += m + "분 ";
        if(s > 0)       str2 += s + "초";

        str1 += "에 알람 설정";
        str2 += "후에 알람이 울립니다.";

        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.u1); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남

            String channelName =str1;
            String description = str2;
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }
        }else builder.setSmallIcon(R.mipmap.icon); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        builder.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("{alarm}")
                .setContentTitle(str1)
                .setContentText(str2)
                .setContentInfo("INFO")
                .setContentIntent(pendingI);

        if (notificationManager != null) {
            notificationManager.notify(1234, builder.build());      // 노티피케이션 동작시킴

            //Toast.makeText(this,"알람", Toast.LENGTH_SHORT).show();
        }

        return builder;
    }

    //다음 알람 보여주기
    public static Calendar nextalarm(Context context, long millis){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());     //공유 데이터 클래스 선언
        Calendar calendar = new GregorianCalendar();      //년,월,일,시간,분,초 시간 클래스
        Date date;
        String day;     //요일
        String timelist;        //시간 목록
        String hour;
        Boolean bool = true;
        final short first = 8;      //기준 시간
        int  time = 0;
        int ago = preferences.getInt("ago",10);
        short TTL = 7;      //반복문이 한번 실행될때마다 감소(무한 루프 방지)

        while(bool){
            calendar.setTimeInMillis(millis);     //밀리초 시간으로 현재 시간설정
            date = calendar.getTime();       //날짜 얻음

            day = new SimpleDateFormat("E",Locale.getDefault()).format(date);       //현재 요일 얻기
            timelist = preferences.getString(day,"");       //요일에 따른 시간 얻기
            hour = new SimpleDateFormat("H",Locale.getDefault()).format(date);     //현재 시간 얻기

            for(int i = 0;i < timelist.length();i += 2){      //시간 목록 차례대로 읽기
                time = Integer.valueOf(timelist.charAt(i)) + first - '0';       //(수업 시간의 아스키 코드 값) + (기준 시간) - (0의 아스키 코드 값)
                if(Integer.valueOf(hour) < time){         //현재 시간이 시간 목록에 있는 시간보다 적을 경우
                    bool = false;       //계속 진행하지 않음
                    calendar.set(Calendar.HOUR,time);
                    calendar.set(Calendar.MINUTE,10 - ago);
                    calendar.set(Calendar.SECOND,0);
                    break;
                }
            }
            if(--TTL == 0)        break;      //무한루프 탈출
            if(bool){       //계속 진행시 하루 뒤 자정을 초기화
                calendar.add(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY,0);
                millis = calendar.getTimeInMillis();
            }
        }
        //return TimeControl(calendar);
        return calendar;        //설정된 알람 시간 반환
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);      //공유 데이터 클래스
        if(preferences.getBoolean("알람",false))
        {
            calendar = nextalarm(this, Calendar.getInstance().getTimeInMillis());

            notice(this,calendar);

            SharedPreferences.Editor editor = getSharedPreferences("alarm", MODE_PRIVATE).edit();       //알람 편집기
            editor.putLong("time", calendar.getTimeInMillis());     //현재 시간 밀리초 형태로 저장
            editor.apply();     //편집 내용 적용

            diaryNotification(calendar,this);
        }
        finish();       //액티비티 종료
    }
//String str = new SimpleDateFormat("MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(date);
    public static void diaryNotification(Calendar cal,Context context)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = context.getPackageManager();       //패키지 매니저 객체 구함
        ComponentName receiver = new ComponentName(context, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(context, Alarm_Reciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                }
            }
            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        }
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }


    }
}
