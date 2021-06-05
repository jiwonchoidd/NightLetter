package com.cookandroid.nightletter;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import java.io.File;
import java.util.ArrayList;


public class ItemDataAdapter extends BaseAdapter {

    ArrayList<ItemData> datai;
    LayoutInflater inflater;

    public ItemDataAdapter(LayoutInflater inflater, ArrayList<ItemData> datai) {
        this.datai = datai;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datai.size(); //datas의 개수를 리턴
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datai.get(position);//datas의 특정 인덱스 위치 객체 리턴.
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.box_listitem, null);
        }
        //convertView. findViewById()
        final TextView text_time = (TextView) convertView.findViewById(R.id.box_time);
        TextView text_content = (TextView) convertView.findViewById(R.id.box_content);
        final RatingBar rating1 = (RatingBar) convertView.findViewById(R.id.box_ratingbar);
        CheckBox box_delete = (CheckBox) convertView.findViewById(R.id.box_delete);

        box_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder delete = new AlertDialog.Builder(v.getContext());
                delete.setTitle("밤편지");
                delete.setMessage(text_time.getText()+"일자 일기 삭제 하시겠습니까? (삭제한 뒤 새로고침하세요.)");
                delete.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    //대화상자 확인 눌렀을시 이벤트
                    public void onClick(DialogInterface dialog, int which) {
                        int i= (int) rating1.getRating();
                        File file = new File("/sdcard/Nightletter/"+i+"_"+text_time.getText()+".txt");
                        ItemDataAdapter.super.notifyDataSetChanged();
                        file.delete();
                        notifyDataSetChanged();
                    }
                });
                delete.show();
            }});

        text_time.setText(datai.get(position).getDate());
        text_content.setText(datai.get(position).getContent());
        rating1.setRating(datai.get(position).getRating());

            // 참조 사이트 https://kitesoft.tistory.com/72
        return convertView;
    }

    }




