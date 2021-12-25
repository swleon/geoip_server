package com.haibao.geoip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan({"com.haibao.geoip.mapper"})
@SpringBootApplication
public class GeoipServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeoipServerApplication.class, args);
    }

}
