package com.cookandroid.nightletter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button goMenu = (Button) findViewById(R.id.GoMenu);
        ImageButton imf=(ImageButton)findViewById(R.id.InfomationBtn);
        final EditText userName = (EditText) findViewById(R.id.UserName);
        TextView hello=(TextView) findViewById(R.id.hello) ;
        hello.setText(R.string.app_hello);
        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE );

        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        // 앱을 종료해도 이름 정보를 저장하기위해서 SharedPreferences 객체를 이용
        String nameData = test.getString("namedata", "저장안됨");
        // 네임데이터를 불러옴 이미 저장된 정보가 있다면 다음 엑티비티로 이동하게함

        if (nameData == "저장안됨") { // 만약 이름이 저장 안되어있다면 저장하는 메소드
            goMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //경로 저장 절대 경로
                    final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    //파일 저장
                    final File directory = new File(strSDpath + "/Nightletter");
                    //만약 경로폴더가 없다면 만드세요
                    if(!directory.exists()){
                        directory.mkdir();
                    }

                    final String userNameStr = userName.getText().toString().trim();
                    final int nameLength = userNameStr.length();
                    //대화상자 참조 사이트 https://deumdroid.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%8A%A4%ED%8A%9C%EB%94%94%EC%98%A4-%EB%8C%80%ED%99%94%EC%83%81%EC%9E%90dialog-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
                    AlertDialog.Builder nameOkay = new AlertDialog.Builder(MainActivity.this);
                    nameOkay.setTitle("밤편지");
                    nameOkay.setMessage(userNameStr + " 님이 맞습니까?");
                    nameOkay.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        //대화상자 확인 눌렀을시 이벤트
                        public void onClick(DialogInterface dialog, int which) {
                            if (nameLength <= 1) {
                                Toast.makeText(getApplicationContext(), nameLength + " 글자는 너무 짧아요. 다시 입력해보세요.", Toast.LENGTH_SHORT).show();
                            } else {

                                SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
                                SharedPreferences.Editor editor = test.edit();
                                editor.putString("namedata", userNameStr); //namedata라는 key값으로 이름 데이터를 저장한다.
                                editor.commit(); //저장 완료
                                //토스트 메시지
                                Toast.makeText(getApplicationContext(), userNameStr + "님 이름이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                    nameOkay.show();
                }
            });
            imf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder imfalert = new AlertDialog.Builder(MainActivity.this);
                    imfalert.setTitle("밤편지 어플소개");
                    imfalert.setMessage(R.string.app_story);
                    imfalert.show();

                }
            });
        }
        else { // 저장이 되어있군요 너는 다음 레이아웃으로 가게될것이야!!!
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        }
    }
}
