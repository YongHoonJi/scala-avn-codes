
import scala.collection.mutable
import scala.collection.mutable.HashMap

def getFrequency(words:List[String]): mutable.Map[String, Int] = {
  val counts = HashMap[String, Int]().withDefaultValue(0)
  for(w <- words){
    counts(w) += 1
  }
  counts
}


// count word in a page
val page = "The present invention relates to a composition for manufacturing a base layer.\nThe composition for manufacturing a base layer according to the present invention includes a thermally expandable microsphere, thereby improving physical properties such as cold resistance, heat resistance, durability, shock absorption, and anti-resistance, and maintaining excellent gap adhesion with an adhesive."

val wordInPage = page.split("\\W").toList

getFrequency(wordInPage)


// lazy stream in infinite stream
val largeList = (1 to 1000000000).toStream.take(3)
largeList.foreach( println(_))


