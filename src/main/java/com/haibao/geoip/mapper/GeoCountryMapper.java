package com.haibao.geoip.mapper;

import com.haibao.geoip.domain.model.GeoCountry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface GeoCountryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GeoCountry record);

    int insertSelective(GeoCountry record);

    GeoCountry selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GeoCountry record);

    int updateByPrimaryKey(GeoCountry record);

    List<GeoCountry> select(GeoCountry geoCountry);

    int updateByGeonameidAndLocalesSelective(GeoCountry geoCountry);
}
