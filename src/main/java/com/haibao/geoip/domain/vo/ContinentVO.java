package com.haibao.geoip.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 大洲
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContinentVO {
    private Long geonameId;
    private String continent;
    private String continentName;
}
