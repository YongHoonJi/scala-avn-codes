// functor는 Option, Seq, Future, Either 에서 사용하는 map을 추상화 한것이라고 보면된다.

// Sequence : Seq[A] => Seq[B]
Seq(1,2,3) map(_ * 2)

// Options
Some(1) map (_.toString)


// sequences, option은 공통된 틀이 있음
// F[A]를 받아서 A => B를 적용 후 F[B]를 리턴받음
// 함수 합성으로 본다면 R => A함수를 받아서 A => B를 적용 후 R => B 함수를 리턴 받음.


/*
Functor의 정의
F[A] 에 대해 (A => B) => F[B] 타입의 map 함수

아래의 법칙을 따름
fa map (A => B) == fa
fa map(g(f(_)) == (fa map f) map g


import scala.language.higherKinds

trait Functor[F_] {
  def map[A,B](fa: F[A])(f: A => B): F[B]
}
*/

/*
functor 예제
 */

import cats.Functor

val list1 = List(1,2,3)

val list2 = Functor[List].map(list1)(_ * 2)

val func = (x: Int) => x+1

val lifted = Functor[Option].lift(func)
lifted(Some(1))
//res1: Option[Int] = Some(2)

sealed trait Result[+A]
final case class Success[A](value: A) extends Result[A]
final case class Warning[A](value: A, message: String) extends Result[A]
final case class Failure(message: String) extends Result[Nothing]

import cats.Functor

implicit val resultFunctor = new Functor[Result] {
  def map[A, B](result: Result[A])(func: A => B): Result[B] =
    result match {
      case Success(value) => Success(func(value))
      case Warning(value, message) => Warning(func(value), message)
      case Failure(message) => Failure(message)
    }
}

def success[A](value: A): Result[A] =
  Success(value)

def warning[A](value: A, message: String): Result[A] =
  Warning(value, message)

def failure(message: String): Result[A] =
  Failure(message)

success(100) map (_ * 2)


// functor는 Option, Seq, Future, Either 에서 사용하는 map을 추상화
// functor는 F[A]에 대해 (A => B) => F[B] 타입의 map 함수
Seq(1,2,3,4) map (_ * 2)

Some(1) map (_.toString)
