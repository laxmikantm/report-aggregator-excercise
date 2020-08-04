package main.java.core;

import java.util.HashMap;

public class TestCharacteristics {

    HashMap<String, HashMap<String , String>> testControlData = new HashMap<>();

    public HashMap<String, HashMap<String, String>> getTestControlData() {
        return testControlData;
    }

    public void setTestControlData(HashMap<String, HashMap<String, String>> testControlData) {
        this.testControlData = testControlData;
    }



}
