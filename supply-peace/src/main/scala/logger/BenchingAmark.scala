package logger

import java.util
import org.apache.logging.log4j.{LogManager, Logger}

/**
  * Created by prayagupd
  * on 12/21/16.
  */
object BenchingAmark {

  val data = "<note><to>Sylvia</to><from>Prayag</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>"

  val CONCURRENT_REQUESTS: Int = 1
  val EACH_REQUEST_ITERATIONS: Int = 1
  val EACH_JOKER_ITERATIONS: Int = 1

  val concurrentLoggers: util.List[BenchmarkThread] =
    new util.ArrayList[BenchmarkThread]

  @throws[InterruptedException]
  def jokerStartsLaughing() {
    println(s"logging mode=${System.getProperty("Log4jContextSelector")}")
    val logger: Logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME)

    for (requestId <- 1 to CONCURRENT_REQUESTS) {
      concurrentLoggers.add(new BenchmarkThread("ConcurrentRequest_" + requestId, EACH_REQUEST_ITERATIONS, logger))
    }

    logger.debug("-----------------------------------------------------------")
    logger.debug("----------------------  Joker -----------------------------")
    logger.debug("-----------------------------------------------------------")

    jokerDoesBenchmark("Joker_As_Writer", EACH_JOKER_ITERATIONS, logger)

    Thread.sleep(100)

    logger.debug("-----------------------------------------------------------")
    logger.debug("---------------------  Bench a Mark -----------------------")
    logger.debug("-----------------------------------------------------------")

    for (i <- 0 until CONCURRENT_REQUESTS) {
      concurrentLoggers.get(i).start()
    }
    Thread.sleep(100)
    logger.debug("-----------------------------------------------------------")
    logger.debug("----------------------  Batman  ---------------------------")
    logger.debug("-----------------------------------------------------------")
  }

  protected class BenchmarkThread(val name: String, val iteration: Int, val logger: Logger) extends Thread {
    override def run() {
      jokerDoesBenchmark(name, iteration, logger)
    }
  }

  protected def jokerDoesBenchmark(name: String, iterations: Int, logger: Logger) {
    for (i <- 1 to iterations) {
      if(i == iterations) {
        println(i + " -> " + iterations)
      }
      logger.info("{}:request {} - {}", name, i, data)
    }
  }
}