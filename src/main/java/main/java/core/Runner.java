package main.java.core;
import main.java.core.ConsolidateReports;
import main.java.core.OutPutGenerator;

import java.util.Scanner;

public class Runner {

    public static void main(String[] args) throws Exception {
        ConsolidateReports crs = new ConsolidateReports();
        OutPutGenerator opg = new OutPutGenerator();

        System.out.println("---------------WELCOME----------------");
        System.out.println(": Please enter the test file name or below outlined test file single digit codes : ");
        System.out.println(" 1. graph_test -> enter 1");
        System.out.println(" 2. lock_test_1 -> enter 2");
        System.out.println(" 3. lock_test_2 -> enter 3");
        System.out.println(" 4. rewrite_test -> enter 4");
        Scanner in = new Scanner(System.in);
        String inputString = in.nextLine();

        switch (inputString.trim().toLowerCase()) {
            case "1":
            case "graph_test":
            case "graph_test.txt":
                inputString= "graph_test.txt";
                break;

            case "2":
            case "lock_test_1":
            case "lock_test_1.txt":
                inputString= "lock_test_1.txt";
                break;

            case "3":
            case "lock_test_2":
            case "lock_test_2.txt":
                inputString= "lock_test_2.txt";
                break;

            case "4":
            case "rewrite_test":
            case "rewrite_test.txt":
                inputString= "rewrite_test.txt";
                break;
            default:
                throw new Exception("*Invalid File Name");

        }
        crs.readTestFile(inputString);
        opg.printOutput(crs);
    }
}
