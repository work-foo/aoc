package day01

def qs = [new PriorityQueue(), new PriorityQueue() ]
System.in.splitEachLine(~/   /) { ss ->
    ss.eachWithIndex { it, index ->
        qs[index].offer( it as int)
    }
}

int acc;
for (acc = 0; !qs[0].isEmpty(); acc += Math.abs(qs[0].poll() - qs[1].poll() )) ;

System.out.withPrintWriter { pw -> 
    pw.println(acc)
}
