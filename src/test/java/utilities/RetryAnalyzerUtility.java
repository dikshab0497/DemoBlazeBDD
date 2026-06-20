package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzerUtility implements IRetryAnalyzer {

    private static final Logger logger = LogManager.getLogger(RetryAnalyzerUtility.class);
    private static final int MAX_RETRY = 2;
    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY) {
            retryCount++;
            logger.warn("[RETRY] Retrying failed test: '{}' | Attempt: {}/{}",
                    result.getName(), retryCount, MAX_RETRY);
            return true;
        }
        logger.error("[RETRY] Test '{}' failed after {} attempts", result.getName(), MAX_RETRY);
        return false;
    }
}