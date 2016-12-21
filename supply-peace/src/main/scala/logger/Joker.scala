package logger

import org.apache.logging.log4j.{LogManager, Logger}

/**
  * Created by prayagupd
  * on 9/17/16.
  */

object Joker {
  System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector")
  val logger = LogManager.getLogger("benchMarkLogger")

  def main(args: Array[String]) {

    println(s"logging debug enables = ${logger.isDebugEnabled}")
    BenchingAmark.jokerStartsLaughing()
    println(s"something=${System.getProperty("something")}")
    logger.debug("I'm Hunter Thomson and I'm alive.")
    logger.debug("Peace is failing ", new Exception("I'm Lunatic exception"))

  }
}
