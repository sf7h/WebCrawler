import java.io._

import Parser._
import org.htmlcleaner.HtmlCleaner
import org.scalatest.FunSuite


class ParserTest extends FunSuite {
  test("should test if the parser returns all the a div in a file") {
    val cleaner = new HtmlCleaner
    val root = cleaner.clean(new File("/Users/salwafathallah/Code/Crawler/src/pageSource.txt"))
    val links = Parser.getLinks(root)
    assert(links.size == 8)
  }
}



