

val wc = new PageReader()
val it = wc.readFile("https://en.wikipedia.org/wiki/Scala_(programming_language)")

val list = wc.split(it)
list.map( r => "word : " + r).foreach( s => println(s))

