package com.haibao.geoip.controller;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haibao.geoip.common.enums.ContinentEnums;
import com.haibao.geoip.domain.vo.ContinentVO;
import com.haibao.geoip.domain.vo.GeoCountryVO;
import com.haibao.geoip.domain.vo.GeoProvinceVO;
import com.haibao.geoip.domain.vo.IpInfo;
import com.haibao.geoip.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * geo
 * @author wuque
 */
@Validated
@RestController
@RequestMapping("/api/geo")
//@CrossOrigin(originPatterns = {"*.xxx.com*", "*.xxx.com*"}, maxAge = 3600, allowCredentials = "true")
public class GeoController
{

    @Autowired
    private GeoService geoService;


    @GetMapping("/ip")
    public ResponseEntity<IpInfo> ip(@RequestParam(name="ip",required = false,defaultValue = "") String ip)
    {
        return geoService.getIpInfo(ip);
    }

    /**
     * 获取所有大洲
     * @return
     */
    @GetMapping("/continent")
    public ResponseEntity<List<ContinentVO>> continent()
    {
        return ResponseEntity.ok(ContinentEnums.toList());
    }

    /**
     *
     * @param  lang : ISO-639-1 language code (en,de,fr,it,es,...) (default = english)
     * @return
     */
    @GetMapping("/country")
    public ResponseEntity<List<GeoCountryVO>> country(@RequestParam(name = "lang",required = false,defaultValue = "en") String lang,
                                                      @RequestParam(name = "search",required = false,defaultValue = "") String search)
    {
        List<GeoCountryVO> list = geoService.getAllCountryInfo(lang,search);
        return ResponseEntity.ok(list);
    }

    /**
     *
     * @param lang
     * @return
     */
    @GetMapping("/countryByGroup")
    public ResponseEntity<Map<String,List<GeoCountryVO>>> countryByGroup(
            @RequestParam(name = "lang",required = false,defaultValue = "en") String lang)
    {
        List<GeoCountryVO> list = geoService.getAllCountryInfo(lang,"");

        Map<String,List<GeoCountryVO>> map = Maps.newHashMap();

        list.stream().forEach(geoCountryVO -> {
            if(StrUtil.isNotEmpty(geoCountryVO.getCountryName())){
                String firstkey = geoCountryVO.getCountryName().substring(0, 1).toUpperCase();
                List<GeoCountryVO> list1;
                if(map.containsKey(firstkey)){
                    list1 = map.get(firstkey);
                }else{
                    list1 = Lists.newArrayList();
                }
                list1.add(geoCountryVO);
                map.put(firstkey,list1);
            }
        });

        return ResponseEntity.ok(map);
    }


    @GetMapping("/children")
    public ResponseEntity<List<GeoProvinceVO>> children(@RequestParam(name = "lang",required = false,defaultValue = "en") String lang,
                                                        @RequestParam(name = "geonameId",required = true) Long geonameId)
    {
        return geoService.getGeoProvinceByPgeonameid(lang,geonameId);
    }


    /**
     * 行政地区信息 同步外网geo依赖
     * @param lang
     * @return
     */
    @GetMapping("/sync")
    public ResponseEntity sync(
            @RequestParam(name = "lang",required = false,defaultValue = "")
                    String lang)
    {
        return geoService.sync(lang);
    }

}
