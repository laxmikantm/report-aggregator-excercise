package main.java.core;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner {

    public static void main(String[] args) {
        ConsolidateReports crs = new ConsolidateReports();
        OutPutGenerator opg = new OutPutGenerator();

        System.out.println("--------WELCOME--------------");
        System.out.println(": Please enter test file name\n");
        Scanner in = new Scanner(System.in);

        String s = in.nextLine();
        crs.readTestFile(s);


        opg.printOutput(crs);
    }
}
