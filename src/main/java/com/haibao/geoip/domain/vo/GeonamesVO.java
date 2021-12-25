package com.haibao.geoip.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * geonames
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeonamesVO implements Serializable {

     private String adminCode1;
     private String lng;
     private Long geonameId;
     private String toponymName;
     private String countryId;
     private String fcl;
     private Long population;
     private String countryCode;
     private String name;
     private String nameCN;
     private String fclName;
     private Map<String,Object> adminCodes1;
     private String countryName;
     private String countryNameCN;
     private String fcodeName;
     private String adminName1;
     private String adminName1CN;
     private String lat;
     private String fcode;

//      "adminCode1":"23",
//              "lng":"121.41667",
//              "geonameId":1796231,
//              "toponymName":"Shanghai Shi",
//              "countryId":"1814991",
//              "fcl":"A",
//              "population":18880000,
//              "countryCode":"CN",
//              "name":"上海",
//              "fclName":"country, state, region,...",
//              "adminCodes1":{
//        "ISO3166_2":"SH"
//    },
//            "countryName":"中国",
//            "fcodeName":"first-order administrative division",
//            "adminName1":"上海",
//            "lat":"31.16667",
//            "fcode":"ADM1"

}
