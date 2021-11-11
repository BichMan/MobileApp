package com.example.appdoctruyentranh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

import adapter.TruyenTranhAdapter;
import object.TruyenTranh;

public class MainActivity extends AppCompatActivity {
    GridView gdvDSTruyen;
    TruyenTranhAdapter adapter;
    ArrayList<TruyenTranh> truyenTranhArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        anhXa();
        setUp();
        setClick();
    }
    private void init(){
        truyenTranhArrayList = new ArrayList<>();
        //truyền data vào list
        truyenTranhArrayList.add(new TruyenTranh("Stalker x Stalker","Chapter 38","http://st.imageinstant.net/data/comics/154/stalker-x-stalker.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Change (JinYuan)","Chapter 101","http://st.imageinstant.net/data/comics/225/change-jinyuan.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Quyển sổ báo thù","Chapter 38","http://st.imageinstant.net/data/comics/160/quyen-so-bao-thu.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Real Account II","Chapter 165","http://st.imageinstant.net/data/comics/189/real-account-ii.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Stalker x Stalker","Chapter 38","http://st.imageinstant.net/data/comics/154/stalker-x-stalker.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Change (JinYuan)","Chapter 101","http://st.imageinstant.net/data/comics/225/change-jinyuan.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Quyển sổ báo thù","Chapter 38","http://st.imageinstant.net/data/comics/160/quyen-so-bao-thu.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Real Account II","Chapter 165","http://st.imageinstant.net/data/comics/189/real-account-ii.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Stalker x Stalker","Chapter 38","http://st.imageinstant.net/data/comics/154/stalker-x-stalker.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Change (JinYuan)","Chapter 101","http://st.imageinstant.net/data/comics/225/change-jinyuan.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Quyển sổ báo thù","Chapter 38","http://st.imageinstant.net/data/comics/160/quyen-so-bao-thu.jpg"));
        truyenTranhArrayList.add(new TruyenTranh("Real Account II","Chapter 165","http://st.imageinstant.net/data/comics/189/real-account-ii.jpg"));
        adapter = new TruyenTranhAdapter(this,0,truyenTranhArrayList);
    }
    private void anhXa(){
        gdvDSTruyen = findViewById(R.id.gdvDSTruyen);
    }
    private void setUp(){
        gdvDSTruyen.setAdapter(adapter);
    }
    private void setClick(){}
}