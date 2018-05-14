
import spray.json._
import spray.json.DefaultJsonProtocol._

case class Vegetable(name: String, color: String)
implicit val VegetableFormat = jsonFormat2(Vegetable)

val spinachString = """{"name": "spinach", "color": "green"}"""
val spinach: Vegetable = spinachString.parseJson.convertTo[Vegetable]

val broccoli = Vegetable("broccoli", "green")
val beet = Vegetable("beet", "red")
println(broccoli.toJson.compactPrint)
println(beet.toJson.prettyPrint)