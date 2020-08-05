package test.java;

import main.java.core.ConsolidateReports;
import main.java.core.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class TestSuite {

    private ConsolidateReports crs;
    private final String fileName;

    public TestSuite(String fileName){
        super();
        this.fileName = fileName;

    }

    @Before
    public void fileReadOperation(){
        crs = new ConsolidateReports();
        crs.readTestFile(fileName);

    }

    @Test
    public void valiadateTestCaseNames(){
        List<String> expectedKeys = Arrays.asList(
                "TestAcquireRepoLock",
                "TestReadLastOperation"
        );

        Enumeration<String> keys = crs.getTestControlData().keys();
        List<String> keysSet = Collections.list(keys);
        Collections.sort(keysSet);
        Collections.sort(expectedKeys);

        Assert.assertEquals("Error: keys not as per expected", expectedKeys, keysSet);
    }

    @Test
    public void checkPassFailNode() {
        Hashtable<String,Integer> table = new Hashtable<String,Integer>(){
            {
                put ("PASS_COUNT", 2);
                put("FAIL_COUNT", 0);
                put("SKIP_COUNT", 0);
                put("TEST_STEP_COUNT", 2);
            }
        };
        Assert.assertTrue("Error: Pass/Fail count doesn't tally.",
                table.equals(crs.getTestControlData().get("TestAcquireRepoLock")));

        table = new Hashtable<String,Integer>(){
            {
                put("PASS_COUNT", 1);
                put("FAIL_COUNT", 1);
                put("SKIP_COUNT", 0);
                put("TEST_STEP_COUNT", 2);
            }
        };

        Assert.assertTrue("Error: Pass/Fail count doesn't tally.",
                table.equals(crs.getTestControlData().get("TestReadLastOperation")));

    }

    @Test
    public void validateTotalExecutionTime() {
        double EXPECTED_EXECUTION_TIME= 0.0;

        Assert.assertThat("Error: Total Time execution is not correct.",
                Utils.EXECUTION_TIME, equalTo(EXPECTED_EXECUTION_TIME));
    }

    @Test
    public void validateFinalTestStatus() {
        String EXPECTED_FINAL_STATUS = "PASS";

        Assert.assertThat("Error: Test Suite's final test status doesn't match.",
                Utils.FINAL_STATUS, equalTo(EXPECTED_FINAL_STATUS));
    }

    @Test
    public void validateTestSuiteName() {
        String TEST_SUITE_NAME = "//src/core:lock_test";

        Assert.assertThat(" Error: Test Suite name doesn't match. ",
                Utils.TEST_SUITE_NAME.trim() , equalTo(TEST_SUITE_NAME));
    }

    @Parameterized.Parameters
    public static Collection input() {
        return Arrays.asList(new Object[][]{
                { "lock_test_2.txt" }
        });
    }

}
