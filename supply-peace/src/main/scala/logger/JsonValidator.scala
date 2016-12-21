package logger

import java.io.File

import com.fasterxml.jackson.databind.util.JSONPObject
import org.json.JSONObject

import scala.io.Source._

/**
  * Created by prayagupd
  * on 12/21/16.
  */

object JsonValidator {

  def main(args: Array[String]): Unit = {
    validateJson()
  }

  def validateJson(): Unit = {
    new File("async_rolled_threadsafe.log")

    fromFile("async_rolled_threadsafe.log").getLines().foreach(line => {
      val json = new JSONObject(line)
      //println("validated")
    })
  }

  def validatePayson(): Unit = {
    fromFile("async_rolled_threadnotsafe.log").getLines().foreach(line => {
      val json = new JSONObject(line)
      //println("validated")
    })
  }

}
