package com.excel.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Pradeep CH
 * @version 1.1
 */

public class Document {

    private Map<String,Record> records;
    private String[] columns;

    public Document(){
        this.records =new HashMap<>();
    }
    public Map<String,Record> getRecords() {
        return records;
    }

    public void setRecords(Map<String,Record> records) {
        this.records = records;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }
}
