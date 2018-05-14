val nestedNumbers = List(List(1,2), List(3,4))

nestedNumbers.flatMap( x => x.map(_ * 2))
