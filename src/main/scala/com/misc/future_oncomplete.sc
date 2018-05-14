import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.util._

val futureFail = Future {
  throw new Exception("Error!")
}

futureFail.onComplete {
  case Success(value) => println(value)
  case Failure(e) => println(e)
}