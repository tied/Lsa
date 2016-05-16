package com.itzabota.jira.plugins.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class SqlTimestampAdapter extends XmlAdapter<java.util.Date, java.sql.Timestamp> {

    @Override
    public java.util.Date marshal(java.sql.Timestamp sqlTimestamp) throws Exception {
        if(null == sqlTimestamp) {
            return null;
        }
        return new java.util.Date(sqlTimestamp.getTime());
    }

    @Override
    public java.sql.Timestamp unmarshal(java.util.Date utilDate) throws Exception {
        if(null == utilDate) {
            return null;
        }
        return new java.sql.Timestamp(utilDate.getTime());
    }

}

