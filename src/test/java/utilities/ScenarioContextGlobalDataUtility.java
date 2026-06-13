package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScenarioContextGlobalDataUtility {

    private static final Logger logger = LogManager.getLogger(ScenarioContextGlobalDataUtility.class);

    private static int expectedTotal;

    public static int getExpectedTotal() {
        return expectedTotal;
    }

    public static void setExpectedTotal(int total) {
        logger.info("[SCENARIO-CONTEXT] Setting expectedTotal: " + total);
        expectedTotal = total;
    }

    public static void reset() {
        logger.info("[SCENARIO-CONTEXT] Resetting expectedTotal to 0");
        expectedTotal = 0;
    }
}