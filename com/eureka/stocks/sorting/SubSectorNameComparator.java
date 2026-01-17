package com.eureka.stocks.sorting;

import com.eureka.stocks.vo.SubSectorVO;

import java.util.Comparator;

/***
 * Comparator which orders a list of subsectors by subsector name ascending
 */
public class SubSectorNameComparator implements Comparator<SubSectorVO> {

    @Override
    public int compare(SubSectorVO o1, SubSectorVO o2) {
        return o1.getSubSectorName().compareTo(o2.getSubSectorName());
    }
}
