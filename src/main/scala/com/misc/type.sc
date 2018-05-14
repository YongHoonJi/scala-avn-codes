//def foo[A](f: A => List[A], i: Int) = f(i)



// type inference
// 타입 유추를 함. 반환타입도 유추
def func1[T](x: T) = x
val x = func1(322)
val y = func1("hey")
val z = func1(Array(1,2,3,4))

// covariant (? extends T)
class Covariant[+A]
val cv: Covariant[AnyRef] = new Covariant[String]
//val cv1: Covariant[String] = new Covariant[AnyRef]

// contravariant (? super T)
class Contravariant[-A]
val ctv: Contravariant[String] = new Contravariant[AnyRef]
//val ctv2: Contravariant[AnyRef] = new Contravariant[String]


// trait Function1 [-T1, +R] extends AnyRef
class Animal { val sound = "rustle" }

class Bird extends Animal { override val sound = "call" }

class Chicken extends Bird { override val sound = "cluck" }

val getTweet: (Bird => String) = ((a: Animal) => a.sound )
/* ok
val getTweet: (Bird => String) = ((a: Bird) => a.sound )
*/
/* not ok :should be ? super Bird
val getTweet: (Bird => String) = ((a: Chicken) => a.sound )
*/

/*
bird를 반환하는데 chicken도 bird의 확장형이므로 문제없음.
 */

val hatch: (() => Bird) = (() => new Chicken )


//def cacophony[T](things: Seq[T]) = things map (_.sound)
def biophony[T <: Animal](things: Seq[T]) = things map (_.sound)
val bios = biophony(Seq(new Chicken, new Bird))



