package com.haibao.geoip.api;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import com.haibao.geoip.common.utils.GsonUtils;
import com.haibao.geoip.domain.vo.GeoChildrenInfoVO;
import com.haibao.geoip.domain.vo.GeoCountryVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: wuque
 * @date: 2021/9/22 17:38
 * @description: Geo服务调用
 * https://www.geonames.org/export/ws-overview.html
 * username: happy666
 * password:1qaz@wsx
 */
@Component("geonamesComponent")
public class GeonamesApi {

    private final String serviceURI = "http://api.geonames.org";
    private final String username = "happy666";

    /**
     * 获取国家信息
     * Webservice Type : REST
     * Url : api.geonames.org/countryInfo?
     * Parameters : country (default = all countries)
     * lang : ISO-639-1 language code (en,de,fr,it,es,...) (default = english)
     * Result : Country information : Capital, Population, Area in square km, Bounding Box of mainland (excluding offshore islands)
     * Example : http://api.geonames.org/countryInfo?username=demo
     *
     * @param language (en,de,fr,it,es,...) (default = english)
     * @return
     */
    public List<GeoCountryVO> getCountryInfo(String language) {

        String url = serviceURI + "/countryInfoJSON?username=" + username;
        url = StrUtil.isNotEmpty(language) ? url + "&lang=" + language : url;
        String res = HttpUtil.get(url, 5000);
        if (StrUtil.isEmpty(res) || !res.contains("geonames")) {
            return Lists.newArrayList();
        }

        Map<String, List<Map>> listMap = GsonUtils.gsonToMaps(res);
        List<Map> list = listMap.getOrDefault("geonames", Lists.newArrayList());

        List<GeoCountryVO> geoCountryVOS = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            geoCountryVOS.add(GsonUtils.gsonToBean(GsonUtils.gsonString(map),GeoCountryVO.class));
        }
        return geoCountryVOS;
    }


    /**
     * 返回给定 geonameId 的子项（管理部门和人口稠密的地方）。
     * 子级是其他行政区划内的行政区划，例如州 (ADM1) 中的县 (ADM2) 或大陆上的国家/地区。
     * 树叶是人口稠密的地方，此服务不包括其他要素类，如景点、山脉等
     * @param geonameId
     * @param language
     * @return
     */
    public GeoChildrenInfoVO getChildren(Long geonameId, String language) {

        String url = serviceURI + "/childrenJSON?geonameId="+geonameId+"&username=" + username;
        url = StrUtil.isNotEmpty(language) ? url + "&lang=" + language : url;
        String res = HttpUtil.get(url, 5000);
        if (StrUtil.isEmpty(res) || !res.contains("geonames")) {
            return null;
        }

        return  GsonUtils.gsonToBean(res,GeoChildrenInfoVO.class);
    }

    /**
     * 获取中英 两种语言 全量国家
     *
     * {
     *             "continent":"AS",
     *             "capital":"Tokyo",
     *             "capitalCN":"东京",
     *             "languages":"ja",
     *             "geonameId":1861060,
     *             "south":24.255169441,
     *             "isoAlpha3":"JPN",
     *             "north":45.52295736,
     *             "fipsCode":"JA",
     *             "population":"126529100",
     *             "east":145.817458885,
     *             "isoNumeric":"392",
     *             "areaInSqKm":"377835.0",
     *             "countryCode":"JP",
     *             "west":122.933653061,
     *             "countryName":"Japan",
     *             "countryNameCN":"日本",
     *             "postalCodeFormat":"###-####",
     *             "continentName":"Asia",
     *             "continentNameCN":"亚洲",
     *             "currencyCode":"JPY"
     *         }
     *
     * @return
     */
    public List<GeoCountryVO> getAllCountryInfo() {

        List<GeoCountryVO> cnList = getCountryInfo("zh");
        List<GeoCountryVO> enList = getCountryInfo("en");
        List<GeoCountryVO> geoCountryVOS = enList.stream().map(geoCountryVO -> {
            cnList.forEach(geoCountryVO1 -> {
                if(geoCountryVO.getCountryCode().equals(geoCountryVO1.getCountryCode())){
                    geoCountryVO.setCountryNameCN(geoCountryVO1.getCountryName());
                    geoCountryVO.setContinentNameCN(geoCountryVO1.getContinentName());
                    geoCountryVO.setCapitalCN(geoCountryVO1.getCapital());
                }
            });
            return geoCountryVO;
        }).collect(Collectors.toList());


        return geoCountryVOS;
    }

    /**
     * 根据国家获取大洲
     * @param country
     * @return
     */
    public String getContinentNameByCountry(String country) {

        if(StrUtil.isEmpty(country)) {
            return "";
        }
            List<GeoCountryVO> geoCountryVOS = getAllCountryInfo();
            Set<String> continentnameSet = geoCountryVOS.stream().map(geoCountryVO -> {

                if (geoCountryVO.getCountryName().equals(country)) {
                    return geoCountryVO.getContinentName();
                }else if (geoCountryVO.getCountryNameCN().equals(country)) {
                    return geoCountryVO.getContinentName();
                }else if (geoCountryVO.getIsoAlpha3().equals(country)) {
                    return geoCountryVO.getContinentName();
                }else if(geoCountryVO.getCountryName().contains(country)){
                    return geoCountryVO.getContinentName();
                }
                return "";
            }).filter(str ->{
                return StrUtil.isNotEmpty(str);
            }).collect(Collectors.toSet());

            String continent = continentnameSet.iterator().hasNext() ? continentnameSet.iterator().next() : "";

            return continent;
    }

}
