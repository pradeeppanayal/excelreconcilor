package com.excel.reconcile.core;

import com.excel.beans.Document;
import com.excel.beans.ReconsileResult;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * @author Pradeep CH
 * @version 1.0
 */

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException, ParseException {

        Options options = prepareOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( options, args);

        if(cmd.hasOption('h')){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "Excel Reconcilor", options );
            return;
        }
        String path1;
        String path2;
        String outputPath;
        if(!cmd.hasOption('s')  || !cmd.hasOption('o'))
            throw new InvalidParameterException("Path of files and out put file are required. Use -h for help.");
        if(!cmd.hasOption('i'))
            throw new InvalidParameterException("Unique column identifiers are required. Use -h for help.");
        String instanceIdentifier = "xlsx";
        if(cmd.hasOption('c'))
            instanceIdentifier = cmd.getOptionValue('c');

        String[] paths = cmd.getOptionValues('s');
        if(paths.length!=2)
            throw new InvalidParameterException("Invalid file paths. Should be 2 paths with -s argument");

        path1= paths[0];
        path2= paths[1];
        outputPath= cmd.getOptionValue('o');


        //instance.reconcile(f1,f2,new String[]{"Segment",	"Country",	 "Product" });
        String[] matchColumns=cmd.getOptionValue('i').split(",");
        System.out.println("Processing started with param [Source 1: "+path1+", Source 2:"+path2+", O/p Path :"+outputPath+", Identity : "+cmd.getOptionValue('i')+"]");
        ExcelReconciler instance= ReconcilerInstanceFactory.getInstance(instanceIdentifier);
        Document document1 = instance.parse(path1,matchColumns);
        System.out.println("Parsed file 1 with row count :"+document1.getRecords().size());
        Document document2 = instance.parse(path2,matchColumns);
        System.out.println("Parsed file 1 with row count :"+document2.getRecords().size());
        ReconsileResult result = instance.reconcile(document1,document2,matchColumns);
        System.out.println("Reconciling completed. Mismatches rows :"+result.getResultRow().size());
        System.out.println("Persisting ..");
        instance.exportResult(result,outputPath);
        System.out.println("Output file saved to location :" + outputPath);

        //System.out.println(result);

    }

    private static Options prepareOptions() {
        // create Options object
        Options options = new Options();
        options.addOption("s", true, "File path of files to be reconciled. Use this option to twice to provide both paths.");
        options.addOption("o", true, "Output file path");
        options.addOption("i", true, "Comma separated identity columns");
        options.addOption("c", true, "Type of the file. supported format [xlsx] (optional)");
        options.addOption("h", false, "Help");
        return options;
    }

}
