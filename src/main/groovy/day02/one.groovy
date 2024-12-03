package day02

int acc = 0;

System.in.splitEachLine(~/ /) { ss ->
    def ns = ss.collect {it as int}
    def ds = ns.collate(2,1,false).collect {a, b -> 
        b-a
    }
    if (ds.every {0 < it && it <= 3} || ds.every {-3 <= it && it < 0}) {
        acc++
    }
}

System.out.withPrintWriter { pw -> 
    pw.println(acc)
}
