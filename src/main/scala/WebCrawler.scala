package WebCrawler

import HttpConnector._
import WebCrawlerEngine.WebCrawlerEngine
import Writer.Writer
import java.io._

import scala.util.{Failure, Success, Try}

object WebCrawler {
  def main(args: Array[String]): Unit = {
    Try {
      val (seed, depth) = getArguments(args)
      val connector = new HttpConnector()
      val engine = new WebCrawlerEngine(seed, connector)
      val sitemap = engine.crawlFromSeed(depth)
      val file = new PrintWriter(new File("sitemap.txt"))
      val writer = new Writer(file, seed, sitemap)
      writer.writeSitemapToFile()
      file.close()
    } match {
      case Success(result) => println("Complete. Saved output to sitemap.txt")
      case Failure(error) => println(error)
    }
  }

  private def getArguments(args: Array[String]): (String, Int) = {
    if (args.size == 2) {
      val seed = args(0)
      val depth = args(1).toInt
      (seed, depth)
    }
    else if (args.size == 1) {
      val seed = args(0)
      val depth = Int.MaxValue
      (seed, depth)
    }
    else {
      println("*************************************************************************************************")
      println("* WebCrawler CLI can be used with 1 or 2 arguments                                               *")
      println("* 1st argument is the seed                                                                     *")
      println("* 2nd argument is the depth, if not set the crawling will continue until all URLs are processed *")
      println("*************************************************************************************************")
      throw new Exception("Too many or not enough arguments")
    }

  }
}