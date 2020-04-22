package Writer

import java.io._

import scala.collection.mutable.{Set, Stack}

class Writer(pw: PrintWriter, seedUrl: String, sitemap: scala.collection.concurrent.Map[String, Seq[String]]) {
  def writeSitemapToFile(): Unit = {
    val dfsQueue = Stack[String]()
    dfsQueue.push(seedUrl)
    val visitedUrls = Set[String]()
    var level = 0
    writeToFileByLevel(seedUrl, visitedUrls, level)
  }

  private def writeToFileByLevel(url: String, visitedUrls: Set[String], level: Int): Unit = {
    writeUrlToFile(url, level)
    visitedUrls += url
    if (sitemap.contains(url)) {
      for (child <- sitemap(url)) {
        if (!visitedUrls.contains(child)) {
          writeToFileByLevel(child, visitedUrls, level + 1)
        }
      }
    }
  }

  private def writeUrlToFile(url: String, level: Int): Unit = {
    1 to level foreach { _ => pw.write("   ") }
    pw.write("|\n")
    1 to level foreach { _ => pw.write("   ") }
    pw.write("|")
    pw.write("---")
    pw.write("> " + url + "\n")
  }
}