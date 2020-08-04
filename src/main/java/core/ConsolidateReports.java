package main.java.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class ConsolidateReports {

    private static final String REGEX_SECONDS_FRACTION = "\\(([^)]+)\\)";
    public static final String TEST_PREFIX = "Test";
    String TestCaseName;
    private final Hashtable<String, List<String>> topLevelMap = new Hashtable<>();
    protected static List<String> errorMsgsList = new ArrayList<String>();
    Hashtable<String, Hashtable<String , Integer>> testControlData = new Hashtable<>();
    public Hashtable<String, Hashtable<String, Integer>> getTestControlData() {
        return testControlData;
    }


    /********************************************************************************
     * Logic::::::
     * 1. Read a file
     * 2. Read Test Name and store into SET
     * 3. Build PASS, FAIL, SKIP count
     * 4. Run-time counter
     * 5. Output String
     ********************************************************************************/

    public void readTestFile(final String fileName) {

        String filePath = System.getProperty("user.dir") + "/data/" + fileName;

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(
                    this::buildTopLevelMap
            );

            topLevelMap.forEach((k, v) ->{
                Hashtable<String, Integer> passFailTable= iterateOverMapRecords(k, v);
                testControlData.put(k, passFailTable);
                    System.out.println("HashTable for->"+k+" : "+testControlData);
            });
            System.out.println("TEST CONTROL DATA: "+testControlData);
            System.out.println("TopLevelMap->" + topLevelMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildTopLevelMap(final String ip) {
        if (ip.contains(TEST_PREFIX)) {
            String testName = generateTestCaseName(ip);

            /************************************************/
            /* Check if key-value already exists in HashTable for testName.
            * If 'Yes' then retrieve existing list, add the record into list & then add it back*/
            /************************************************/
            if (topLevelMap.containsKey(testName)) {
                List<String> existingList = topLevelMap.get(testName);
                topLevelMap.remove(testName);
                existingList.add(ip);
                topLevelMap.put(testName, existingList);
            } else {
                 topLevelMap.computeIfAbsent(testName, k -> new ArrayList<>()).add(ip);
            }
        }
        else if (ip.equals(TEST_STATUS_MODEL.RESULT_PASS.getItem())) {
            Utils.FINAL_STATUS = "PASS";
        } else if (ip.equals(TEST_STATUS_MODEL.RESULT_FAIL.getItem())) {
            Utils.FINAL_STATUS = "FAIL";
        } else if (ip.contains(TEST_STATUS_MODEL.START)) {
            Utils.TEST_SUITE_NAME = ip.split(TEST_STATUS_MODEL.START.getItem())[1];
        }else if(!ip.contains(TEST_STATUS_MODEL.START)){
            errorMsgsList.add(ip);
        }
    }

    private String generateTestCaseName(final String ip) {
        TestCaseName = TEST_PREFIX + ip.split(TEST_PREFIX)[1];
        TestCaseName= TestCaseName.split(" ")[0];
        return TestCaseName;
    }

    private Hashtable<String, Integer> iterateOverMapRecords(final String key, final List<String> value) {
        Hashtable<String, Integer> passFailTable = new Hashtable<>();
        AtomicInteger LOCAL_SKIP_COUNT = new AtomicInteger();
        AtomicInteger LOCAL_FAIL_COUNT = new AtomicInteger();
        AtomicInteger LOCAL_PASS_COUNT = new AtomicInteger();
        AtomicInteger LOCAL_TEST_STEP_COUNT = new AtomicInteger();

        value.forEach(ip -> {
                if (ip.contains(TEST_STATUS_MODEL.RUN)) {
                    Utils.TEST_PACK_COUNT++;
                } else if (ip.contains(TEST_STATUS_MODEL.PASS)) {
                    Utils.GLOBAL_TESTS_OCCURANCE_COUNT++;
                    LOCAL_PASS_COUNT.getAndIncrement();
                    LOCAL_TEST_STEP_COUNT.getAndIncrement();
                    extractExeTimeAndIncrement(ip);
                } else if (ip.contains(TEST_STATUS_MODEL.FAIL)) {
                    Utils.GLOBAL_TESTS_OCCURANCE_COUNT++;
                    LOCAL_FAIL_COUNT.getAndIncrement();
                    LOCAL_TEST_STEP_COUNT.getAndIncrement();
                    extractExeTimeAndIncrement(ip);
                } else if (ip.contains(TEST_STATUS_MODEL.SKIP)) {
                    Utils.GLOBAL_TESTS_OCCURANCE_COUNT++;
                    LOCAL_SKIP_COUNT.getAndIncrement();
                    LOCAL_TEST_STEP_COUNT.getAndIncrement();
                    extractExeTimeAndIncrement(ip);
                }//end else if

            passFailTable.put(TEST_COUNTS_MODEL.PASS_COUNT.getItem(), LOCAL_PASS_COUNT.intValue() );
            passFailTable.put(TEST_COUNTS_MODEL.FAIL_COUNT.getItem(),LOCAL_FAIL_COUNT.intValue() );
            passFailTable.put(TEST_COUNTS_MODEL.SKIP_COUNT.getItem(),LOCAL_SKIP_COUNT.intValue() );
            passFailTable.put(TEST_COUNTS_MODEL.TEST_STEP_COUNT.getItem(),LOCAL_TEST_STEP_COUNT.intValue() );


        });

        System.out.println("************RECORD*******************");
        System.out.println(": Record Key: " + key);
        System.out.println("+++Local Pass->" + LOCAL_PASS_COUNT.toString());
        System.out.println("+++Local Fail->" + LOCAL_FAIL_COUNT.toString());
        System.out.println("+++Local SKIP->" + LOCAL_SKIP_COUNT.toString());
        System.out.println("+++Local TEST STEP COUNT->" + LOCAL_TEST_STEP_COUNT.toString());
        return passFailTable;
    }

    //TODO: To remove this
    @Deprecated
    private void doIndividualTestCount(final String ip) {


        if (ip.contains(TEST_STATUS_MODEL.START.getItem())) {
            Utils.TEST_SUITE_NAME = ip.split(TEST_STATUS_MODEL.START.getItem())[1];
        } else if (ip.contains(TEST_STATUS_MODEL.RUN)) {

        } else if (ip.contains(TEST_STATUS_MODEL.PASS)) {
            Utils.GLOBAL_TESTS_OCCURANCE_COUNT++;
            Utils.PASS_COUNT++;
            extractExeTimeAndIncrement(ip);
        } else if (ip.contains(TEST_STATUS_MODEL.FAIL)) {
            Utils.GLOBAL_TESTS_OCCURANCE_COUNT++;
            Utils.FAIL_COUNT++;
            extractExeTimeAndIncrement(ip);
        } else if (ip.contains(TEST_STATUS_MODEL.SKIP)) {
            Utils.GLOBAL_TESTS_OCCURANCE_COUNT++;
            Utils.SKIP_COUNT++;
            extractExeTimeAndIncrement(ip);
        } else if (ip.equals(TEST_STATUS_MODEL.RESULT_PASS.getItem())) {
            Utils.FINAL_STATUS = "PASS";
        } else if (ip.equals(TEST_STATUS_MODEL.RESULT_FAIL.getItem())) {
            Utils.FINAL_STATUS = "FAIL";
        }
    }

    private void extractExeTimeAndIncrement(final String testStr) {
        double outputValue = 0.00;
        Matcher m = Pattern.compile(REGEX_SECONDS_FRACTION).matcher(testStr);
        if (m.find()) {
            outputValue = Double.parseDouble(m.group(1).replaceAll("s", ""));
        }
        incrementExeTime(outputValue);
    }

    private void incrementExeTime(final double timeIp) {
        Utils.EXECUTION_TIME += timeIp;
    }

}

