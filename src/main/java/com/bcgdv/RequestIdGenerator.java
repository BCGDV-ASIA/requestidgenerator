package com.bcgdv;

import java.util.UUID;

/**
 * Generate HTTP Request IDs
 *  Simon Mittag
 */
public class RequestIdGenerator {

    /**
     * Supply X-Request-ID as HTTP header
     */
    public static String X_REQUEST_ID = "X-Request-ID";

    /**
     * We support DEV, TEST, and PROD environments with shortcodes.
     */
    public enum Environment{
        DEV, TEST, PROD;

        protected static final String DEVELOPMENT_ENVIRONMENT = "D";
        protected static final String TEST_ENVIRONMENT = "T";
        protected static final String PRODUCTION_ENVIRONMENT = "P";

        @Override
        public String toString() {
            if(this==DEV) return DEVELOPMENT_ENVIRONMENT;
            if(this==TEST) return TEST_ENVIRONMENT;
            if(this==PROD) return PRODUCTION_ENVIRONMENT;
            return DEVELOPMENT_ENVIRONMENT;
        }
    }

    /**
     * Default 16 byte installation ID
     */
    protected static final String DEFAULT_INSTALLATION_ID = "0";

    /**
     * Default 6 digit build number
     */
    protected static final String DEFAULT_BUILD_NUMBER = "0";

    /**
     * The default environment is "D" for development
     */
    protected static final String DEFAULT_ENVIRONMENT = "D";

    /**
     * The default platform is "S" for server-side
     */
    protected static final String DEFAULT_PLATFORM = "S";

    /**
     * Constants and expressions we need to create IDs faster.
     */
    protected static final String REGEX_NUMBERS = "[0-9]";
    protected static final String REGEX_LETTERS = "[a-z]";
    protected static final String ZERO = "0";
    protected static final String X = "X";
    protected static final String R = "R";
    protected static final String DASH = "-";
    protected static final String EMPTY = "";

    /**
     * The unique build number.
     */
    protected String buildNumber;

    /**
     * The unique installation id
     */
    protected String installationId;

    /**
     * The environment
     */
    protected Environment environment;

    /**
     * used internally for creating IDs
     */
    private String base;

    /**
     * Create a default RequestIdGenerator. This should only be used to substitute for Ids that were not properly
     * generated, i.e. as a default logging template and is otherwise deprecated.
     */
    public RequestIdGenerator() {
        this.buildNumber = DEFAULT_BUILD_NUMBER;
        this.installationId = DEFAULT_INSTALLATION_ID;
        this.environment=Environment.DEV;
        this.base = getBase();
    }

    /**
     * Create a default RequestidGenerator with installationId and buildNumber.
     * @param environment the environment, @see RequestIdGenerator.Environment
     * @param installationId a unique identifier for the current installation, i.e. a UUID.
     * @param buildNumber The build number, supplied by CI such as Jenkins. Remains constant for all request IDs
     */
    public RequestIdGenerator(Environment environment, String installationId, String buildNumber) {
        this.environment = environment;
        this.installationId = installationId;
        this.buildNumber = buildNumber;
        this.base = getBase();
    }

    /**
     * Generates a unique installation ID. This value should be stored by the client and should remain constant, even
     * after updates of the software. It uniquely identifies the client installation.
     * @return a 16 byte String with the installation ID
     */
    public static String generateInstallationId() {
        return UUID.randomUUID().toString().replaceAll(DASH, EMPTY);
    }

    /**
     * Generates a unique request Id as String.
     * @return the request id
     */
    public String unique() {
        StringBuilder sb = new StringBuilder();
        sb.append(base);
        sb.append(UUID.randomUUID());
        return sb.toString();
    }

    /**
     * Generates an empty request id
     * @return the request id
     */
    public String empty() {
        StringBuilder sb = new StringBuilder();
        sb.append(base);
        sb.append(UUID.randomUUID().toString().replaceAll(REGEX_NUMBERS, ZERO).replaceAll(REGEX_LETTERS, ZERO));
        return sb.toString();
    }

    /**
     * Creates a base StringBuilder for internal use.
     * @return base StringBuilder
     */
    protected String getBase() {
        StringBuilder sb = new StringBuilder();
        sb.append(X);
        sb.append(DASH);
        sb.append(this.getEnvironment());
        sb.append(this.getInstallationID());
        sb.append(DASH);
        sb.append(getPlatform());
        sb.append(buildNumber);
        sb.append(DASH);
        sb.append(R);
        return sb.toString();
    }

    /**
     * Get the current installation ID. Identifies a client of RequestIdGenerator
     * @return the installation ID.
     */
    protected String getInstallationID() {
        return this.installationId;
    }

    /**
     * Get the current environment.
     * @return the environment
     */
    protected String getEnvironment() {
        return this.environment.toString();
    }

    /**
     * Get the current platform.
     * @return the platform.
     */
    protected String getPlatform() {
        return DEFAULT_PLATFORM;
    }
}
