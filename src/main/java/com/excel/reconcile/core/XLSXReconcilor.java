package com.excel.reconcile.core;

import com.excel.beans.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pradeep CH
 * @version 1.0
 */
public class XLSXReconcilor extends   AbstractReconciler {

    private static final String ACTION = "Action";
    private static final String S1=" (s1)";
    private static final String S2=" (s2)";

    public Document parse(String filePath, String[] identityColumns) {
        if(identityColumns==null || identityColumns.length==0)
            throw new InvalidParameterException("Identity column should be specified");

        File file1=new File(filePath);
        Document document =new Document();
        Workbook workbook1;
        try {
            workbook1 = WorkbookFactory.create(file1);
        }catch (Exception ezx){
            ezx.printStackTrace();
            throw new InvalidParameterException("Could not parse the file to excel format.");
        }
        Sheet s=workbook1.getSheetAt(0);
        Row firstRow = s.getRow(0);
        int  cellCount = firstRow.getPhysicalNumberOfCells();
        String[] columns = new String[cellCount];
        for(int i=0;i<cellCount;i++){
            columns[i] = firstRow.getCell(i).getStringCellValue();
        }
        document.setColumns(columns);
        for(int i=1;i<s.getPhysicalNumberOfRows();i++){
            Row row= s.getRow(i);
            Record crow = new Record();
            for(int j=0;j<row.getPhysicalNumberOfCells();j++){
                String column = columns[j];
                crow.getItems().put(column, getValue(row.getCell(j)));
            }

            document.getRecords().put(super.getIdentityAsKey(crow,identityColumns),crow);
        }
        try {
            workbook1.close();
            workbook1 = null;
        }catch (Exception ex){
            //TODO nothing
        }
        return document;
    }

    /**
     * Exports the result to a given file
     * @param result
     * @param outputPath
     * @throws IOException
     */
    public void exportResult(ReconsileResult result, String outputPath) throws IOException {
        File op=new File(outputPath);

        if (op.exists())
            if (!op.delete())
                throw new InvalidParameterException("Could not remove the target file.");
        //op.createNewFile();

        Workbook workbook1;
        OutputStream os;
        try {
            workbook1 = new XSSFWorkbook();
            os = new FileOutputStream(op);
        } catch (Exception ezx) {
            ezx.printStackTrace();
            throw new InvalidParameterException("Could not parse the file to excel format.");
        }
        Sheet sheet = workbook1.createSheet();

        Map<String, Integer>  columnIndexMapper= createHeader(sheet,result);
        recordResult(sheet,result,1,columnIndexMapper);
        workbook1.write(os);
        try{
            if(os!=null) os.close();
            if(workbook1!=null) workbook1.close();
            workbook1=null;
        }catch (Exception ex){}

    }

    /**
     * This method creates the header and return the colum index mapper
     * @param sheet
     * @param result
     * @return
     */
    private Map<String, Integer> createHeader(Sheet sheet,ReconsileResult result){
        int i = 0;
        Row header = sheet.createRow(0);
        Map<String, Integer> columnIndexMapper = new HashMap<String, Integer>();
        for (String str : result.getIdentityColumnNames()) {
            header.createCell(i).setCellValue(str);
            columnIndexMapper.put(str, i);
            i++;
        }
        header.createCell(i).setCellValue(ACTION);
        columnIndexMapper.put(ACTION, i);
        i++;

        for (String str : result.getUpdatedColumnNames()) {
            if (result.getIdentityColumnNames().contains(str))
                continue;
            String c1 = str + S1;
            String c2 = str + S2;
            header.createCell(i).setCellValue(c1);
            columnIndexMapper.put(c1, i);
            i++;
            header.createCell(i).setCellValue(c2);
            columnIndexMapper.put(c2, i);
            i++;
        }
        return columnIndexMapper;
    }

    /**
     * this method adds the reconcile result to the excel file
     * @param sheet
     * @param result
     * @param rowIndex
     * @param columnIndexmapper
     */
    private void recordResult(Sheet sheet,ReconsileResult result,int rowIndex,Map<String,Integer> columnIndexmapper){
        for(ResultEntry entry: result.getResultRow()){
            Row row= sheet.createRow(rowIndex++);
            for(String str: entry.getIdentityFields().keySet()){
                Cell cell = row.createCell(columnIndexmapper.get(str));
                setCellValue(cell,entry.getIdentityFields().get(str));
            }
            row.createCell(columnIndexmapper.get(ACTION)).setCellValue(entry.getMode());
            for(ReconsiledItem item: entry.getItems()){
                String c1= item.getName()+S1;
                String c2= item.getName()+S2;
                Cell cell = row.createCell(columnIndexmapper.get(c1));
                setCellValue(cell,item.getVal1());
                cell = row.createCell(columnIndexmapper.get(c2));
                setCellValue(cell,item.getVal2());
            }
        }
    }

    /**
     * Based on the type of the data which updates the value
     * @param cell
     * @param o
     */
    private void setCellValue(Cell cell, Object o) {
        if(o==null)
            cell.setBlank();
        else if(o instanceof  String)
            cell.setCellValue((String) o);
        else if(o instanceof  Double)
            cell.setCellValue((Double) o);
        else if(o instanceof  Boolean)
            cell.setCellValue((Boolean) o);
        else
            cell.setCellValue(o.toString());
    }


    /**
     * Based on the cell type fetch the data
     * @param cell
     * @return
     */
    public Object getValue(Cell cell){
        if(cell==null)
            return null;
        if(cell.getCellType() == CellType.BLANK)
        return "";
        if(cell.getCellType() == CellType.BOOLEAN)
        return cell.getBooleanCellValue();
        if(cell.getCellType() == CellType.NUMERIC)
        return cell.getNumericCellValue();
        if(cell.getCellType() == CellType.STRING)
        return cell.getStringCellValue();
        return  "";
    }

}
