package com.excel.reconcile.core;

import com.excel.beans.*;
import com.excel.comparators.RowComparator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pradeep CH
 * @version 1.0
 */
public abstract class AbstractReconciler implements  ExcelReconciler{

    public ReconsileResult reconcile(Document document1, Document document2, String[] identityColumns) {
        Comparator<Record> rowComparator = new RowComparator(identityColumns);
        final ReconsileResult result=new ReconsileResult();


        Set<String> identityColumnSet = new HashSet<String>();
        for (String str:identityColumns  )
            identityColumnSet.add(str);

        result.setIdentityColumnNames(identityColumnSet);

        for (String str: document1.getColumns())
            result.getColumns().add(str);
        for (String str: document2.getColumns())
            result.getColumns().add(str);
        for(Record record1 : document1.getRecords()){
            boolean hasmatch= false;
            for(Record record2 : document2.getRecords()){
                if(record2.isReconciled())
                    continue;
                if(rowComparator.compare(record1, record2)==0){
                    record1.setReconciled(true);
                    record2.setReconciled(true);
                    ResultEntry entry =  findMismatchIfAny(record1, record2,identityColumnSet,result.getColumns());
                    if(entry.hasMismatch()){
                        entry.setMode("Modified");
                        result.getResultRow().add(entry);
                        entry.getItems().stream().forEach(o-> result.getUpdatedColumnNames().add(o.getName()));
                    }
                    hasmatch=true;
                    break;
                }
            }
            //entries which is missing in second file
            if(!hasmatch){
                result.getResultRow().add( fillMissingEntry(record1,"Removed",identityColumns));
            }
        }

        //add entries which is added to the second
        for(Record record2 : document2.getRecords()){
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
}
