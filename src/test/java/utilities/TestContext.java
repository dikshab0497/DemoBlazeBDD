package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestContext {

    private static final Logger logger = LogManager.getLogger(TestContext.class);

    private static Throwable lastError;

    public static Throwable getLastError() {
        return lastError;
    }

    public static void setLastError(Throwable error) {
        lastError = error;
        if (error != null) {
            logger.error("[TEST-CONTEXT] Error captured: " + error.getMessage(), error);
        }
    }

    public static void reset() {
        logger.info("[TEST-CONTEXT] Resetting lastError");
        lastError = null;
    }
}