package com.tnt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReading {

    public static void echoAsCSV(Sheet sheet, BufferedWriter bw, String lang) throws IOException {
        Row row;
        int count = 1;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                bw.write("    <trans-unit id=\""+count+"\">");
                bw.newLine();
                bw.write("       <source xml:lang=\"en\"><![CDATA[" + row.getCell(j++) + "]]></source>");
                bw.newLine();
                bw.write("       <target xml:lang=\"" + lang + "\"><![CDATA[" + row.getCell(j) + "]]></target>");
                bw.newLine();
                bw.write("    </trans-unit>");
                bw.newLine();
                count++;
            }
        }
    }
    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        String fileName = args[0];
        String lang = getLocale(fileName);
        System.out.println(fileName);
        InputStream inp = null;
        try {
            File fout = new File("target/"+lang+".dict.xliff");
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            inp = new FileInputStream(fileName);
            Workbook wb = WorkbookFactory.create(inp);
            bw.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
            bw.newLine();
            bw.write("  <xliff version=\"1.1\">");
            bw.newLine();
            bw.write("   <file");
            bw.newLine();
            bw.write("      source-language=\"en\"");
            bw.newLine();
            bw.write("      target-language=\"" + lang + "\">");
            bw.newLine();
            bw.write("      <body>");
            bw.newLine();
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                echoAsCSV(wb.getSheetAt(i), bw, lang);
            }
            bw.write("      </body>");
            bw.newLine();
            bw.write("    </file>");
            bw.newLine();
            bw.write("  </xliff>");
            bw.close();
        } catch (Exception ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inp.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static String getLocale(String fileName) {

        String localePatternStr = "/[a-z]{2,2}+_+[a-z]{2,2}/";
        // Compile Regex expression
        Pattern localePattern = Pattern.compile(localePatternStr);
        // Match locale+directory pattern in original URL
        Matcher localeMatcher = localePattern.matcher(fileName);
        String urlLocal ="en-gb";
        if (localeMatcher.find()) {
            
            urlLocal  = localeMatcher.group(1);
            System.out.println("local" + urlLocal);
        }

        return urlLocal;
    }
}