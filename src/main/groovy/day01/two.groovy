package day01

def ls = [[], []]
System.in.splitEachLine(~/   /) { ss ->
    ss.eachWithIndex { it, index ->
        ls[index].add(it as int)
    }
}

def cs = ls.collect {list -> list.countBy{it}}

int acc = cs[0].inject(0) { acc, value, count ->
    acc + value * count * cs[1].get(value, 0)
}

System.out.withPrintWriter { pw -> 
    pw.println(acc)
}

