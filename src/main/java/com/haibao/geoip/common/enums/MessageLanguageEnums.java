package com.haibao.geoip.common.enums;

/**
 * 语言 枚举
 */
public enum MessageLanguageEnums {

    ZH_CN("zh","CN","简体中文"),
    EN_US("en","US","英文"),
            ;

    private String language;
    private String countryCode;
    private String descs;

    MessageLanguageEnums(String language, String countryCode, String descs) {
        this.language = language;
        this.countryCode = countryCode;
        this.descs = descs;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }
}
