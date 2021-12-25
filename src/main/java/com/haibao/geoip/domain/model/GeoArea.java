package com.haibao.geoip.domain.model;

import com.haibao.geoip.common.base.domain.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoArea extends BaseModel {

    private String adminCode1;

    private String lng;

    private Long geonameId;

    private String toponymName;

    private String countryId;

    private String fcl;

    private Long population;

    private String countryCode;

    private String name;

    private String fclName;

    private String admincodes1Iso31662;

    private String countryName;

    private String fcodeName;

    private String adminName1;

    private String lat;

    private String fcode;

    /**
     * 语言环境
     */
    private String locales;

    private Integer level;

    private Long pGeonameId;


}
