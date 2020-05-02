package com.excel.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pradeep CH
 * @version 1.0
 */

public class ResultEntry {

    private Map<String,Object> identityFields;
    private List<ReconsiledItem> items;
    private String mode;


    public  boolean hasMismatch(){
        return this.items.size()>0;
    }
    public ResultEntry(){
        this.identityFields = new HashMap<String, Object>();
        this.items = new ArrayList<ReconsiledItem>();
    }

    public ResultEntry(String[] identityColumns, Record record){
        this();
        fillIdentityFields(identityColumns, record);
    }

    public void fillIdentityFields(String[] identityColumns, Record record) {
        for(String str: identityColumns){
            this.identityFields.put(str, record.getItems().get(str));
        }
    }

    public Map<String, Object> getIdentityFields() {
        return identityFields;
    }

    public void setIdentityFields(Map<String, Object> identityFields) {
        this.identityFields = identityFields;
    }

    public List<ReconsiledItem> getItems() {
        return items;
    }

    public void setItems(List<ReconsiledItem> items) {
        this.items = items;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "ResultEntry{" +
                "identityFields=" + identityFields +
                ", items=" + items +
                ", mode='" + mode + '\'' +
                '}';
    }
}

