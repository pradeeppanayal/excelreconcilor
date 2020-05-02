# Excel Reconcilor
This is a utility application developed using apache poi package to reconcile two excel files.

# Purpose
This can be used to compare two excel files and generates a detailed report contains the required info to easily identify the diff.

# Report
Report contains the following informations,

* Change action added/modified/removed
* Value from both the excel for easy compare

# Usage

Step 1: Create an instance 

        ExcelReconciler instance= ReconcilerInstanceFactory.getInstance("xlsx");
        
Step 2: Parse the file create the documents, creates a lightweighted instance of the excel file

        Document document1 = instance.parse("/work/sampleexcelfile/Sample1.xlsx");
        Document document2 = instance.parse("/work/sampleexcelfile/Sample2.xlsx");
        
Step 3: Perform reconcile, generates a result object contains required info to identify the difference

        ReconsileResult result = instance.reconcile(document1,document2,matchColumns);
        
Step 4: Export the result to an excel file

        instance.exportResult(result,"/work/sampleexcelfile/result.xlsx");`
    
