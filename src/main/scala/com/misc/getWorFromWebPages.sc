
import scala.io.Source

class PageReader() {

  val fileEquals = """[a-zA-Z]{1}:.+""".r
  val httpEquals = """http[s]*://.+""".r

  def readFile(uri: String): Option[Iterator[String]] = uri match {
    case fileEquals() =>
      try {
        Some(Source.fromFile(uri, "utf-8").getLines())
      }
      catch {
        case e: Exception => None
      }

    case httpEquals() =>
      try {
        Some(Source.fromURL(uri, "utf-8").getLines())
      } catch {
        case e: Exception => None
      }
    case _ => {
      None
    }
  }

  def split(it: Option[List[String]]): List[Char] = {
    val filtered = it.reduce((a, b) => (a + "" + b))
    filtered
      .replaceAll("<\\/?[a-z][a-z0-9]*[^<>]*>|<!--.*?-->", "")
      .replaceAll("<script.*>.*<\\/script>", "")
      .replaceAll("[^a-zA-Z\\s]", "")
    filtered.toStream.toList
  }
}


val wc = new PageReader()
val it = wc.readFile("https://en.wikipedia.org/wiki/Scala_(programming_language)")
  it.groupBy(identity)
  .mapValues(_.size)