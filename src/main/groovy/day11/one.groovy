package day11


import com.google.common.graph.Traverser
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

import static java.lang.Math.*

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day11/test') /* 0099811188827773336446555566 */ ;def expected = 7
//def resource = manager.getInputStream('day11/test2'); /* 022111222 */ def expected = 22
//def resource = manager.getInputStream('day11/test3') /* 0099811188827773336446555566 */ ;def expected = 1
def resource = manager.getInputStream('day11/input'); def expected = 193899
def stones = resource.getText().split()

def evenDigits = { n ->
    n.length() % 2 == 0
}
def splitMiddle = { string ->
    def len = string.length()
    int half = len / 2
    [string.take(half), new BigInteger(string.drop(half)).toString()]
}
def f = { String n ->
    switch (n) {
        case '0' -> ['1']
        case {evenDigits it} -> splitMiddle(n)
        default -> ["${n.toBigInteger().multiply(BigInteger.valueOf(2024)) }"]
    }
}

25.times {
    println it
    stones = stones.collectMany(f)
}

acc = stones.size()

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
