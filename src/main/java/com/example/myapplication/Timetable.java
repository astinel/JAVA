package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Timetable extends AppCompatActivity {
    final short max = 9;

    SharedPreferences preference;       //데이터 클래스 선언
    SharedPreferences.Editor editor;
    TableLayout tableLayout;
    LinearLayout linearLayout;
    Spinner spinner;
    short sub;
    char Day[] = {'월','화','수','목','금'};
    //과목 목록
    private String subject[] = {
            "IoT 네트워크 보안",
            "디지털포렌식",
            "마이크로컴퓨터 응용 및 실습",
            "시스템보안",
            "자바소프트웨어코딩",
            "정보보호론",
            "창의설계 및 프로젝트",
    };
    //수업 시간 데이터
    private String time(String sub){      //과목 이름을 인자로 받음
        String str = "";     //문자열 변수

        if(sub.compareTo("IoT 네트워크 보안") == 0)                  str = "월3,4 화3 ";
        else if(sub.compareTo("디지털포렌식") == 0)                  str = "수1,2,3 ";
        else if(sub.compareTo("마이크로컴퓨터 응용 및 실습") == 0)    str = "수5,6,7,8 ";
        else if(sub.compareTo("시스템보안") == 0)                    str = "화2 목6,7 ";
        else if(sub.compareTo("자바소프트웨어코딩") == 0)             str = "수3,4 목2 ";
        else if(sub.compareTo("정보보호론") == 0)                    str = "월6,7 목3 " ;
        else if(sub.compareTo("창의설계 및 프로젝트") == 0)          str = "화5,6,7,8 ";

        return str;
    }

    //날짜 확인 함수
    private short daycheck(char c){
        short d;

        for(d = 0;d < Day.length;d++){
            if(c == Day[d])     break;
        }
        return d;
    }

    //텍스트 뷰 생성
    private TextView maketext(String str){
        final float size = 20;

        TextView textView = new TextView(this);
        textView.setText(str);      //텍스트 뷰에 시간 표시
        textView.setTextSize(size);       //텍스트 크기 설정
        textView.setTextColor(Color.BLACK);     //텍스트 색깔 검정

        return textView;
    }

    //삭제 버튼 생성 함수
    private TextView button(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = maketext("X");
        textView.setTextColor(Color.RED);
        textView.setOnClickListener(v -> delete(v));
        textView.setLayoutParams(layoutParams);

        return textView;
    }

    private void PrintTime(String sub,Boolean boo){
        LinearLayout layout;
        TextView cell;
        String str = time(sub);
        int t;
        short d;

        for(int i = 0;i < str.length();i++){        //문자열의 길이 만큼 반복
            d = daycheck(str.charAt(i));        //요일을 숫자로 변환
            layout = (LinearLayout) linearLayout.getChildAt(d + 1);     //해당 요일 레이아웃 가져옴
            do{
                t = Integer.valueOf(str.charAt(++i));       //문자열에 있는 시간 정수형으로 바꿈
                t = t++ - '0';
                cell = (TextView) layout.getChildAt(t);        //시간에 해당하는 칸 가져옴
                if(boo)     cell.setText(sub);      //해당 칸에 과목 이름 넣기
                else        cell.setText("");       //해당 칸 삭제
                if(str.charAt(++i) == ' ')      break;
            }while(true);
            merge(++d);
        }
    }

    //데이터 출력 함수
    private void print(String sub){
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
        TableRow table = new TableRow(this);        //새로운 테이블 로우 생성
        LinearLayout timelayout = new LinearLayout(this);       //새로운 리니어 레이아웃 생성
        TextView textView[] = new TextView[3];
        String str[] = new String[2];
        int t;
        short d;

        str[0] = sub;
        str[1] = time(str[0]);      //과목에 따른 시간 불러오기

        //과목 리스트 출력
        textView[2] = button();
        for(short b = 0;b < 2;b++)      textView[b] = maketext(str[b]);      //새로운 텍스트 뷰 만듬
        for(short a = 1;a <= 2;a++)     timelayout.addView(textView[a]);
        table.addView(textView[0]);        //생성된 텍스트 뷰 테이블 로우에 넣기
        table.addView(timelayout);
        tableLayout.addView(table,layoutParams);      //생성된 테이블 로우 테이블 레이아웃에 넣기

        PrintTime(str[0],true);
    }

    //공백 확인 함수
    private boolean compare(String str){
        if(str.compareTo("")==0)    return true;
        else        return false;
    }

    //중복 합치기
    private void merge(short d){
        LinearLayout layout = (LinearLayout)linearLayout.getChildAt(d);
        LinearLayout.LayoutParams params;
        String str1,str2;
        TextView text1,text2;
        String str = "";

        for(short t = 1;t < max;t++){
            text1 = (TextView) layout.getChildAt(t);        //첫 번째 텍스트 가져옴
            str1 = text1.getText().toString();      //첫 번째 텍스트에서 문자열 가져옴
            if(compare(str1)){      //첫 번째 문자열에 아무것도 없다면
                params = (LinearLayout.LayoutParams) text1.getLayoutParams();
                params.weight = 1;
                text1.setLayoutParams(params);
                continue;        //건너 뜀
            }
            str += t + ",";
            editor.putString(Day[d-1] + "",str);
            for(float s = 1;t < max;t++,s++){
                text2 = (TextView)layout.getChildAt(t+1);       //두 번째 텍스트 가져옴
                str2 = text2.getText().toString();      //두 번째 텍스트에서 문자열 가져옴
                if(str1.compareTo(str2)==0){        //첫 번째 문자열과 두 번째 문자열이 같다면
                    params = (LinearLayout.LayoutParams) text2.getLayoutParams();       //두 번째 텍스트 요소 가져옴
                    params.weight=0;        //크기 0
                    text2.setLayoutParams(params);      //두 번째 텍스트에 요소 적용
                    continue;       //건너 뜀
                }
                params =(LinearLayout.LayoutParams) text1.getLayoutParams();        //첫 번째 텍스트 요소 가져옹
                params.weight = s;        //중복된 내용 갯수만큼 크기 적용
                text1.setLayoutParams(params);      //첫 번째 텍스트에 요소 적용
                break;
            }
        }
    }

    private void load(){
        for (short a = 0; a < sub; a++)     print(preference.getString("sub" + a,""));        //데이터 불러오기
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        //데이터 초기화
        preference = PreferenceManager.getDefaultSharedPreferences(this);       //데이터 클래스
        editor = preference.edit();     //데이터 편집기
        //Time = findViewById(R.id.Time);     //시간 레이아웃 찾음
        //Delete = findViewById(R.id.Delete);
        //스피너 초기화
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,subject);        //배열 목록 만들기
        spinner = findViewById(R.id.spinner);       //스피너 찾기
        spinner.setAdapter(arrayAdapter);       //스피너에 배열 목록 적용

        //레이아웃 초기화
        linearLayout = findViewById(R.id.LinearLayout);
        tableLayout = findViewById(R.id.TableLayout);
        sub = (short) preference.getInt("sub", 0);      //과목 개수 불러옴
        load();
    }

    //중복 확인
    private Boolean check(String sub){     //입력된 과목
        LinearLayout layout;
        TextView textView;
        String str,str2;
        int t;
        short d;

        //과목 중복 확인
        for(int a = 1;a < tableLayout.getChildCount();a++){     //과목의 개수 만큼 반복
            textView = (TextView) ((TableRow) tableLayout.getChildAt(a)).getChildAt(0);      //과목에서 텍스트뷰 하나 가져옴
            str = textView.getText().toString();       //텍스트뷰에서 문자열 가져옴
            if(sub.compareTo(str) == 0){      //입력된 과목과 가져온 문자열이 같다면
                Toast.makeText(this,"중복",Toast.LENGTH_SHORT).show();        //중복 메세지 출력
                return false;       //실행되지 않음
            }
        }
        //시간 중복 확인
        str = time(sub);
        for(int i = 0;i < str.length();i++){        //문자열의 길이 만큼 반복
            d = daycheck(str.charAt(i));        //요일을 숫자로 변환
            layout = (LinearLayout) linearLayout.getChildAt(d + 1);     //해당 요일 레이아웃 가져옴
            do{
                t = Integer.valueOf(str.charAt(++i));       //문자열에 있는 시간 정수형으로 바꿈
                t = t++ - '0';
                textView = (TextView) layout.getChildAt(t);        //시간에 해당하는 칸 가져옴
                str2 = textView.getText().toString();
                if(!compare(str2)){     //해당 시간에 다른 과목이 있을 경우
                    Toast.makeText(this,"시간 중복",Toast.LENGTH_SHORT).show();
                    return false;       //실행돠지 않음
                }
                if(str.charAt(++i) == ' ')      break;     //요일 데이터가 끝나면 빠져 나감
            }while(true);
        }
        return true;
    }

    //과목 추가 함수
    public void add(View v){
        String str = spinner.getSelectedItem().toString();       //스피너에 선택된 문자열 가져오기

        if(check(str)){
            print(str);
            editor.putString("sub" + sub,str);
            sub++;

            Toast.makeText(this,str + " 추가",Toast.LENGTH_SHORT).show();
        }
    }

    //과목 삭제 함수
    public void delete(View v){
        TableRow tableRow = (TableRow) v.getParent().getParent();
        TextView textView = (TextView)tableRow.getChildAt(0);
        String str = textView.getText().toString();

        tableLayout.removeView(tableRow);
        PrintTime(str,false);        //해당 과목 시간표에서 삭제
        Toast.makeText(this,str + "삭제",Toast.LENGTH_SHORT).show();

        for(short a = 1;a < sub;a++){
            tableRow = (TableRow) tableLayout.getChildAt(a);
            textView = (TextView) tableRow.getChildAt(0);
            str = textView.getText().toString();
            editor.putString("sub" + a, str);
        }
        sub--;
        editor.apply();
    }

    //확인 함수
    /*private boolean check(){
        String str;
        if(ind==text.length)     return false;
        for(int a=0;a<ind;a++){
            str = text[a].getText().toString();
            if(string.compareTo(str)==0)        return false;
        }
        return true;
    }*/

    //과목 추가 함수
    /*public void add(View v){
        String str ="내용을 입력하세요";
        string = editText.getText().toString();

        if(check(str)){
            if(string.compareTo("")!=0) {
                if (ind < text.length) {
                    text[ind].setText(string);
                    editText.setText("");
                    editor.putString("sub"+ind,string);
                    editor.putInt("ind",++ind);
                    str = "("+string+") 추가";
                }
            }
        }
        else {
            if(ind == text.length)      str = "더 이상 추가 할수 없습니다.";
            else                        str = "동일한 내용이 존재합니다";
        }
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }*/

    //과목 선택 함수
    /*public void select(View v) {
        //int id = v.getId();

        TextView text;
        if(Id!=0){
            ((TextView)findViewById(Id)).setTextColor(Color.BLACK);
            //text = findViewById(Id);
            //text.setTextColor(Color.BLACK);
        }
        ((TextView)v).setTextColor(Color.RED);
        //text = findViewById(id);
        //text.setTextColor(Color.RED);

        Id= v.getId();
    }*/

    //알람 출력 함수
    public void Alarm(View v){
        Intent inter = new Intent(getApplicationContext(),Alarm.class);
        startActivity(inter);
    }

    @Override
    protected void onStop(){        //액티비티가 멈출때
        super.onStop();

        editor.putInt("sub",sub);
        editor.apply();     //데이터 적용
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Intent inter = new Intent(getApplicationContext(),Alarm.class);
        //startActivity(inter);
    }
}
    /*
    boolean check(int d){
        num=0;
        string = editText.getText().toString();
        if(string.compareTo("")!=0)
            num = Integer.parseInt(string);
        return (num > 0 && num < (max+1));
    }*/

//android:ellipsize="marquee"