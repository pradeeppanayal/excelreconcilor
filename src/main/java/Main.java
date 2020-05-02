import com.excel.beans.Document;
import com.excel.reconcile.core.ExcelReconciler;
import com.excel.reconcile.core.ReconcilerInstanceFactory;
import com.excel.beans.ReconsileResult;

import java.io.*;

/**
 * @author Pradeep CH
 * @version 1.0
 */

public class Main {
    public static void main(String[] args) throws IOException {

        //instance.reconcile(f1,f2,new String[]{"Segment",	"Country",	 "Product" });
        String[] matchColumns=new String[]{"Name" };

        ExcelReconciler instance= ReconcilerInstanceFactory.getInstance("xlsx");
        Document document1 = instance.parse("/home/ias/work/sampleexcelfile/Sample1.xlsx");
        Document document2 = instance.parse("/home/ias/work/sampleexcelfile/Sample2.xlsx");
        ReconsileResult result = instance.reconcile(document1,document2,matchColumns);
        instance.exportResult(result,"/home/ias/work/sampleexcelfile/result.xlsx");

        //System.out.println(result);

    }

}
