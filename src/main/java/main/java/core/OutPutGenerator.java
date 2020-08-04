package main.java.core;

import java.util.Hashtable;

import static main.java.core.Utils.*;

public class OutPutGenerator {

    private void getOutPutString(ConsolidateReports crs){
        String FINAL_RESULT_HIGHLIGHT_COLOUR= ANSI_BLACK;

        System.out.println("-------------INDIVIDUAL TEST CASE DETAILS-------------------");
        printTestControlData(crs);
        System.out.println("----------------------------------------------------");
        System.out.println(ANSI_BLACK_BACKGROUND+ ANSI_WHITE+": Total Tests Count: "+Utils.GLOBAL_TESTS_OCCURANCE_COUNT + ANSI_RESET);
//        printPassCountDetails();
//        printFailCountDetails();
//        printSkipCountDetails();
        System.out.println(": Total time taken to run: "+ Utils.convertToMiliSeconds(EXECUTION_TIME)+"ms");
        if(FINAL_STATUS.equalsIgnoreCase("PASS")){
            FINAL_RESULT_HIGHLIGHT_COLOUR= ANSI_GREEN;
        }else if(FINAL_STATUS.equalsIgnoreCase("FAIL")){
            FINAL_RESULT_HIGHLIGHT_COLOUR= ANSI_RED;
        }
        System.out.println(ANSI_YELLOW_BACKGROUND+ ": Final Test Suite run status:"+ ANSI_RESET + FINAL_RESULT_HIGHLIGHT_COLOUR+ FINAL_STATUS+ ANSI_RESET);
        System.out.println("----------------------------------------------------");
        System.out.println("----------------------------------------------------");
        System.out.println(ANSI_YELLOW_BACKGROUND+"**Error / Warning messages thrown during the TestSuite Run**"+ANSI_RESET);
        printRawErrorMessages();
        System.out.println("----------------------------------------------------");
    }

    private void printTestControlData(ConsolidateReports crs) {
        crs.getTestControlData().forEach((key, value) -> {
            System.out.println("*TEST CASE NAME* : "+ key);
            System.out.println("-----------------------");
            System.out.println(ANSI_GREEN+ " :PASS Count: "+ value.get(TEST_COUNTS_MODEL.PASS_COUNT.getItem())+ANSI_RESET);
            System.out.println(ANSI_RED+" :FAIL Count: "+ value.get(TEST_COUNTS_MODEL.FAIL_COUNT.getItem())+ ANSI_RESET);
            System.out.println(ANSI_BLUE+ " :SKIP Count: "+ value.get(TEST_COUNTS_MODEL.SKIP_COUNT.getItem())+ANSI_RESET);
            System.out.println(" :Test Steps Ran : "+ value.get(TEST_COUNTS_MODEL.TEST_STEP_COUNT.getItem()));

            System.out.println("=========================\n");
        });
    }

    private void printPassCountDetails(){
        System.out.println(ANSI_CYAN_BACKGROUND+ ": Number of Tests Passed: "+ Utils.PASS_COUNT+ ANSI_RESET);
    }

    private void printFailCountDetails(){
        System.out.println(ANSI_RED+ ": Number of Tests Failed: "+ Utils.FAIL_COUNT+ ANSI_RESET);
    }

    private void printSkipCountDetails(){
        System.out.println(ANSI_BLUE+ ": Number of Tests Skipped: "+ SKIP_COUNT+ ANSI_RESET);
    }

    private void printRawErrorMessages(){
        ConsolidateReports.errorMsgsList.forEach(System.out::println);
    }

    public void printOutput(ConsolidateReports crs) {
        System.out.println("*********************************************************");
        System.out.println("*************************OUT-PUT*************************");
        System.out.println("*********************************************************\n");
        System.out.println(": Maste Test Suite Name: "+ TEST_SUITE_NAME);
        this.getOutPutString(crs);
    }
}
