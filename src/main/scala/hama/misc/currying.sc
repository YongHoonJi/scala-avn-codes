def filter(xs: List[Int], p: Int => Boolean): List[Int] =
  if (xs.isEmpty) xs
  else if (p(xs.head)) xs.head :: filter(xs.tail, p)
  else filter(xs.tail, p)
def modN(n: Int) = (x: Int) => ((x % n) == 0)
val nums = List(1, 2, 3, 4, 5, 6, 7, 8)

println(filter(nums, modN(2)))
println(filter(nums, modN(3)))

// modeN1, modeN2는 다른 표현식의 같은 함수이다.
def modN1(n: Int) = (x: Int) => ((x % n) == 0)
def modN2(n: Int)(x: Int) = ((x % n) == 0)