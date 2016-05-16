package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model;

import java.util.Comparator;

public class MapDbFieldsOrderComparator implements Comparator<MapDBField> {
    @Override
    public int compare(MapDBField o1, MapDBField o2) {
        return o1.getOrder().compareTo(o2.getOrder());
    }
}
