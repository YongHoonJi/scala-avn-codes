import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.io.Source

val f: Future[String] = Future {
  val f = Source.fromFile("build.sbt")
  try f.getLines().mkString("\n") finally f.close()
}

println(f.isCompleted)
Thread.sleep(2000)
println(f.isCompleted)
println(f.value)

val seqFromUrl = Seq("telnet 1", "password is required.")
// future and callback
def getUrlSpec(): Future[Seq[String]] = Future {
  //val f = Source.fromURL("http://www.w3.org/Addressing/URL/url-spec.txt")
  //try f.getLines().toList finally f.close()
  seqFromUrl
}

val u: Future[Seq[String]] = getUrlSpec()

def find(lines: Seq[String], word: String) = lines.zipWithIndex.collect {
  case (line, n) if line.contains(word) => (n, line)
} mkString("\n")

val suc = u.value

suc foreach {
  l => println(find(l, "password"))
}

