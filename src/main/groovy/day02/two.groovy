package day02

import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

int acc = 0;

new ClasspathResourceManager().getInputStream('day02/input').splitEachLine(~/ /) { ss ->
    def ns = ss.collect {it as int}

    if (gradual(ns) ||  deletions(ns).any(this::gradual)) {
        acc++
    }
}

System.out.withPrintWriter { pw -> 
    pw.println(acc)
}

def gradual(def list) {
    def ds = list.collate(2,1,false).collect {a, b -> 
        b-a
    }
    ds.every {0 < it && it <= 3} || ds.every {-3 <= it && it < 0}
}

List<List<Integer>> deletions(List list) {
    def copy = 
    list.collect {
        def l = []
        l.addAll(list)
        l
    }

    copy.eachWithIndex { it, index ->
        it.remove(index)
    }
}