package com.haibao.geoip.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeoParm {

    private Long geonameId;
    private String geoname;
    private Integer geotype;
}
