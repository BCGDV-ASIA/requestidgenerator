/*
 * AndroidRequestIdGenerator
 */
package com.bcgdv;

/**
 * <pre>
 * AndroidRequestIdGenerator creates HTTP Request IDs for Android. Use a singleton
 * instance per app.
 *
 * Environment ("dev", "test" or "prod") should be fetched
 * from gradle build configuration and set accordingly.
 *
 * InstallationId is a unique identifier for each Android app instance and
 * should be stored on the device per installation, per environment, i.e if
 * both a "dev" and "test" environment app are installed they must not overwrite
 * each other's installationId. Value should however survive application upgrade
 * and/or restore from backup.
 *
 * BuildNumber is generally provided by a CI server such as Jenkins.
 *
 *
 * Sample ID below:
 * {@code
 *                                      ┏ build number
 *                                      ┃
 *    ┏ 16 byte installation ID         ┃  ┏ "R" for request ID
 *    ┃                                 ┃  ┃
 * X-Dab255bf524ed445e89f5c8b2efb3a2e5-A12-R8c0a9b6a-92af-49ad-a5b7-e5f02d67d4d4
 * ┃ ┃                                 ┃    ┃
 * ┃ ┗ "D" for dev environment         ┃    ┗ request UUID
 * ┃                                   ┃
 * ┗ "X" beginning of X-Request-ID     ┗ "A" for android
 *
 * }
 * </pre>
 *  Simon Mittag
 */
public class AndroidRequestIdGenerator extends RequestIdGenerator {

    /**
     * Shortcode for Android platform
     */
    protected static final String ANDROID_PLATFORM = "A";

    /**
     * Create an AndroidRequestIdGenerator with Environment and buildNumber. This is now deprecated, use
     * @see #AndroidRequestIdGenerator(RequestIdGenerator.Environment, String, String) instead.
     * @param environment the environment, @see #RequestIdGenerator.Environment
     * @param buildNumber the build number, supplied by CI such as Jenkins. Remains constant for all request IDs
     */
    @Deprecated
    public AndroidRequestIdGenerator(Environment environment, String buildNumber) {
        super(environment, RequestIdGenerator.DEFAULT_INSTALLATION_ID, buildNumber);
    }

    /**
     * Create an AndroidRequestIdGenerator with Environment, installationid and buildNumber
     * @param environment the environment, @see #RequestIdGenerator.Environment
     * @param installationId a unique identifier for the current installation, i.e. a UUID.
     * @param buildNumber the build number, supplied by CI such as Jenkins. Remains constant for all request IDs
     */
    public AndroidRequestIdGenerator(Environment environment, String installationId, String buildNumber) {
        super(environment, installationId, buildNumber);
    }

    /**
     * Returns shortcode for Android platform
     * @return the shortcode
     */
    @Override
    protected String getPlatform() {
        return ANDROID_PLATFORM;
    }
}
