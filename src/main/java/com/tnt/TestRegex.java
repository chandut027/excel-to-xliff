package com.tnt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

    public static void main(String[] args) {
        
        String localePatternStr = "[a-z]{2,2}_[a-z]{2,2}";
        // Compile Regex expression
        Pattern localePattern = Pattern.compile(localePatternStr);
        // Match locale+directory pattern in original URL
        Matcher localeMatcher = localePattern.matcher("bg_bg-track-n-trace-export.xls");
        String urlLocal ="en-gb";
        if (localeMatcher.find()) {
            urlLocal  = localeMatcher.group();
            System.out.println("Match");
        }
        System.out.println("local: " + urlLocal.replace("_", "-"));
    }
}
