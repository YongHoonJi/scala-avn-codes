def findFirst[A](as: Array[A], p: A => Boolean): Int = {
  @annotation.tailrec
  def loop(n: Int): Int = {
    if (n >= as.length) -1
    // If the function `p` matches the current element,
    // we've found a match and we return its index in the array.
    else if (p(as(n))) n
    else loop(n + 1)
  }

  loop(0)
}

val numbers = Array(1,3,4,5,0)

findFirst[Int](numbers, (x) => x == 3)



// First, a binary search implementation, specialized to `Double`,
// another primitive type in Scala, representing 64-bit floating
// point numbers
// Ideally, we could generalize this to work for any `Array` type,
// so long as we have some way of comparing elements of the `Array`
def binarySearch(ds: Array[Double], key: Double): Int = {
  @annotation.tailrec
  def go(low: Int, mid: Int, high: Int): Int = {
    if (low > high) -mid - 1
    else {
      val mid2 = (low + high) / 2
      val d = ds(mid2) // We index into an array using the same
      // syntax as function application
      if (d == key) mid2
      else if (d > key) go(low, mid2, mid2-1)
      else go(mid2 + 1, mid2, high)
    }
  }
  go(0, 0, ds.length - 1)
}


def polyBinarySearch[A](as: Array[A], key: A, gt: (A,A) => Boolean): Int = {
  @annotation.tailrec
  def go(low: Int, mid: Int, high: Int): Int = {
    if (low > high) -mid - 1
    else {
      val mid2 = (low + high) / 2
      val a = as(mid2)
      val greater = gt(a, key)
      if (!greater && !gt(key,a)) mid2
      else if (greater) go(low, mid2, mid2-1)
      else go(mid2 + 1, mid2, high)
    }
  }
  go(0, 0, as.length - 1)
}