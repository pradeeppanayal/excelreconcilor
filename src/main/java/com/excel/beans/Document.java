package com.excel.beans;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Pradeep CH
 * @version 1.0
 */

public class Document {

    private List<Record> records;
    private String[] columns;

    public Document(){
        this.records =new ArrayList<Record>();
    }
    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }
}
