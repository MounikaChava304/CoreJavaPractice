package com.eureka.stocks.vo;

import java.util.Objects;

public class SubSectorVO implements Comparable<SubSectorVO> {
    private int subSectorID;
    private String subSectorName;
    private int sectorID;

    private SubSectorVO() {
    }

    public SubSectorVO(int subSectorID) {
        this.subSectorID = subSectorID;
    }

    public int getSubSectorID() {
        return subSectorID;
    }

    public String getSubSectorName() {
        return subSectorName;
    }

    public void setSubSectorName(String subSectorName) {
        this.subSectorName = subSectorName;
    }

    public int getSectorID() {
        return sectorID;
    }

    public void setSectorID(int sectorID) {
        this.sectorID = sectorID;
    }

    @Override
    public String toString() {
        return "SubSectorVO{" +
                "subSectorID=" + subSectorID +
                ", subSectorName='" + subSectorName + '\'' +
                ", sectorID=" + sectorID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SubSectorVO that = (SubSectorVO) o;
        return subSectorID == that.subSectorID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(subSectorID);
    }

    /***
     * Natural order that needs to be established is descending order by SubSectorID
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(SubSectorVO o) {
        if (this.subSectorID > o.getSubSectorID()) {
            return -1;
        } else if (this.subSectorID < o.getSubSectorID()) {
            return 1;
        } else {
            return 0;
        }
    }
}
