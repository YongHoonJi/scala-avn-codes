val num = List(1,2,3,4,5)
def sum_def = (a:Int, a1:Int) => a+a1
val sum = num.reduce(sum_def)



def time[R](block: => R): R = {
  val t0 = System.nanoTime()
  val result = block    // call-by-name
  val t1 = System.nanoTime()
  println("Elapsed time: " + (t1 - t0) + "ns")
  result
}


val sum_single = time { List.range(0, 1000000).reduce(sum_def) }

val sum_parall = time { List.range(0, 1000000).par.reduce(sum_def) }