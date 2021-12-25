package com.haibao.geoip.common.enums;

/**
 * 区域级别
 */
public enum AreaLevelEnums {

    continent(-1,"大洲"),
    COUNTRY(0,"国家"),
    PROVINCE(2,"省/州"),
    CITY(4,"城市"),
    ;

    private int level;
    private String descs;

    AreaLevelEnums(int level, String descs) {
        this.level = level;
        this.descs = descs;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }
}
