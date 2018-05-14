package hama.misc

import scala.io.Source

class PageReader(){

  val fileEquals = """[a-zA-Z]{1}:.+""".r
  val httpEquals = """http://.+""".r

  def readFile(uri: String): Option[Iterator[String]] = uri match {
    case fileEquals() =>
      try {
        println("0")
        Some(Source.fromFile(uri, "utf-8").getLines())
      }
      catch {
        case e: Exception => None
      }

    case httpEquals() =>
      try{
        println("1")
        Some(Source.fromURL(uri, "utf-8").getLines())
      } catch {
        case e: Exception => None
      }

    case _ => {
      println("2")
      None
    }
  }

  def split[A](it: Option[Iterator[String]]): List[String] = {
    var list = List[String]()
    for(l <- it.get){
      val temp = l.split(" ").toList
      println(temp)
      for( r <- temp){
        list = list :+ r
      }
    }
    list
  }
}