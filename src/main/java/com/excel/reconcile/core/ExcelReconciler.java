package com.excel.reconcile.core;

import com.excel.beans.Document;
import com.excel.beans.ReconsileResult;

import java.io.IOException;

/**
 * @author Pradeep CH
 * @version 1.0
 */

public interface ExcelReconciler {

    Document parse(String sourceFilePath);

    ReconsileResult reconcile(Document document1, Document document2, String[] identityColumns);

    void exportResult(ReconsileResult result, String op) throws IOException;
}
