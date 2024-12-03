package day02

import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

int acc = 0;

new ClasspathResourceManager().getInputStream('day02/input').splitEachLine(~/ /) { ss ->
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
