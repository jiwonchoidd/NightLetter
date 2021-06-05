package com.cookandroid.nightletter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {
    public void onBackPressed() { //이름 수정으로 가게되는 뒤로가기 막기
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String nameData= test.getString("namedata", "저장안됨");
        TextView text1=(TextView)findViewById(R.id.helloapp);
        text1.setText(nameData+"님의 밤편지입니다. ");
        Button leftButton=(Button)findViewById(R.id.leftbutton);
        Button rightButton=(Button)findViewById(R.id.rightbutton);

        // 왼쪽 버튼은 편지작성
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), writeActivity.class);
                startActivity(intent);
            }
        });
        // 오른쪽 버튼은 사서함
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), BoxActivity.class);
            startActivity(intent);
            }
        });
    }
}
