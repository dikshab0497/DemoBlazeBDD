package utilities;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

public class DataProviders {

    private static final Logger logger = LogManager.getLogger(DataProviders.class);
    private static final String LOGIN_DATA_PATH = ".\\testData\\Opencart_LoginData.xlsx";

    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {
        logger.info("[DATA-PROVIDER] Loading LoginData from: " + LOGIN_DATA_PATH);

        ExcelUtility xlutil = new ExcelUtility(LOGIN_DATA_PATH);

        int totalRows = xlutil.getRowCount("Sheet1");
        int totalCols = xlutil.getCellCount("Sheet1", 1);

        logger.info("[DATA-PROVIDER] Rows found: " + totalRows + " | Columns found: " + totalCols);

        String[][] loginData = new String[totalRows][totalCols];

        for (int i = 1; i <= totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                loginData[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
            }
        }

        logger.info("[DATA-PROVIDER] LoginData loaded successfully. Total records: " + totalRows);
        return loginData;
    }
}