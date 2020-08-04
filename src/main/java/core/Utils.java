package main.java.core;

public class Utils {

    /* Counters*/
    public static int GLOBAL_TESTS_OCCURANCE_COUNT = 0;
    public static int PASS_COUNT = 0;
    public static int FAIL_COUNT = 0;
    public static int SKIP_COUNT = 0;
    public static String TEST_SUITE_NAME;
    public static String FINAL_STATUS;
    public static  int TEST_PACK_COUNT = 0;

    /*Execution Time Counter*/
    public static double EXECUTION_TIME= 0;

    /** For Coloured Commandline Output **/
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static int convertToMiliSeconds(final double timeIp){
        return (int) timeIp*1000;
    }


}
