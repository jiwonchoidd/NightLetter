package com.cookandroid.nightletter;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.FileInputStream;
import java.io.IOException;

public class readActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);


        SharedPreferences test3 = getSharedPreferences("test", MODE_PRIVATE);
        String namedata3 = test3.getString("namedata", "저장된 이름이 없음");
        TextView name2 = (TextView) findViewById(R.id.name2);
        name2.setText(namedata3 + "님의 일기 읽기");


        Intent intent = getIntent();
        final String date = intent.getStringExtra("1");
        final int rating = intent.getIntExtra("3", 1);
        TextView showDialog = (TextView) findViewById(R.id.showDialog);
        RatingBar rating1 = (RatingBar) findViewById(R.id.rating1);
        Button back = (Button) findViewById(R.id.back);
        Button reset = (Button) findViewById(R.id.reset);
        //파일 읽어오기
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.READ_EXTERNAL_STORAGE}, MODE_PRIVATE);
        try {
            // 파일 제목을 알았으니까 대략적인 내용 저장 포문 와 !!
            FileInputStream inFs = new FileInputStream("/sdcard/Nightletter/" + rating + "_" + date + ".txt");
            byte[] txt = new byte[inFs.available()];
            inFs.read(txt);
            String context = new String(txt);
            showDialog.setText(date + "\n\n" + context);
            inFs.close();
        } catch (IOException e) {

        }

        rating1.setRating(rating);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoxActivity.class);
                startActivity(intent);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), writeActivity.class);
                intent.putExtra("rating", rating);
                intent.putExtra("date", date);
                intent.putExtra("try",1);
                startActivity(intent);

            }
        });
    }
}
