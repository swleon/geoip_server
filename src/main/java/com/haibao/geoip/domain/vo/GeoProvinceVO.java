package com.haibao.geoip.domain.vo;

import com.haibao.geoip.domain.model.GeoArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoProvinceVO extends GeoArea {

    private Long totalResultsCount;
    private List<GeoArea> geoChildrenInfoList;
}
