
/*
모나드는 3개의 법칙을 따라야 한다.
Left identity: (pure(a) flatMap f) == f(a)
Right identity: (m flatMap pure) == m
Associativity: (m flatMap f flatMap g) == (m flatMap (x => f(x) flatMap g))
 */

/*
아래 future는 모나드의 한 형
unit - 모나드는 특정 값에 대한 감싸기 규칙을 만든다.
bind and flatMap - 감싼 값에 대해 꺼낼 수 있는 방법을 제공.
꺼낸 값을 가지고 원하는 형식으로 변형 후 그 값을 감싸서 반환
 */

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