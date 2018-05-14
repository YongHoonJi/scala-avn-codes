
case class Boxed[T](value:T)
case class Logged[T](value:T, log:List[String])

abstract class MyOption[+A]
case class MySome[+A](x: A) extends MyOption[A]
case object MyNone extends MyOption[Nothing]

abstract class MyList[+A]
case class Cons[B](var hd: B, var tl: MyList[B]) extends MyList[B]
case object MyNil extends MyList[Nothing]

def initBoxed(x:Int):Boxed[Int] = Boxed(x)
def initLogged(x:Int):Logged[Int] = Logged(x, List())
def initMyOption(x:Int):MyOption[Int] = MySome(x)
def initMyList(x:Int):MyList[Int] = Cons(x,MyNil)

// 아래 double, sqrt 결과를 C[T]하고 싶다. T는 기저타입
def double(x:Int):Int = x + x
def sqrt(x:Int):Int = Math.sqrt(x).toInt

def doubleBoxed(x:Int):Boxed[Int] = Boxed(x+x)
def sqrtBoxed(x:Int):Boxed[Int] = Boxed(Math.sqrt(x).toInt)

def doubleLogged(x:Int):Logged[Int] = Logged(x+x, List("double("+x+") = " + (x+x)))
def sqrtLogged(x:Int):Logged[Int] = Logged(Math.sqrt(x).toInt, List("sqrt("+x+").toInt = " + Math.sqrt(x).toInt))

def doubleMyOption(x:Int):MyOption[Int] = MySome(x+x)
def sqrtMyOption(x:Int):MyOption[Int] = if(x>=0) MySome(Math.sqrt(x).toInt) else MyNone


def doubleMyList(x:Int):MyList[Int] = Cons(x+x,MyNil)
def sqrtMyList(x:Int):MyList[Int] = Cons(Math.sqrt(x).toInt,MyNil)

/* 함수 조합 */
// 프로그램을 짠다는 것은 이런식의 함수 합성을 계속 수행해 데이터를 변환하는 것
def o[T,V,U](f:T=>V, g:V=>U) = (x:T) => g(f(x))

val doubleThenSqrt = o(double, sqrt)
doubleThenSqrt(8)

// 커리된 조합
def o2[T,V,U](f:T=>V) = (g:V=>U) => (x:T) => g(f(x))
val doubleThenSqrt2Result = o2(double)(sqrt)(8)

// 박스를 벗기고 적용 후 다시 입힘
def mkBoxedFun(f:Int=>Boxed[Int]) = (x:Boxed[Int]) => {
  val value = x.value    // x(박싱된 값)에서 내부의 값 노출시키기
  val value2 = f(value)  // 함수 적용
  value2				   // 값 반환
}
// 부가정보가 있는 Boxed를 처리하도록 하는 예
val x2 = o(mkBoxedFun(doubleBoxed), mkBoxedFun(sqrtBoxed)) (initBoxed(8))


def mkLoggedFun(f:Int=>Logged[Int]) = (x:Logged[Int]) => {
  val value = x.value    // x(로그 포함된 값)에서 내부의 값 노출시키기
  val value2 = f(value)  // 함수 적용
  value2				   // 값 반환
}
// double 로그가 사라짐
val x3 = o(mkLoggedFun(doubleLogged), mkLoggedFun(sqrtLogged))(initLogged(8))

def mkLoggedFunRevised(f:Int=>Logged[Int]) = (x:Logged[Int]) => {
  val value = x.value    // x(로그 포함된 값)에서 내부의 값 노출시키기
  val log = x.log        // x에서 로그 가져오기
  val value2 = f(value)  // 함수 적용
  Logged(value2.value, log:::value2.log)
}

val x4 = o(mkLoggedFunRevised(doubleLogged), mkLoggedFunRevised(sqrtLogged))(initLogged(8))

// MyOption
def mkMyOptionFun(f: (Int=>MyOption[Int])) = (x: MyOption[Int]) => x match {
  case MySome(x) => { // 값을 노출시키는 작업은 패턴매칭으로 됨
    val value2 = f(x) // f 적용하기
    value2 // 값 반납하기
  }
  case MyNone => MyNone
}

val x5 = o(mkMyOptionFun(doubleMyOption), mkMyOptionFun(sqrtMyOption))(MySome(8))
val errval = o(mkMyOptionFun(doubleMyOption), mkMyOptionFun(sqrtMyOption))(MySome(-8))

// lazy
class Lazy[T](value: ()=>T) {
  def getValue():T = value()
}

def initLazy(x: =>Int):Lazy[Int] = new Lazy(()=>x)
def doubleLazy(x: =>Int):Lazy[Int] = new Lazy(()=>{print("lazy double(" + x + ") run\n");x+x})
def sqrtLazy(x: =>Int):Lazy[Int] = new Lazy(()=>{print("lazy sqrt(" + x + ") run\n");Math.sqrt(x).toInt})

def mkLazyFun(f: (Int=>Lazy[Int])) = (x: Lazy[Int]) => {
  def value = x.getValue // x서 내부의 값 노출시키기(def로 했으므로 계산하지 않음)
  def tmpFun() = {	     // x 내부 값을 계산하지 않게 함수를 하나 정의
    val y = f(value)
    y.getValue()
  }
  new Lazy(tmpFun)
}

val x6 = o(mkLazyFun((x)=>doubleLazy(x)), mkLazyFun((y)=>sqrtLazy(y)))(initLazy(8))
//getValue()를 하기 전까지 아무것도 실행되지 않았다.
x6.getValue
// 리스트
def mkMyListFun(f:Int=>MyList[Int]) = (x:MyList[Int]) => {
  // f가 만드는 리스트를 모두 합쳐야 하기 때문에 결과적으로는
  // flatMap과 비슷한 일을 해야 한다.
  def append(l1:MyList[Int], l2:MyList[Int]):MyList[Int] = l1 match {
    case Cons(h,t) => {
      Cons(h,append(t,l2))
    }
    case MyNil => l2
  }
  def mapAll(l:MyList[Int]):MyList[Int] = l match {
    case Cons(h,t) => {
      val value2 = f(h)
      val remain = mapAll(t)
      append(value2,remain)
    }
    case MyNil => MyNil
  }
  mapAll(x)
}

// x1은 앞에서 만들었던 리스트이다.
// x1 = Cons(1,Cons(2,Cons(8,MyNil)))
val x1:MyList[Int] = Cons(1,Cons(2,Cons(8,MyNil)))
val x7 = o(mkMyListFun(doubleMyList), mkMyListFun(sqrtMyList))(x1)
