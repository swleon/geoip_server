package com.haibao.geoip.service;

import com.haibao.geoip.domain.model.GeoArea;
import com.haibao.geoip.domain.model.GeoCountry;
import com.haibao.geoip.domain.vo.GeoCountryVO;
import com.haibao.geoip.domain.vo.GeoParm;
import com.haibao.geoip.domain.vo.GeoProvinceVO;
import com.haibao.geoip.domain.vo.IpInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * geo db service
 */
public interface GeoService {

    List<GeoCountryVO> getAllCountryInfo(String lang, String search);

    ResponseEntity sync(String lang);

    List<GeoCountry> getCountryList(GeoCountry geoCountryQuery);

    List<GeoArea> getAreaList(GeoArea geoAreaQuery);

    List<GeoArea> matchAreaList(GeoArea geoCityQuery);

    ResponseEntity<IpInfo> getIpInfo(String ip);

    ResponseEntity<List<GeoProvinceVO>> getGeoProvinceByPgeonameid(String lang, Long geonameId);

    GeoCountry getCountry(GeoCountry geoCountryQuery);

    String getContinentNameByCountryName(String region);

    String cleanCountryName(String region);
    String cleanContinent(String region);

    List<GeoCountry> matchCountryList(String region);

    GeoParm matchRegoin(String areaStr);
}
