package day01

import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

def qs = [new PriorityQueue(), new PriorityQueue() ]
new ClasspathResourceManager().getInputStream('day01/input').splitEachLine(~/   /) { ss ->
    ss.eachWithIndex { it, index ->
        qs[index].offer( it as int)
    }
}

int acc;
for (acc = 0; !qs[0].isEmpty(); acc += Math.abs(qs[0].poll() - qs[1].poll() )) ;

System.out.withPrintWriter { pw -> 
    pw.println(acc)
}
