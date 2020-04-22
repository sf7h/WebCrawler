package WebCrawlerEngine

import java.util.concurrent.{ConcurrentHashMap, ConcurrentLinkedQueue}

import HttpConnector._
import Parser.Parser

import scala.collection.JavaConverters._
import scala.collection.concurrent.Map
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

class WebCrawlerEngine(domainName: String, connector: HttpConnector) {
  private val queue = new ConcurrentLinkedQueue[String]()
  private val visitedUrls = new ConcurrentHashMap[String, Unit]().asScala
  private val sitemap = new ConcurrentHashMap[String, Seq[String]]().asScala
    
  def crawlFromSeed(depth: Int): Map[String, Seq[String]] = {
    enqueueUrl(domainName)
    println("Crawling: " + domainName)
    var level = new java.util.concurrent.atomic.AtomicInteger(0)

    while (!queue.isEmpty && level.get <= depth) {
      level.incrementAndGet()
      val seedUrl = queue.poll()
      val processChildrenFutures = crawlChildren(seedUrl)
      Future.sequence(processChildrenFutures).andThen( );
    }

    sitemap
  }

  private def crawlChildren(seedUrl: String): Seq[Future[Unit]] = {
    val rootNode = connector.getContent(seedUrl)
    val children = Parser.getLinks(rootNode)
    var futures: Seq[Future[Unit]] = Seq[Future[Unit]]()

    for (child <- children) {
      futures = futures :+ Future {
        processChildUrl(seedUrl, child.getAttributeByName("href"))
      }
      Thread.sleep(0, 1)
    }

    futures
  }

  private def processChildUrl(seedUrl: String, link: String) = {
    val childUrl = domainName + link
    if (isNotNullAndIsInSameDomain(link) && !visitedUrls.contains(childUrl)) {
      enqueueUrl(childUrl)
      markUrlAsVisited(childUrl)
      addChildUrlToSitemap(childUrl, seedUrl)
    }
  }

  private def isNotNullAndIsInSameDomain(url: String): Boolean = {
    url != null && !url.isEmpty() && url.head == '/'
  }

  private def enqueueUrl(url: String): Unit = {
    queue.add(url)
  }

  private def markUrlAsVisited(url: String): Unit = {
    visitedUrls.put(url, None)
  }

  private def addChildUrlToSitemap(childUrl: String, seedUrl: String): Unit = {
    if (sitemap.contains(seedUrl)) {
      val newList = sitemap(seedUrl) :+ childUrl
      sitemap(seedUrl) = newList
    }
    else
      sitemap += (seedUrl -> Seq(childUrl))
  }
}