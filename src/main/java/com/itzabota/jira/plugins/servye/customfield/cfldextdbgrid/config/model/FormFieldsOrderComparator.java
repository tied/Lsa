package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model;

import java.util.Comparator;

public class FormFieldsOrderComparator implements Comparator<FormField> {
    @Override
    public int compare(FormField o1, FormField o2) {
        return o1.getOrder().compareTo(o2.getOrder());
    }
}
