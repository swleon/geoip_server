package com.haibao.geoip.mapper;

import com.haibao.geoip.domain.model.GeoArea;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface GeoAreaMapper {

    int deleteByPrimaryKey(Long id);

    int insert(GeoArea record);

    int insertSelective(GeoArea record);

    GeoArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GeoArea record);

    int updateByPrimaryKey(GeoArea record);

    List<GeoArea> select(GeoArea geoAreaQuery);

    int updateByGeonameIdAndLocalesSelective(GeoArea geoArea);

    List<GeoArea> match(GeoArea geoCityQuery);
}
