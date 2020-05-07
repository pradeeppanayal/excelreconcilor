package com.excel.reconcile.core;

import com.excel.beans.*;

import java.security.InvalidParameterException;
import java.util.*;

import static java.util.Collections.*;

/**
 * @author Pradeep CH
 * @version 1.0
 */
public abstract class AbstractReconciler implements  ExcelReconciler{

    public ReconsileResult reconcile(Document document1, Document document2, String[] identityColumns) {
        final ReconsileResult result=new ReconsileResult();


        final Set<String> identityColumnSet = new HashSet<>();
        addAll(identityColumnSet, identityColumns);

        result.setIdentityColumnNames(identityColumnSet);

        //find all columns
        addAll(result.getColumns(),document1.getColumns());
        addAll(result.getColumns(),document2.getColumns());

        for(Map.Entry<String, Record> record1 : document1.getRecords().entrySet()){
            Record matchingRec = document2.getRecords().get(record1.getKey());
            if(matchingRec==null){
                result.getResultRow().add( fillMissingEntry(record1.getValue(),"Removed",identityColumns));
                continue;
            }
            matchingRec.setReconciled(true);
            ResultEntry entry =  findMismatchIfAny(record1.getValue(), matchingRec,identityColumnSet,result.getColumns());
            if(entry.hasMismatch()){
                entry.setMode("Modified");
                result.getResultRow().add(entry);
                entry.getItems().stream().forEach(o-> result.getUpdatedColumnNames().add(o.getName()));
            }
        }

        //add entries which is added to the second
        for(Record record2 : document2.getRecords().values()){
            if(!record2.isReconciled())
                result.getResultRow().add( fillMissingEntry(record2,"Added",identityColumns));
        }
        return result;
    }

    private ResultEntry fillMissingEntry(Record record1, String mode,String[] identityColumns) {
        ResultEntry entry =new ResultEntry();
        entry.setMode(mode);
        for(String str: identityColumns){
            entry.getIdentityFields().put(str,record1.getItems().get(str));
        }
        return entry;
    }

    private ResultEntry findMismatchIfAny(Record record1, Record record2, Set<String> identityColumns, Set<String> columns) {
        ResultEntry entry =new ResultEntry();
        for(String str: columns) {
            Object v1 = record1.getItems().get(str);
            Object v2 = record2.getItems().get(str);
            if (identityColumns.contains(str)){
                entry.getIdentityFields().put(str, v1);
                continue;
            }
            if( v1==null && v1==null)
                continue;
            if(v1==null || !v1.equals(v2))
                entry.getItems().add(new ReconsiledItem(str,v1,v2));
        }
        return entry;
    }

    @Override
    public String getIdentityAsKey(Record rec, String[] identityColumns) {
        String str= "";
        for(String columnName: identityColumns){
            if(!rec.getItems().containsKey(columnName))
                throw new InvalidParameterException("Identity column could not found");
            str = str + rec.getItems().get(columnName);
        }
        return str;
    }
}
