package com.haibao.geoip.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 返回给定 geonameId 的子项（管理部门和人口稠密的地方）。
 * 子级是其他行政区划内的行政区划，例如州 (ADM1) 中的县 (ADM2) 或大陆上的国家/地区。
 * 树叶是人口稠密的地方，此服务不包括其他要素类，如景点、山脉等
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoChildrenInfoVO {

    private Long totalResultsCount;
    private List<GeonamesVO> geonames;
}
