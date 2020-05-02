package com.excel.beans;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Pradeep CH
 * @version 1.0
 */

public class Record {

    private Map<String,Object> items;
    private boolean reconciled;

    public Record(){
        this.items= new HashMap<String, Object>();
        this.reconciled=false;
    }
    public Map<String, Object> getItems() {
        return items;
    }

    public void setItems(Map<String, Object> items) {
        this.items = items;
    }

    public boolean isReconciled() {
        return reconciled;
    }

    public void setReconciled(boolean reconciled) {
        this.reconciled = reconciled;
    }

}
