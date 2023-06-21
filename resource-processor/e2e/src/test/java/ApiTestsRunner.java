public class ApiTestsRunner {

    private void runWithoutContainerization() {
        new ResourceProcessorTests().runAll();
    }

    public static void main(String[] args) {
        boolean useContainerization = Boolean.parseBoolean(System.getProperty("test.containerization"));
        ApiTestsRunner testRunner = new ApiTestsRunner();
        if (!useContainerization) {
            testRunner.runWithoutContainerization();
        }
    }
}
