import logger.BenchingAmark
import logger.BenchingAmark._
import org.json.JSONObject
import org.scalatest.FunSuite

import scala.io.Source._

/**
 * Created by prayagupd
 * on 9/17/16.
 */

class JokerSpecs extends FunSuite {

  test("thread not safe should write valid json"){
    val lines = fromFile("async_rolled_threadnotsafe.log").getLines().toList
    lines.foreach(line => {
      val json = new JSONObject(line)
      assert(json.isInstanceOf[JSONObject])
    })
  }

  test("thread not safe should write CONCURRENT_REQUESTS * EACH_REQUEST_ITERATIONS+11 logs"){
    val lines = fromFile("async_rolled_threadnotsafe.log").getLines().toList.size

    assert(lines == (CONCURRENT_REQUESTS * EACH_REQUEST_ITERATIONS)+11 + EACH_JOKER_ITERATIONS)
  }

  test("threadsafe should write 32*200+12 logs"){
    val lines = fromFile("async_rolled_threadsafe.log").getLines().toList.size
    assert(lines == (CONCURRENT_REQUESTS * EACH_REQUEST_ITERATIONS)+11 + EACH_JOKER_ITERATIONS)
  }

  test("threadsafe should write proper json writes"){
    var count = 0
    fromFile("logging12.log").getLines().foreach(line => {
      val json = new JSONObject(line)
      assert(json.isInstanceOf[JSONObject])
      count = count + 1
    })
    println(count)
  }
}
