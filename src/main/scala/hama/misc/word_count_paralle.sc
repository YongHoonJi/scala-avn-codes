import scala.collection.mutable
import scala.collection.mutable.HashMap

def getFrequency(page: String): mutable.Map[String, Int] = {
  val wordsInPage = page.split("\\W").toList
  val counts = HashMap[String, Int]().withDefaultValue(0)
  for(w <- wordsInPage){
    counts(w) += 1
  }
  counts
}


// count word in a page
val pages = List("The present invention relates to a composition for manufacturing a base layer.\\nThe composition for manufacturing a base layer according to the present invention includes a thermally expandable microsphere, thereby improving physical properties such as cold resistance, heat resistance, durability, shock absorption, and anti-resistance, and maintaining excellent gap adhesion with an adhesive.",
"The present invention relates to a composition")

val freq = pages.map( p => getFrequency(p)).flatten


freq

