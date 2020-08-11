# Simple Test Report Aggregator 


## Assumptions:

1. Input file should contain single START
2. On one line not more than one of START ```/ --- PASS:/ --- FAIL: / -- SKIP:``` keyword should be present
3. "=== RUN" is not counted as Test Step.
4. TestCase should start with word "Test" 
5. Standlone "PASS" or "FAIL" will be counted as overall test Suite status

### How to run Excercise App:

1. Install maven dependencies
2. Go to main/java/core/Runner.java
3. Right click & click on Run option [Kindly refer snapshot provided at the bottom of this page]
4. Provide input of the test files either using number 1/2/3/4 or write full file name viz- "rewrite_test" / "rewrite_test.txt" etc
5. Output will be generated on output prompt of IDE [Kindly refer snapshot provided at the bottom of this page]

## How to run Tests:
1. Go to src/test/java/test/java/TestSuite.java
2. Right click & click on Run option


Thanks a lot

## Illustration of the data structure used to slice the data

![image](lib/Illustration/Img1.png)


![image](lib/Illustration/Img2.png)

## Future Improvements:

1. Remove Hardcoupling and Decouple functions into Modular format, so that any side effects can be avoided.
And testing functions in isolation will become possible.
2. Provide dynamic test data to tests
3. Improve regex
4. Handle Blanks and unexpected inputs
5. Fix commandline issue so that tests can be triggered from commandline



## Run Illustration

![image](lib/Illustration/Run-Output.png)

![image](lib/Illustration/Run-run.png)


