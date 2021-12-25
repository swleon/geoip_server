package com.haibao.geoip.common.enums;


import com.haibao.geoip.domain.vo.ContinentVO;

import java.util.LinkedList;
import java.util.List;

/**
 * 大洲 枚举
 */
public enum ContinentEnums {

    SouthAmerica(0L,"SA","South America"),
    Asia(1L,"AS","Asia"),
    Europe(2L,"EU","Europe"),
    NorthAmerica(3L,"NA","North America"),
    Africa(4L,"AF","Africa"),
    Oceania(5L,"OC","Oceania"),
    Antarctica(6L,"AN","Antarctica");

    private Long geonameId;
    private String continent;
    private String continentName;

    ContinentEnums(Long geonameId, String continent, String continentName) {
        this.geonameId = geonameId;
        this.continent = continent;
        this.continentName = continentName;
    }

    public Long getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Long geonameId) {
        this.geonameId = geonameId;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * 将枚举值转化成list集合
     *
     * @return
     */
    public static List<ContinentVO> toList(){
        List<ContinentVO> list = new LinkedList<>();
        for (ContinentEnums continentEnum: ContinentEnums.values()) {
            ContinentVO continentVO = new ContinentVO();
            continentVO.setGeonameId(continentEnum.getGeonameId());
            continentVO.setContinent(continentEnum.getContinent());
            continentVO.setContinentName(continentEnum.getContinentName());
            list.add(continentVO);
        }
        return list;
    }



}
