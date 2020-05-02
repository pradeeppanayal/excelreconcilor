package com.excel.comparators;

import com.excel.beans.Record;

import java.util.Comparator;

/**
 * @author Pradeep CH
 * @version 1.0
 */

public class RowComparator implements Comparator<Record> {

    private String[] identityFields;
    public RowComparator(String[] identityFields){
        this.identityFields=identityFields;
    }
    public int compare(Record record1, Record record2) {
        if(record1 ==null && record2 ==null)
            return 0;
        if(record1 ==null)
            return -1;
        if(record2 ==null)
            return 1;
        for(String fieldName:identityFields){
            Object v1= record1.getItems().get(fieldName);
            Object v2= record2.getItems().get(fieldName);
            if(v1==null && v2==null)
                continue;
            if(v1==null)
                return -1;
            if(v2==null)
                return 1;
            int i=v1.toString().compareTo(v2.toString());
            if(i!=0)
                return i;
        }
        return 0;
    }
}
