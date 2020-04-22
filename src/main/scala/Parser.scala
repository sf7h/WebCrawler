package Parser

import org.htmlcleaner.TagNode

object Parser {
  def getLinks(rootNode: TagNode): Seq[TagNode] = {
    val links = rootNode.getElementsByName("a", true)
    links
  }
}