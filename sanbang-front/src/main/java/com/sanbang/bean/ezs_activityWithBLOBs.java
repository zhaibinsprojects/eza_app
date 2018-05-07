package com.sanbang.bean;

public class ezs_activityWithBLOBs extends ezs_activity {
    private String ac_describe;

    private String ac_rule;

    private String ac_seo_describe;

    public String getAc_describe() {
        return ac_describe;
    }

    public void setAc_describe(String ac_describe) {
        this.ac_describe = ac_describe == null ? null : ac_describe.trim();
    }

    public String getAc_rule() {
        return ac_rule;
    }

    public void setAc_rule(String ac_rule) {
        this.ac_rule = ac_rule == null ? null : ac_rule.trim();
    }

    public String getAc_seo_describe() {
        return ac_seo_describe;
    }

    public void setAc_seo_describe(String ac_seo_describe) {
        this.ac_seo_describe = ac_seo_describe == null ? null : ac_seo_describe.trim();
    }
}