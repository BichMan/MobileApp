package com.dumv.appdoctruyentranh.model;

public class ResponseBodyDTO {
    private boolean success;
    private String messeger;

    public ResponseBodyDTO() {
    }

    public ResponseBodyDTO(boolean success, String messeger) {
        this.success = success;
        this.messeger = messeger;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMesseger() {
        return messeger;
    }

    public void setMesseger(String messeger) {
        this.messeger = messeger;
    }
}
