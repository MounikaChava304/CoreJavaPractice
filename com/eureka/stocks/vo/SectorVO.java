package com.eureka.stocks.vo;

import java.util.Objects;

/***
 * This class is used to create instances/objects that each represent a Sector Record or a row from the sector_lookup table
 */
public class SectorVO implements Comparable<SectorVO> {
    private int sectorID;
    private String sectorName;

    private SectorVO() {
    }

    public SectorVO(int sectorID) {
        this.sectorID = sectorID;
    }

    public int getSectorID() {
        return sectorID;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SectorVO sectorVO = (SectorVO) o;
        return sectorID == sectorVO.sectorID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sectorID);
    }

    @Override
    public String toString() {
        return "SectorVO{" +
                "sectorID=" + sectorID +
                ", sectorName='" + sectorName + '\'' +
                '}';

    }

    /***
     * Natural order sorting by sector name ascending
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(SectorVO o) {
        return this.sectorName.compareTo(o.getSectorName());
//        return o.getSectorName().compareTo(this.sectorName);
    }
}
