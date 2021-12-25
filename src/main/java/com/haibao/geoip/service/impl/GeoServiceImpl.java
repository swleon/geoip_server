package com.haibao.geoip.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.cglib.CglibUtil;
import com.google.common.collect.Maps;
import com.haibao.geoip.common.base.Constants;
import com.haibao.geoip.common.enums.AreaLevelEnums;
import com.haibao.geoip.common.enums.MessageLanguageEnums;
import com.haibao.geoip.common.utils.GsonUtils;
import com.haibao.geoip.common.utils.IpUtil;
import com.haibao.geoip.common.utils.ServletUtils;
import com.haibao.geoip.api.GeonamesApi;
import com.haibao.geoip.api.IPSearcherApi;
import com.haibao.geoip.domain.model.GeoArea;
import com.haibao.geoip.domain.model.GeoCountry;
import com.haibao.geoip.domain.vo.*;
import com.haibao.geoip.mapper.GeoAreaMapper;
import com.haibao.geoip.mapper.GeoCountryMapper;
import com.haibao.geoip.service.GeoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 *
 */
@Slf4j
@Service
public class GeoServiceImpl implements GeoService {

    @Resource
    private GeonamesApi geonamesApi;
    @Resource
    private IPSearcherApi ipSearcherApi;
    @Resource
    private GeoCountryMapper geoCountryMapper;
    @Resource
    private GeoAreaMapper geoAreaMapper;


    @Override
    public List<GeoCountryVO> getAllCountryInfo(String lang, String search) {
        lang = StrUtil.isEmpty(lang)? MessageLanguageEnums.EN_US.getLanguage():lang;
        GeoCountry geoCountry = new GeoCountry();
        geoCountry.setLocales(lang);
        geoCountry.setIsDeleted(Constants.IS_DELETED_NO);

        if(StrUtil.isNotEmpty(search)){
            Map parms = Maps.newHashMap();
            parms.put("search",search);
            geoCountry.setParms(parms);
        }

        List<GeoCountry>  geoCountries = geoCountryMapper.select(geoCountry);
        List<GeoCountryVO> geoCountryVOS = new ArrayList<>();
        geoCountries.forEach(geoCountry1 -> {
            geoCountryVOS.add(CglibUtil.copy(geoCountry1,GeoCountryVO.class));
        });
        return geoCountryVOS;
    }

    @Override
    public ResponseEntity sync(String lang) {
        lang = StrUtil.isEmpty(lang)? MessageLanguageEnums.EN_US.getLanguage() :lang;
        //async
        String finalLang = lang;
        CompletableFuture.supplyAsync(new Supplier<Integer>() {

            /**
             * bug
             * @return
             */
            @Override
            public Integer get() {
                List<GeoCountryVO> geoCountryVOS = geonamesApi.getCountryInfo(finalLang);
                upsert(geoCountryVOS, finalLang);

                //同步 省
                geoCountryVOS.forEach(geoCountryVO -> {
                    syncGeoArea(geoCountryVO.getGeonameId(), finalLang, AreaLevelEnums.PROVINCE.getLevel());
                });

                return geoCountryVOS.size();
            }
        });

        return ResponseEntity.ok("Asynchronous execution...");
    }

    @Override
    public List<GeoCountry> getCountryList(GeoCountry geoCountryQuery) {
        return geoCountryMapper.select(geoCountryQuery);
    }

    @Override
    public List<GeoArea> getAreaList(GeoArea geoAreaQuery) {
        if(StrUtil.isEmpty(geoAreaQuery.getLocales())){
            geoAreaQuery.setLocales(MessageLanguageEnums.EN_US.getLanguage());
        }
        return geoAreaMapper.select(geoAreaQuery);
    }

    @Override
    public List<GeoArea> matchAreaList(GeoArea geoCityQuery) {
        return geoAreaMapper.match(geoCityQuery);
    }

    @Override
    public ResponseEntity<IpInfo> getIpInfo(String ip) {
        if(StrUtil.isEmpty(ip)){
            ip = IpUtil.getRealIp(ServletUtils.getRequest());
            log.info(ip);
        }
        IpInfo ipInfo  = ipSearcherApi.getIpInfo(ip);
        log.info(GsonUtils.gsonString(ipInfo));
        return ResponseEntity.ok(ipInfo);
    }

    @Override
    public ResponseEntity<List<GeoProvinceVO>> getGeoProvinceByPgeonameid(String lang, Long geonameId) {
        if(StrUtil.isEmpty(lang)){
            lang = MessageLanguageEnums.EN_US.getLanguage();
        }
        GeoArea geoAreaQuery = new GeoArea();
        geoAreaQuery.setLocales(lang);
        geoAreaQuery.setPGeonameId(geonameId);
        List<GeoArea> list = getAreaList(geoAreaQuery);
        List<GeoProvinceVO> geoProvinceVOS = list.stream().map(geoArea -> {
            return CglibUtil.copy(geoArea,GeoProvinceVO.class);
        }).collect(Collectors.toList());

        String finalLang = lang;
        geoProvinceVOS.parallelStream().forEach(geoProvinceVO -> {
            GeoArea geoAreaQuery4 = new GeoArea();
            geoAreaQuery4.setLocales(finalLang);
            geoAreaQuery4.setPGeonameId(geoProvinceVO.getGeonameId());
            List<GeoArea> list4 = getAreaList(geoAreaQuery4);
            geoProvinceVO.setGeoChildrenInfoList(list4);
            geoProvinceVO.setTotalResultsCount((long)list4.size());
        });
        return ResponseEntity.ok(geoProvinceVOS);
    }

    @Override
    public GeoCountry getCountry(GeoCountry geoCountryQuery) {
        if(StrUtil.isEmpty(geoCountryQuery.getLocales())){
            geoCountryQuery.setLocales(MessageLanguageEnums.EN_US.getLanguage());
        }
       List<GeoCountry> geoCountries = getCountryList(geoCountryQuery);
       if(CollUtil.isEmpty(geoCountries)){
           return  null;
       }
       return geoCountries.get(0);
    }

    @Override
    public String getContinentNameByCountryName(String region) {
        GeoCountry geoCountryQuery = new GeoCountry();
        geoCountryQuery.setCountryName(region);
        GeoCountry geoCountry = getCountry(geoCountryQuery);
        if(ObjectUtil.isNotEmpty(geoCountry)){
           return geoCountry.getContinentName();
        }
        return null;
    }

    /**
     * 清洗 国家 字段
     *
     * @param region
     * @return
     */
    @Override
    public String cleanCountryName(String region) {
        List<GeoCountry> geoCountries = matchCountryList(region);
        if(CollUtil.isNotEmpty(geoCountries)){
            GeoCountry geoCountry = geoCountries.get(0);
            GeoCountry geoCountryQueryEn = new GeoCountry();
            geoCountryQueryEn.setGeonameId(geoCountry.getGeonameId());
            geoCountryQueryEn.setLocales(MessageLanguageEnums.EN_US.getLanguage());
            GeoCountry geoCountryEn = getCountry(geoCountryQueryEn);
            if(ObjectUtil.isNotEmpty(geoCountryEn)){
                return geoCountryEn.getCountryName();
            }
        }
        return null;
    }

    @Override
    public String cleanContinent(String region) {
        List<GeoCountry> geoCountries = matchCountryList(region);
        if(CollUtil.isNotEmpty(geoCountries)){
            GeoCountry geoCountry = geoCountries.get(0);
            GeoCountry geoCountryQueryEn = new GeoCountry();
            geoCountryQueryEn.setGeonameId(geoCountry.getGeonameId());
            geoCountryQueryEn.setLocales(MessageLanguageEnums.EN_US.getLanguage());
            GeoCountry geoCountryEn = getCountry(geoCountryQueryEn);
            if(ObjectUtil.isNotEmpty(geoCountryEn)){
                return geoCountryEn.getContinentName();
            }
        }
        return null;
    }

    @Override
    public List<GeoCountry> matchCountryList(String region) {
        Map map = Maps.newHashMap();
        map.put("search",region);
        GeoCountry geoCountryQuery = new GeoCountry();
        geoCountryQuery.setParms(map);
        return getCountryList(geoCountryQuery);
    }

    @Override
    public GeoParm matchRegoin(String region) {

        List<GeoCountry> geoCountries = matchCountryList(region);
        if(CollUtil.isNotEmpty(geoCountries)){
            GeoCountry geoCountry = geoCountries.get(0);
            GeoCountry geoCountryQueryEn = new GeoCountry();
            geoCountryQueryEn.setGeonameId(geoCountry.getGeonameId());
            geoCountryQueryEn.setLocales(MessageLanguageEnums.EN_US.getLanguage());
            GeoCountry geoCountryEn = getCountry(geoCountryQueryEn);
            if(ObjectUtil.isNotEmpty(geoCountryEn)){
                GeoParm geoParm = new GeoParm();
                geoParm.setGeotype(AreaLevelEnums.continent.getLevel());
                geoParm.setGeoname(geoCountryEn.getContinentName());
                geoParm.setGeonameId(geoCountryEn.getGeonameId());
                return geoParm;
            }
        }

        geoCountries = matchCountryList(region);
        if(CollUtil.isNotEmpty(geoCountries)){
            GeoCountry geoCountry = geoCountries.get(0);
            GeoCountry geoCountryQueryEn = new GeoCountry();
            geoCountryQueryEn.setGeonameId(geoCountry.getGeonameId());
            geoCountryQueryEn.setLocales(MessageLanguageEnums.EN_US.getLanguage());
            GeoCountry geoCountryEn = getCountry(geoCountryQueryEn);
            if(ObjectUtil.isNotEmpty(geoCountryEn)){
                GeoParm geoParm = new GeoParm();
                geoParm.setGeotype(AreaLevelEnums.COUNTRY.getLevel());
                geoParm.setGeoname(geoCountryEn.getCountryName());
                geoParm.setGeonameId(geoCountryEn.getGeonameId());
                return geoParm;
            }
        }

        GeoArea geoCityQuery = new GeoArea();
        geoCityQuery.setName(region);
        List<GeoArea> geoAreas = matchAreaList(geoCityQuery);
        if(CollUtil.isNotEmpty(geoAreas) ){
            GeoArea geoArea = geoAreas.get(0);
            GeoArea geoAreaQuery = new GeoArea();
            geoAreaQuery.setLocales(MessageLanguageEnums.EN_US.getLanguage());
            geoAreaQuery.setGeonameId(geoArea.getGeonameId());
            geoAreas = getAreaList(geoAreaQuery);
            if(CollUtil.isNotEmpty(geoAreas)){
                geoArea = geoAreas.get(0);
            }
            GeoParm geoParm = new GeoParm();
            geoParm.setGeotype(geoArea.getLevel());
            geoParm.setGeoname(geoArea.getName());
            geoParm.setGeonameId(geoArea.getGeonameId());
            return geoParm;
        }

        return null;
    }

    /**
     * 同步省 市
     * @param geonameId
     * @param lang
     * @param level
     * @return
     */
    private boolean syncGeoArea(Long geonameId, String lang, int level) {
        GeoChildrenInfoVO geoChildrenInfoVO = geonamesApi.getChildren(geonameId,lang);
        if(null != geoChildrenInfoVO && geoChildrenInfoVO.getTotalResultsCount() > 0){
            List<GeonamesVO> geonamesVOList =  geoChildrenInfoVO.getGeonames();
            upsert(geonamesVOList, level,geonameId,lang);

            if(level == AreaLevelEnums.PROVINCE.getLevel()){
                //同步城市
                geonamesVOList.stream().forEach(geonamesVO -> {
                    syncGeoArea(geonamesVO.getGeonameId(),lang,AreaLevelEnums.CITY.getLevel());
                });
            }
        }

        return true;
    }

    /**
     * update or insert geoArea
     * @param geonamesVOList
     * @param level 2-省  4-市级别
     * @param pGeonameId 父级别
     * @param lang
     */
    private boolean upsert(List<GeonamesVO> geonamesVOList, int level, Long pGeonameId, String lang) {

        if(CollUtil.isEmpty(geonamesVOList)){
            return false;
        }
        geonamesVOList.parallelStream().forEach(geonamesVO -> {

            GeoArea geoAreaQuery = new GeoArea();
            geoAreaQuery.setGeonameId(geonamesVO.getGeonameId());
            geoAreaQuery.setPGeonameId(pGeonameId);
            geoAreaQuery.setLocales(lang);
            geoAreaQuery.setLevel(level);
            geoAreaQuery.setIsDeleted(Constants.IS_DELETED_NO);
            List<GeoArea> geoAreas = geoAreaMapper.select(geoAreaQuery);

            GeoArea geoArea = CglibUtil.copy(geonamesVO,GeoArea.class);
            geoArea.setLevel(level);
            geoArea.setLocales(lang);
            geoArea.setPGeonameId(pGeonameId);
            geoArea.setAdmincodes1Iso31662(MapUtil.getStr(geonamesVO.getAdminCodes1(),"ISO3166_2"));
            geoArea.setUpdated((int)DateUtil.currentSeconds()/1000);

            if(geoAreas.size() > 0){
                geoAreaMapper.updateByGeonameIdAndLocalesSelective(geoArea);
                log.info("update area : {}",GsonUtils.gsonString(geoArea));
            }else{
                geoArea.setCreated((int)DateUtil.currentSeconds()/1000);
                geoArea.setIsDeleted(Constants.IS_DELETED_NO);
                geoAreaMapper.insert(geoArea);
                log.info("add area : {}",GsonUtils.gsonString(geoArea));
            }
        });

        return  true;
    }

    /**
     * update or insert geoCountry
     * @param geoCountryVOS
     * @param finalLang
     */
    private boolean upsert(List<GeoCountryVO> geoCountryVOS, String finalLang) {
       if(CollUtil.isEmpty(geoCountryVOS)){
           return false;
       }
        long count = geoCountryVOS.parallelStream().map(geoCountryVO -> {

            GeoCountry geoCountry = CglibUtil.copy(geoCountryVO,GeoCountry.class);
            geoCountry.setLocales(finalLang);
            geoCountry.setUpdated((int)DateUtil.currentSeconds()/1000);

            GeoCountry geoCountryQuery = new GeoCountry();
            geoCountryQuery.setGeonameId(geoCountry.getGeonameId());
            geoCountryQuery.setLocales(finalLang);
            geoCountryQuery.setIsDeleted(Constants.IS_DELETED_NO);
            List<GeoCountry> geoCountries = geoCountryMapper.select(geoCountryQuery);

            int result;
            if(geoCountries.size() > 0){
                result = geoCountryMapper.updateByGeonameidAndLocalesSelective(geoCountry);
                log.info("sync update country: {}", GsonUtils.gsonString(geoCountryVO));
            }else{
                geoCountry.setCreated((int)DateUtil.currentSeconds()/1000);
                geoCountry.setIsDeleted(Constants.IS_DELETED_NO);
                result = geoCountryMapper.insert(geoCountry);
                log.info("sync add country: {}", GsonUtils.gsonString(geoCountryVO));
            }

            return result;
        }).filter(integer -> {
            return integer > 0;
        }).count();

       return count > 0;
    }
}
