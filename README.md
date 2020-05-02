# Excel Reconcilor
This is a utility application developed using apache poi package to reconcile two excel files.

# Purpose
This can be used to compare two excel files and generates a detailed report contains the required info to easily identify the diff.

# Report
Report contains the following informations,

* Change action added/modified/removed
* Value from both the excel for easy compare

# Usage

Step 1: Create a set of columns to identify the unique row, which is used to find the matching row in second file. You can specify multiple columns, so that it will look for combined identity.
        
        String[] matchColumns=new String[]{"Name" };

Step 2: Create an instance 

        ExcelReconciler instance= ReconcilerInstanceFactory.getInstance("xlsx");
        
Step 3: Parse the file create the documents, creates a lightweighted instance of the excel file

        Document document1 = instance.parse("Sample1.xlsx");
        Document document2 = instance.parse("Sample2.xlsx");
        
Step 4: Perform reconcile, generates a result object contains required info to identify the difference

        ReconsileResult result = instance.reconcile(document1,document2,matchColumns);
        
Step 5: Export the result to an excel file

        instance.exportResult(result,"result.xlsx");

# Command line access
This  util support command line usage as follows,

        java -jar excel-reconciler.jar -s Sample1.xlsx -s Sample2.xlsx -o result.xlsx -i Name

Following are the supported command line args,

        usage: Excel Reconcilor
         -c <arg>   Type of the file. supported format [xlsx] (optional)
         -h         Help
         -i <arg>   Comma separated identity columns
         -o <arg>   Output file path
         -s <arg>   File path of files to be reconciled. Use this option twice
                    to provide both paths.

    
