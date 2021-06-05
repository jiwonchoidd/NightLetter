package com.cookandroid.nightletter;

// 정보를 저장하기 위한 클래스
public class ItemData {
    String date;    //날짜 저장
    String content;   //내용 저장
    int rating;      //레이팅 저장

    public ItemData(String date, String content, int rating) {
        // 생성자로 정보 받아서 보낸다
        this.date= date;
        this.content=content;
        this.rating=rating;
    }
    public String getDate() {
        return date;
    }
    public String getContent() {
        return content;
    }
    public int getRating() {
        return rating;
    }


}
