package com.haibao.geoip.domain.model;

import com.haibao.geoip.common.base.domain.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoCountry extends BaseModel {

    private String continent;

    private String capital;

    private String languages;

    private Long geonameId;

    private Double south;

    private String isoAlpha3;

    private Double north;

    private String fipsCode;

    private String population;

    private Double east;

    private String isoNumeric;

    private String areaInSqKm;

    private String countryCode;

    private Double west;

    private String countryName;

    private String postalCodeFormat;

    private String continentName;

    private String currencyCode;

    /**
     * 语言环境
     */
    private String locales;

    private Map parms;

}
