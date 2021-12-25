package com.haibao.geoip.common.base;

public class Constants {


    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 删除标识 0-正常 1-删除
     */
    public static final Integer IS_DELETED_NO = 0;
    public static final Integer IS_DELETED_YES = 1;

    /**
     * 状态标识
     */
    public static final String STATUS_TEST = "0";
    public static final String STATUS_ONLINE = "1";

    /**
     * 必须 标识 1-必须 0-非必要
     * */
    public static final String IS_REQUIRED_YES = "1";
    public static final String IS_REQUIRED_NO = "0";
    /**
     * 操作方式
     */
    public static final String OPERATION_METHOD_SAVE = "save";
    public static final String OPERATION_METHOD_UPDATE = "update";

    public static final String DEFAULT_IP = "0:0:0:0:0:0:0:1";

    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String HTTPS_HTTP = "https,http";
}
