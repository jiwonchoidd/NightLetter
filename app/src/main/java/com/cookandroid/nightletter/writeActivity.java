package com.cookandroid.nightletter;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class writeActivity extends AppCompatActivity {

    TextView name;
    RatingBar ratingBar;
    Button showDatePicker;
    Button save;
    EditText editText1;
    Button photo;
    Button btnWrite;
    DatePicker datePicker;
    String fileName;
    RelativeLayout ll;
    InputMethodManager imm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.READ_EXTERNAL_STORAGE}, MODE_PRIVATE);

        showDatePicker = (Button) findViewById(R.id.showDatePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        editText1 = (EditText) findViewById(R.id.EditText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        save = (Button) findViewById(R.id.save);
        photo = (Button) findViewById(R.id.photo);
        name = (TextView) findViewById(R.id.name);
        ll = (RelativeLayout) findViewById(R.id.ll);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        SharedPreferences test2 = getSharedPreferences("test", MODE_PRIVATE);
        String name1 = test2.getString("namedata", "이름입력안함");
        name.setText(name1 + "님의 일기 작성하기");

        photo = (Button) findViewById(R.id.photo);
        Intent intent = getIntent();

        final String date = intent.getStringExtra("date");
        final int rating = intent.getIntExtra("rating", 0);
        int intentTry = intent.getIntExtra("try", 0);

        //인텐트
        if (intentTry == 1) {
            Toast.makeText(getApplicationContext(),date+" 날짜 입력하세요.",Toast.LENGTH_SHORT).show();
            ratingBar.setRating(rating);
            Calendar pickedDated= Calendar.getInstance();
            ratingBar.setVisibility(View.INVISIBLE);
            name.setText("해당 날짜를 입력해야 수정이 됩니다.");
            String date1= date;
            String[] dateArray=date1.split("_");
            List<String> dd = new ArrayList<>();
            for(int i=0; i<dateArray.length;i++){
                dd.add(dateArray[i]);
            }
            String one =dd.get(0);
            String two =dd.get(1);
            String three =dd.get(2);
            int a=Integer.parseInt(one);
            int b=Integer.parseInt(two);
            int c=Integer.parseInt(three);
            pickedDated.set(a,b+1,c);

        }


                showDatePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        editText1.setVisibility(v.GONE);
                        name.setVisibility(v.GONE);
                        ratingBar.setVisibility(v.GONE);
                        save.setVisibility(v.GONE);
                        photo.setVisibility(v.GONE);
                        showDatePicker.setVisibility(v.GONE);


                        datePicker.setVisibility(v.VISIBLE);
                        btnWrite.setVisibility(v.VISIBLE);


                        Calendar cal = Calendar.getInstance();
                        int cYear = cal.get(Calendar.YEAR);
                        int cMonth = cal.get(Calendar.MONTH);
                        int cDay = cal.get(Calendar.DAY_OF_MONTH);

//datepicker 위젯의 초기값을 설정하고 데이트 피커의 값 변경에 따른 동작을 처리 할 리스너 등록
                        //monthOfYear 0부터 시작하기 때문에 +1 해줘야함
                        datePicker.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                fileName = year + "_" + (monthOfYear + 1) + "_" + dayOfMonth + ".txt";
                            }


                        });
                    }


                });

                btnWrite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText1.setVisibility(View.VISIBLE);
                        name.setVisibility(View.VISIBLE);
                        ratingBar.setVisibility(View.VISIBLE);
                        save.setVisibility(View.VISIBLE);
                        photo.setVisibility(View.VISIBLE);
                        showDatePicker.setVisibility(View.VISIBLE);


                        datePicker.setVisibility(View.GONE);
                        btnWrite.setVisibility(View.GONE);

                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (int) ratingBar.getRating();
                        String f = fileName;
                        String text = editText1.getText().toString();
                        String result = text;
                        int textlength = text.length();
                        if (i < 1) {
                            Toast.makeText(getApplicationContext(), "별점을 부여해 주세여", Toast.LENGTH_SHORT).show();
                        } else if (f == null) {
                            Toast.makeText(getApplicationContext(), "날짜를선택해 주세요", Toast.LENGTH_SHORT).show();
                        } else if (textlength < 10) {
                            Toast.makeText(getApplicationContext(), "10글자  이상 작성해 주세요!", Toast.LENGTH_SHORT).show();
                        } else {
                            String filename2 = i + "_" + fileName;
                            Toast.makeText(getApplicationContext(), filename2, Toast.LENGTH_LONG).show();


                            String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
                            File file = new File(strSDpath + "/Nightletter/" + filename2);
                            try {
                                FileOutputStream fos = new FileOutputStream(file);
                                fos.write(result.getBytes());
                                fos.close();
                                Intent intent = new Intent(getApplicationContext(), BoxActivity.class);
                                startActivity(intent);
                            } catch (Exception e) {

                            }


                        }
                    }
                });

            ll.setOnClickListener(myClickListener);
        }

        @SuppressLint("MissingSuperCall")
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    try {
                        InputStream in = getContentResolver().openInputStream(data.getData());
                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

//레이아웃 터치 시 키보드 아래로 내리기 https://sharp57dev.tistory.com/15
        View.OnClickListener myClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                switch (v.getId()) {
                    case R.id.ll:
                        break;
                }
            }
        };
        private void hideKeyBoard ()
        {
            imm.hideSoftInputFromWindow(editText1.getWindowToken(), 0);
        }
    }






