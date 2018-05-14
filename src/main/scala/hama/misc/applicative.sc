class Applicative[T](val value: T) {

  def apply[A, B](b: Applicative[A => B]): Applicative[A] => Applicative[B] = {
    (a: Applicative[A]) => new Applicative[B](b.value(a.value))
  }
}

val stringContainer: Applicative[String] = new Applicative("Knoldus Software LLP")

def rawLengthOfString(str: String): Int = str.length

val lengthOf: Applicative[String => Int] = new Applicative(rawLengthOfString _)

def transformedLength = stringContainer.apply(lengthOf)

transformedLength

val result: Applicative[Int] = transformedLength(stringContainer)

result.value


// example 2
/*
List(1, 2, 3, 4) map {(_: Int) * (_:Int)}
<console>:14: error: type mismatch;
  found   : (Int, Int) => Int
  required: Int => ?
  List(1, 2, 3, 4) map {(_: Int) * (_:Int)}
*/

// curry this:
val res11 = List(1, 2, 3, 4) map {(_: Int) * (_:Int)}.curried
res11 map {_(9)}
