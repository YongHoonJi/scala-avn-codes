import scala.util._
import scala.concurrent._
import ExecutionContext.Implicits.global

val futureFail = Future {
  throw new Exception("Error!")
}

futureFail.onComplete {
  case Success(value) => println(value)
  case Failure(e) => println(e)
}