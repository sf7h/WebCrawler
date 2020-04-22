import java.io._

import HttpConnector._
import WebCrawlerEngine._
import org.htmlcleaner.{HtmlCleaner, TagNode}
import org.scalatest.FunSuite


class WebCrawlerEngineTest extends FunSuite {
  test("should test if the web crawler engine returns all the urls in a file") {
    val (seed, depth) = ("https://salwa.dev", 1)
    val connector = new MockHttpConnector()
    val engine = new WebCrawlerEngine(seed, connector)
    val sitemap = engine.crawlFromSeed(depth)
    assert(sitemap.size == 1)
  }
}

class MockHttpConnector extends HttpConnector {
  override def getContent(url: String) = {
    if (url == "https://salwa.dev") {
      val cleaner = new HtmlCleaner
      cleaner.clean(new File("/Users/salwafathallah/Code/Crawler/src/pageSource.txt"))
    }
    else
      new TagNode("")
  }
}