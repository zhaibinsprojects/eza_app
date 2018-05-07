package com.sanbang.bean;

public class ezs_adv_posWithBLOBs extends ezs_adv_pos {
    private String ap_code;

    private String ap_content;

    public String getAp_code() {
        return ap_code;
    }

    public void setAp_code(String ap_code) {
        this.ap_code = ap_code == null ? null : ap_code.trim();
    }

    public String getAp_content() {
        return ap_content;
    }

    public void setAp_content(String ap_content) {
        this.ap_content = ap_content == null ? null : ap_content.trim();
    }
}