package main.java.core;

public enum TEST_COUNTS_MODEL {

        PASS_COUNT("PASS_COUNT"),
        SKIP_COUNT("SKIP_COUNT"),
        FAIL_COUNT("FAIL_COUNT"),
        TEST_STEP_COUNT("TEST_STEP_COUNT");

        private final String item;

        TEST_COUNTS_MODEL(String item) {
            this.item = item;
        }

        public String getItem() {
            return item;
        }

}
