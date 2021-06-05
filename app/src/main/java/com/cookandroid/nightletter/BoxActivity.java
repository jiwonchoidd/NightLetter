package com.cookandroid.nightletter;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class BoxActivity extends AppCompatActivity {


    //뒤로가기 눌렀을때 메뉴로 이동
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
    ListView listView1;
    //itemdata 클래스에 선언한 변수형태
    ArrayList<ItemData> datai = new ArrayList<ItemData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);
        listView1 = (ListView) findViewById(R.id.listview1);
        Button refresh = (Button) findViewById(R.id.refresh);

        //상단 제목부분
        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String nameData = test.getString("namedata", "저장안됨");
        TextView hellomailbox = (TextView) findViewById(R.id.hellomailbox);
        hellomailbox.setText(nameData + "님의 사서함 ");

        //어뎁터 적용
        final ItemDataAdapter adapter = new ItemDataAdapter(getLayoutInflater(), datai);
        listView1.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //경로 저장 절대 경로
        String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //파일 저장
        File directory = new File(strSDpath + "/Nightletter/");

        File[] files = directory.listFiles();
        // Arraylist는 다양한 변수 list 는 한가지만, 인터페이스
        List<String> filesNameList = new ArrayList<>();
        List<String> filesContextList = new ArrayList<>();
        List<Integer> filesStar = new ArrayList<>();

        //파일 이름에서 별점만 숫자 뺴고기
        for (int i = 0; i < files.length; i++) {
            String a = files[i].getName();
            int b = Integer.parseInt(a.substring(0, 1));
            filesStar.add(b);
        }
        // 파일 제목 저장 포문
        for (int i = 0; i < files.length; i++) {
            filesNameList.add(files[i].getName());
        }

        //이것은 읽기 권한을 준것
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.READ_EXTERNAL_STORAGE}, MODE_PRIVATE);

        try {
            // 파일 제목을 알았으니까 대략적인 내용 저장 포문 와 !!
            for (int i = 0; i < files.length; i++) {
                FileInputStream inFs = new FileInputStream("/sdcard/Nightletter/" + filesNameList.get(i));
                byte[] txt = new byte[inFs.available()];
                inFs.read(txt);
                filesContextList.add(new String(txt));
                inFs.close();
            }
        } catch (IOException e) {

        }

        // 아이템에 추가하는 포문
        for (int i = 0; i < files.length; i++) {
            String temp1 = filesNameList.get(i);
            String temp2 = filesContextList.get(i);
            //https://coding-factory.tistory.com/128 replace 참조
            datai.add(new ItemData(temp1.replace(".txt", "").substring(2)
                    , temp2.substring(0, 9), filesStar.get(i)));

            //날짜 별 정렬
            Collections.sort(datai, new Comparator<ItemData>() {
                @Override
                public int compare(ItemData o1, ItemData o2) {
                    Date newDate1 = null;
                    Date newDate2 = null;
                    DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
                    try {
                        newDate1 = dateFormat.parse(o1.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        newDate2 = dateFormat.parse(o2.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return newDate1.compareTo(newDate2);
                }
            });
            adapter.notifyDataSetChanged(); //변경됬음을 알려줌
        }

        //새로고침 버튼 누를시에 어뎁터 리프레쉬
        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                listView1.invalidate();
                //강제 화면 다시 불러오기 인텐트 껏다가 다시 불러오기
                Intent intent=getIntent();
                finish();
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"새로고침 완료!",Toast.LENGTH_SHORT).show();
            }
        });
        //리스트 아이템 누르면 다음 인텐트로 넘어감
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), readActivity.class);
                intent.putExtra("1", datai.get(position).getDate());
                intent.putExtra("3", datai.get(position).getRating());
                startActivity(intent);
            }
        });



    }


    }