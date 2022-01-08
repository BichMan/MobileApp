package com.dumv.appdoctruyentranh.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YeuThich {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("tenTruyen")
    @Expose
    private String tenTruyen;
    @SerializedName("tenChap")
    @Expose
    private String tenChap;
    @SerializedName("LinkAnh")
    @Expose
    private String linkAnh;
    @SerializedName("id_truyen")
    @Expose
    private String idTruyen;
    @SerializedName("id_user")
    @Expose
    private String idUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
