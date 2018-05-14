import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration


val s = "Hello"
val f: Future[String] = Future {
  s + " future!"
}

// 동기적 사용
Await.result(f, Duration.Inf)

// 비동기적 사용 (flatmap)
case class User(session: String)
case class Order(userid: String)
case class Item(orderid: String)

def getUser(session: String): Future[User] = Future{
  println("getUser")
  User(session)
}


def getOrder(userid: String): Future[Order] = Future{
  println("get Order")
  Order("userid")
}

def getItems(userid: String): Future[List[Item]] = Future{
  println("get Items")
  List(Item("1"), Item("2"), Item("3"))
}

// use for comprehesion
val itemsFuture: Future[List[Item]] = for {
  user <- getUser("session")
  order <- getOrder(user.session)
  items <- getItems(order.userid)
} yield items

itemsFuture.map{
  items => println(s"item - $items")
}