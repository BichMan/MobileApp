package com.dumv.appdoctruyentranh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dumv.appdoctruyentranh.adapter.ChapTruyenAdapter;
import com.dumv.appdoctruyentranh.adapter.TruyenTranhAdapter;
import com.dumv.appdoctruyentranh.api.ApiLayAnh;
import com.dumv.appdoctruyentranh.api.ApiLayTruyen;
import com.dumv.appdoctruyentranh.interfaces.LayAnhVe;
import com.dumv.appdoctruyentranh.object.ChapTruyen;
import com.dumv.appdoctruyentranh.object.TruyenTranh;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DocTruyenActivity extends AppCompatActivity implements LayAnhVe {
ImageView imgAnh;
ArrayList<String> arrUrlAnh;
int soTrang,soTrangDangDoc;
TextView txvSoTrang;
String idChap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);
        init();
        anhXa();
        setUp();
        setClik();
        new ApiLayAnh(this,idChap).execute();
    }

    private void init() {

        Bundle b = getIntent().getBundleExtra("data");
        idChap= b.getString("idChap");
        /*
        arrUrlAnh = new ArrayList<>();
        arrUrlAnh.add("http://cdn5.truyentranh8.net/u2/lacthientt8/25538-tt8-rental-girls/1-/0000-rental-credit.jpg");
        arrUrlAnh.add("http://cdn5.truyentranh8.net/u2/lacthientt8/25538-tt8-rental-girls/1-/001-copy.jpg");
        arrUrlAnh.add("http://cdn5.truyentranh8.net/u2/lacthientt8/25538-tt8-rental-girls/1-/002-copy.jpg");
        arrUrlAnh.add("http://cdn5.truyentranh8.net/u2/lacthientt8/25538-tt8-rental-girls/1-/003-copy.jpg");
        arrUrlAnh.add("http://cdn5.truyentranh8.net/u2/lacthientt8/25538-tt8-rental-girls/1-/004-copy.jpg");
        arrUrlAnh.add("http://cdn5.truyentranh8.net/u2/lacthientt8/25538-tt8-rental-girls/1-/005-copy.jpg");
        arrUrlAnh.add("http://cdn5.truyentranh8.net/u2/lacthientt8/25538-tt8-rental-girls/1-/006-copy.jpg");
        arrUrlAnh.add("http://cdn5.truyentranh8.net/u2/lacthientt8/25538-tt8-rental-girls/1-/007-copy.jpg");
        arrUrlAnh.add("http://cdn5.truyentranh8.net/u2/lacthientt8/25538-tt8-rental-girls/1-/008-copy.jpg");
        arrUrlAnh.add("http://cdn5.truyentranh8.net/u2/lacthientt8/25538-tt8-rental-girls/1-/009-copy.jpg");
        soTrangDangDoc = 1;
        soTrang=arrUrlAnh.size();
        */
    }

    private void anhXa() {
        txvSoTrang =findViewById(R.id.txvSoTrang);
        imgAnh =findViewById(R.id.imgAnh);
    }

    private void setUp(){
        //docTheoTrang(0);
    }

    private void setClik() {

    }

    public void right(View view) {
        docTheoTrang(1);
    }

    public void left(View view) {
        docTheoTrang(-1);
    }

    private void docTheoTrang(int i){
        soTrangDangDoc= soTrangDangDoc+i;
        if(soTrangDangDoc==0){
            soTrangDangDoc=1;
        }
        if(soTrangDangDoc>soTrang){
            soTrangDangDoc = soTrang;
        }
        txvSoTrang.setText(soTrangDangDoc+" / "+soTrang);
        Glide.with(this).load(arrUrlAnh.get(soTrangDangDoc-1)).into(imgAnh);
    }

    @Override
    public void batDau() {

    }

    @Override
    public void ketThuc(String data) {
        try {
            arrUrlAnh = new ArrayList<>();
            JSONArray array = new JSONArray(data);
            for(int i=0;i<array.length();i++){
                arrUrlAnh.add(array.getString(i));
            }
            soTrangDangDoc = 1;
            soTrang=arrUrlAnh.size();
            docTheoTrang(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void biLoi() {

    }
}
