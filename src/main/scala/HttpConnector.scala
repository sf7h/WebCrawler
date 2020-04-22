package HttpConnector

import java.net.{HttpURLConnection, URL, URLConnection}

import org.htmlcleaner.{HtmlCleaner, TagNode}

class HttpConnector {
  private def connect(url: String): URLConnection = {
    val connection = new URL(url).openConnection
    val requestProperties = Map(
      "User-Agent" -> "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)"
    )
    requestProperties.foreach({
      case (name, value) => connection.setRequestProperty(name, value)
    })
    connection
  }

  def getContent(url: String) = {
    val connection = connect(url)
    if (connection.asInstanceOf[HttpURLConnection].getResponseCode == HttpURLConnection.HTTP_OK) {
      val cleaner = new HtmlCleaner
      val rootNode = cleaner.clean(connection.getInputStream)
      rootNode
    }
    else
      new TagNode("")
  }
}