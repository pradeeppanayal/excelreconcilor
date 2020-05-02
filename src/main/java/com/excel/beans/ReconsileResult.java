package com.excel.beans;

import java.util.*;


/**
 * @author Pradeep CH
 * @version 1.0
 */

public class ReconsileResult {
    private Set<String> columns;
    private List<ResultEntry> resultRow;
    private Set<String> identityColumnNames;
    private Set<String> updatedColumnNames;

    public ReconsileResult(){
        this.columns = new HashSet<String>();
        this.resultRow=new ArrayList<ResultEntry>();
        this.identityColumnNames = new HashSet<String>();
        this.updatedColumnNames = new HashSet<String>();
    }

    public Set<String> getIdentityColumnNames() {
        return identityColumnNames;
    }

    public void setIdentityColumnNames(Set<String> identityColumnNames) {
        this.identityColumnNames = identityColumnNames;
    }

    public Set<String> getColumns() {
        return columns;
    }

    public void setColumns(Set<String> columns) {
        this.columns = columns;
    }

    public List<ResultEntry> getResultRow() {
        return resultRow;
    }

    public void setResultRow(List<ResultEntry> resultRow) {
        this.resultRow = resultRow;
    }

    public Set<String> getUpdatedColumnNames() {
        return updatedColumnNames;
    }

    public void setUpdatedColumnNames(Set<String> updatedColumnNames) {
        this.updatedColumnNames = updatedColumnNames;
    }

    @Override
    public String toString() {
        return "ReconsileResult{" +
                "columns=" + columns +
                ", resultRow=" + resultRow +
                '}';
    }
}
