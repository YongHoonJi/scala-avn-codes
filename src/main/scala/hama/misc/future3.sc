
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.control.NonFatal

case class PatentDocument(doc: String)
case class UnknowNoun(ukn: String)

type Recovery[T] = PartialFunction[Throwable,T]
// None으로 복구하기
def withNone[T]: Recovery[Option[T]] = { case NonFatal(e) => None }

// 빈 시퀀스로 복구하기
def withEmptySeq[T]: Recovery[Seq[T]] = { case NonFatal(e) => Seq() }

def getDocumentFromFileSystem(): Future[PatentDocument] = Future {
  println("get a document from file system.")
  Thread.sleep(1000)
  PatentDocument("kipo_102017120111")
}


val future1 = getDocumentFromFileSystem

// do other things...
Thread.sleep(2000)
println("complete")
future1.isCompleted
future1.value

