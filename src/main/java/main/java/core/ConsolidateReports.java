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
    private static final List<String> errorMsgsList = new ArrayList<String>();
    Hashtable<String, Hashtable<String, Integer>> testControlData = new Hashtable<>();

    /*****************
     * Method Getters *
     * ****************/
    public Hashtable<String, Hashtable<String, Integer>> getTestControlData() {
        return testControlData;
    }

    public Hashtable<String, List<String>> getTopLevelMap() {
        return topLevelMap;
    }

    public static List<String> getErrorMsgsList() {
        return errorMsgsList;
    }

    public Hashtable<String, Integer> getPassFailDetails(final String name) {
        return getTestControlData().get(name);
    }


    /********************************************************************************
     * :::::High level Logic::::::
     * 1. Read a file
     * 2. Read Test Name and store High level control details of the testcase
     * 3. Iterate and Build PASS, FAIL, SKIP count per TestCase basis
     * 4. Run-time counter
     ********************************************************************************/

    public void readTestFile(final String fileName) {

        final String filePath = System.getProperty("user.dir") + "/data/" + fileName;

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(
                    this::buildTopLevelMap
            );
            topLevelMap.forEach((k, v) -> {
                Hashtable<String, Integer> passFailTable = iterateOverMapRecords(k, v);
                testControlData.put(k, passFailTable);
            });
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
        } else if (ip.equals(TEST_STATUS_MODEL.RESULT_PASS.getItem())) {
            Utils.FINAL_STATUS = "PASS";
        } else if (ip.equals(TEST_STATUS_MODEL.RESULT_FAIL.getItem())) {
            Utils.FINAL_STATUS = "FAIL";
        } else if (ip.contains(TEST_STATUS_MODEL.START)) {
            Utils.TEST_SUITE_NAME = ip.split(TEST_STATUS_MODEL.START.getItem())[1].trim();
        } else if (!ip.contains(TEST_STATUS_MODEL.START)) {
            errorMsgsList.add(ip);
        }
    }

    private String generateTestCaseName(final String ip) {
        TestCaseName = TEST_PREFIX + ip.split(TEST_PREFIX)[1];
        TestCaseName = TestCaseName.split(" ")[0];
        return TestCaseName;
    }

    public Hashtable<String, Integer> iterateOverMapRecords(final String key, final List<String> value) {
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

            passFailTable.put(TEST_COUNTS_MODEL.PASS_COUNT.getItem(), LOCAL_PASS_COUNT.intValue());
            passFailTable.put(TEST_COUNTS_MODEL.FAIL_COUNT.getItem(), LOCAL_FAIL_COUNT.intValue());
            passFailTable.put(TEST_COUNTS_MODEL.SKIP_COUNT.getItem(), LOCAL_SKIP_COUNT.intValue());
            passFailTable.put(TEST_COUNTS_MODEL.TEST_STEP_COUNT.getItem(), LOCAL_TEST_STEP_COUNT.intValue());


        });
        return passFailTable;
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

