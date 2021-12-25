package com.haibao.geoip.domain.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wuque
 * @description:
 */
@NoArgsConstructor
@Data
public class GeoCountryVO implements Serializable {

    @SerializedName("continent")
    private String continent;
    @SerializedName("capital")
    private String capital;

    private String capitalCN;

    @SerializedName("languages")
    private String languages;
    @SerializedName("geonameId")
    private Long geonameId;
    @SerializedName("south")
    private Double south;
    @SerializedName("isoAlpha3")
    private String isoAlpha3;
    @SerializedName("north")
    private Double north;
    @SerializedName("fipsCode")
    private String fipsCode;
    @SerializedName("population")
    private String population;
    @SerializedName("east")
    private Double east;
    @SerializedName("isoNumeric")
    private String isoNumeric;
    @SerializedName("areaInSqKm")
    private String areaInSqKm;
    @SerializedName("countryCode")
    private String countryCode;
    @SerializedName("west")
    private Double west;
    @SerializedName("countryName")
    private String countryName;

    private String countryNameCN;

    @SerializedName("postalCodeFormat")
    private String postalCodeFormat;
    @SerializedName("continentName")
    private String continentName;

    private String continentNameCN;

    @SerializedName("currencyCode")
    private String currencyCode;

}
