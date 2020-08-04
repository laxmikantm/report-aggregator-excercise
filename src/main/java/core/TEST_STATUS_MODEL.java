package main.java.core;

public enum TEST_STATUS_MODEL implements CharSequence {
    
        START("START"),
        RUN("=== RUN"),
        PASS("--- PASS:"),
        FAIL("--- FAIL:"),
        SKIP("--- SKIP:"),
        RESULT_PASS("PASS"),
        RESULT_FAIL("FAIL");

        private final String item;

        TEST_STATUS_MODEL(String item) {
            this.item = item;
        }

        public String getItem() {
            return item;
        }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }
}
