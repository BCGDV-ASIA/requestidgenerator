package com.bcgdv;

/**
 * Request ID Generator tests
 */

public class RequestIdGeneratorTest {

    /**
     * Prints a series of test IDs
     */
    @org.junit.Test
    public void testAndroidGeneratorWithUniqueIds() {
        sample();
        printMe(
                new AndroidRequestIdGenerator(
                        AndroidRequestIdGenerator.Environment.DEV,
                        RequestIdGenerator.generateInstallationId(),
                        "12"
                )
        );

        printMe(
                new AndroidRequestIdGenerator(
                        AndroidRequestIdGenerator.Environment.TEST,
                        RequestIdGenerator.generateInstallationId(),
                        "45"
                )
        );

        printMe(
                new AndroidRequestIdGenerator(
                        AndroidRequestIdGenerator.Environment.PROD,
                        RequestIdGenerator.generateInstallationId(),
                        "2"
                )
        );
    }

    /**
     * Android IDs
     */
    @org.junit.Test
    public void testAndroidGeneratorEmpty() {
        System.out.println(
                new AndroidRequestIdGenerator(
                        RequestIdGenerator.Environment.DEV,
                        RequestIdGenerator.generateInstallationId(),
                        "2345").empty());
    }

    /**
     * Random IDs
     */
    @org.junit.Test
    public void testGeneratorWithUniqueIds() {
        RequestIdGenerator rig = new RequestIdGenerator();
        printMe(rig);
    }

    /**
     * Empty Generator with default IDs
     */
    @org.junit.Test
    public void testGeneratorEmpty() {
        System.out.println(new RequestIdGenerator(RequestIdGenerator.Environment.DEV, RequestIdGenerator.generateInstallationId(), "2345").empty());
        System.out.println(new RequestIdGenerator().empty());
    }

    /**
     * None of the IDs in sequence should be the same
     * @param rig
     */
    protected void printMe(RequestIdGenerator rig) {
        for (int i = 0; i < 3; i++) {
            System.out.println(rig.unique());
        }
    }

    /**
     * The markup of a unique ID
     */
    protected void sample() {
        System.out.println(
        "\n                                      ┏ build number\n" +
        "                                      ┃\n" +
        "    ┏ 16 byte installation ID         ┃  ┏ \"R\" for request UUID\n" +
        "    ┃                                 ┃  ┃\n" +
        " X-Dab255bf524ed445e89f5c8b2efb3a2e5-A12-R8c0a9b6a-92af-49ad-a5b7-e5f02d67d4d4\n" +
        " ┃ ┃                                 ┃    ┃\n" +
        " ┃ ┗ \"D\" for dev environment         ┃    ┗ request UUID\n" +
        " ┃                                   ┃\n" +
        " ┗ \"X\" beginning of X-Request-ID     ┗ \"A\" for android\n"
        );
    }
}
