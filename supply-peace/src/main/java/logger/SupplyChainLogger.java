package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by prayagupd
 * on 9/17/16.
 */

public class SupplyChainLogger {
    public static Logger logger = LogManager.getLogger(SupplyChainLogger.class);

    public static void main(String[] args) {
        logger.debug("I'm Hunter Thomson");
        logger.debug("Exception occured ", new Exception("some exception"));
    }
}
