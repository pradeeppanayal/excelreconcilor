package com.excel.reconcile.core;

import com.excel.beans.Document;
import com.excel.beans.ReconsileResult;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * @author Pradeep CH
 * @version 1.0
 */

public class Main {
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

        ExcelReconciler instance= ReconcilerInstanceFactory.getInstance(instanceIdentifier);
        Document document1 = instance.parse(path1);
        Document document2 = instance.parse(path2);
        ReconsileResult result = instance.reconcile(document1,document2,matchColumns);
        instance.exportResult(result,outputPath);

        //System.out.println(result);

    }

    private static Options prepareOptions() {
        // create Options object
        Options options = new Options();
        options.addOption("s", true, "Space separated file paths of files to be reconciled(2 paths).");
        options.addOption("o", true, "Output file path");
        options.addOption("i", true, "Comma separated identity columns");
        options.addOption("c", true, "Type of the file. supported format [xlsx] (optional)");
        options.addOption("h", false, "Help");
        return options;
    }

}
