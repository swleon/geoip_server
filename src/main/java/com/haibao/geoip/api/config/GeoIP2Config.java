package com.haibao.geoip.api.config;

import com.maxmind.geoip2.DatabaseReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * https://dev.maxmind.com/geoip/geolocate-an-ip/databases
 */
public class GeoIP2Config {

    // GeoIP2 离线库文件名
    private static final String geoip2DB = "GeoLite2-City.mmdb";

    public static DatabaseReader reader;

    static {
        InputStream database;
        try {
            // 读取 GeoIP2 离线库，jar 包中的资源文件无法以 File 方式读取，需要用 InputStream 流式读取
            database = GeoIP2Config.class.getClassLoader().getResourceAsStream("geoip2/"+geoip2DB);
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
