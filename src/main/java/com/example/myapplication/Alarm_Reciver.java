package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Alarm_Reciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingI = PendingIntent.getActivity(context, 0,notificationIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남

            String channelName ="유원대학교 알람";
            String description = "정해진 시간에 알람합니다.";

            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }
        }else builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Calendar calendar = Calendar.getInstance();
        Date date;
        long millis = calendar.getTimeInMillis();

        if(preferences.getBoolean("알람",false)){
            calendar = Alarm.nextalarm(context,calendar.getTimeInMillis());
            date = calendar.getTime();
            //date = Alarm.TimeControl(calendar.getTimeInMillis(),calendar);
            builder = Alarm.notice(context,calendar);

            if (notificationManager != null) {

                // 노티피케이션 동작시킴
                notificationManager.notify(1234, builder.build());

                //  Preference에 설정한 값 저장
                SharedPreferences.Editor editor = context.getSharedPreferences("alarm", MODE_PRIVATE).edit();
                editor.putLong("time", calendar.getTimeInMillis());
                editor.apply();

                //Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(date);
                Toast.makeText(context.getApplicationContext(),"다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_LONG).show();
                Alarm.diaryNotification(calendar,context);
            }
        }
    }
}














