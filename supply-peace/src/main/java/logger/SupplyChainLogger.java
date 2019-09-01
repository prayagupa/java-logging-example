package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Created by prayagupd
 * on 9/17/16.
 */

public class SupplyChainLogger {

    private static final Logger logger = LogManager.getLogger(SupplyChainLogger.class);

    private Setup setup = new Setup();

    public static void main(String[] args) {

        System.out.println(System.getProperty("something"));


        logger.debug("I'm Hunter Thomson - " + LocalDate.now().toString());
        logger.debug("Exception occured ", new Exception("some exception"));

        System.out.println("logging debug enabled: " + logger.isDebugEnabled());

        while(true) {
            //logger.info("Process is running");
        }
    }
}
