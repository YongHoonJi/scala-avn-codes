

  def func[T](xs:T*) = xs.foreach(x => println(x))

  def main(arg : Array[String]): Unit ={
    func(0,1,2)
  }

  def sum(xs:Int*):Int = if (xs.isEmpty) 0 else xs.head + sum(xs.tail:_*)
  println(sum(0,1,2,3)) // OK !

  val ns = List(1, 3, 5)
  println(sum(ns:_*)) // OK!

  //println(sum(List(1, 3, 5))) // Not Good
