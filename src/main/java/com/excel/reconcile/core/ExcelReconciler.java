package com.excel.reconcile.core;

import com.excel.beans.Document;
import com.excel.beans.ReconsileResult;
import com.excel.beans.Record;

import java.io.IOException;

/**
 * @author Pradeep CH
 * @version 1.0
 */

public interface ExcelReconciler {

    Document parse(String sourceFilePath, String[] identityColumns);

    ReconsileResult reconcile(Document document1, Document document2, String[] identityColumns);

    void exportResult(ReconsileResult result, String op) throws IOException;

    String getIdentityAsKey(Record rec, String[] identityColumns);
}
