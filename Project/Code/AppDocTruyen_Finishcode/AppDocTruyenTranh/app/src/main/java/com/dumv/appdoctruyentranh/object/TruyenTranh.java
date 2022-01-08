package com.dumv.appdoctruyentranh.object;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TruyenTranh implements Serializable {
    private String tenTruyen,tenChap,LinkAnh,id;

    /*
    {
    "tenTruyen":"",
    "tenChap":"",
    "linkAnh":""
    }
     */
    public TruyenTranh(){

    }
    public TruyenTranh(JSONObject o) throws JSONException {
        id = o.getString("id");
        tenTruyen = o.getString("tenTruyen");
        tenChap = o.getString("tenChap");
        LinkAnh = o.getString("LinkAnh");
    }

    public TruyenTranh(String tenTruyen, String tenChap, String linkAnh, String id) {
        this.tenTruyen = tenTruyen;
        this.tenChap = tenChap;
        LinkAnh = linkAnh;
        this.id = id;
    }

    public TruyenTranh(String tenTruyen, String tenChap, String linkAnh) {
        this.tenTruyen = tenTruyen;
        this.tenChap = tenChap;
        LinkAnh = linkAnh;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTenChap() {
        return tenChap;
    }

    public void setTenChap(String tenChap) {
        this.tenChap = tenChap;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}