package com.excel.reconcile.core;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Pradeep CH
 * @version 1.0
 */

public class ReconcilerInstanceFactory {

    private static final Map<String,ExcelReconciler> INSTANCE_STORE=new HashMap<String, ExcelReconciler>();
    static {
        INSTANCE_STORE.put("XLSX",new XLSXReconcilor());
    }

    public  static  final ExcelReconciler getInstance(String name){
        String identifier = name==null?"": name.toUpperCase().trim();
        if(INSTANCE_STORE.containsKey(identifier))
            return INSTANCE_STORE.get(identifier);
        throw new InvalidParameterException("Could not find matching implementation for " + name);
    }
}
