package com.haibao.geoip.api;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.haibao.geoip.common.utils.GsonUtils;
import com.haibao.geoip.api.config.GeoIP2Config;
import com.haibao.geoip.domain.vo.IpInfo;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

/**
 *
 * ip 搜索 离线库
 */
@Component
public class IPSearcherApi {



    /*********************ip2region**********************/
    private static DbSearcher _searcher;
    {
        ClassPathResource classPathResource = new ClassPathResource("ip2region/data/ip2region.db");
        try {
            _searcher = new DbSearcher(new DbConfig(), toByteArray(classPathResource.getInputStream()));
        } catch (DbMakerConfigException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataBlock get(String ip){
        try {
            return _searcher.memorySearch(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DataBlock get(Long ip){
        try {
            return _searcher.memorySearch(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

     byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /*********************GeoIP2*********************/
    /**
     * IP 解析
     *
     * @param ip IP 地址
     * @return IpInfo
     */
    public IpInfo getIpInfo(String ip) {

        IpInfo info = new IpInfo();
        info.setIp(ip);

        try {
            // 根据 IP 地址查询结果
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = GeoIP2Config.reader.city(ipAddress);
            // 从查询结果中提取信息（国家，省份，城市，邮编，定位）
            Country country = response.getCountry();
            Subdivision subdivision = response.getMostSpecificSubdivision();
            City city = response.getCity();
            Postal postal = response.getPostal();
            Location location = response.getLocation();
            // 获取信息的中文名称 zh-CN
            info.setCountryName(country.getNames().get("en"));
            info.setCountryCode(country.getIsoCode());
            info.setCountryGeonameId(country.getGeoNameId());
            info.setProvinceName(subdivision.getNames().get("en"));
            info.setProvinceCode(subdivision.getIsoCode());
            info.setProvinceGeonameId(subdivision.getGeoNameId());
            info.setCityName(city.getNames().get("en"));
            info.setCityGeonameId(city.getGeoNameId());
            info.setPostalCode(postal.getCode());
            info.setLongitude(location.getLongitude());
            info.setLatitude(location.getLatitude());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }

        return info;
    }

    /**
     * 提取用户
     * @param info
     * @return
     */
    public String extractIpAreaByIpInfo(IpInfo info) {
        if(ObjectUtil.isEmpty(info)){
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if(StrUtil.isNotEmpty(info.getCountryName())){
            sb.append(info.getCountryName());
        }
        if(StrUtil.isNotEmpty(info.getProvinceName())){
            if(StrUtil.isNotEmpty(sb.toString())){
                sb.append(" ");
            }
            sb.append(info.getProvinceName());
        }

        if(StrUtil.isNotEmpty(info.getCityName())
          && !info.getCityName().equals(info.getProvinceName())){
            if(StrUtil.isNotEmpty(sb.toString())){
                sb.append(" ");
            }
            sb.append(info.getCityName());
        }

        return sb.toString();
    }


    public static void main(String[] args) {
        IPSearcherApi ipSearcherApi = new IPSearcherApi();
//        IpInfo info = ipSearcherApi.getIpInfo("128.199.8.242");
//        IpInfo info = ipSearcherApi.getIpInfo("159.75.51.84");
        IpInfo info = ipSearcherApi.getIpInfo("115.199.178.187");
//        IpInfo info = ipSearcherApi.getIpInfo("158.101.144.173");
        System.out.println(GsonUtils.gsonString(info));
        System.out.println(ipSearcherApi.extractIpAreaByIpInfo(info));
    }


//    public static void main(String[] args) {
//        IPSearcherApi ipSearcherApi = new IPSearcherApi();
//        System.out.println(ipSearcherApi.get("128.199.8.242"));
//        System.out.println(ipSearcherApi.get("159.75.51.84"));
//        System.out.println(ipSearcherApi.get("115.199.178.187"));
//        DataBlock dataBlock = ipSearcherApi.get("158.101.144.173");
//        System.out.println(GsonUtils.gsonString(dataBlock));
//        System.out.println(dataBlock.getRegion());
//        String region = dataBlock.getRegion();
//        String regions[] = region.split("\\|");
//        System.out.println(regions.length);
//        System.out.println(regions[0]);
//        System.out.println(regions[3]);
//
////        0     |中国|0  |浙江|杭州|电信|  149084
////        _城市Id|国家|区域|省份|城市|ISP_
//    }
}
