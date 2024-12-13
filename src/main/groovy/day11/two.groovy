package day11

import com.google.common.collect.HashMultiset
import com.google.common.util.concurrent.AtomicLongMap
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day11/test') /* 0099811188827773336446555566 */ ;def expected = 7
//def resource = manager.getInputStream('day11/test2'); /* 022111222 */ def expected = 22
//def resource = manager.getInputStream('day11/test3') /* 0099811188827773336446555566 */ ;def expected = 1
def resource = manager.getInputStream('day11/input'); def expected = 193899
def stones = AtomicLongMap.<String>create(resource.getText().split().collectEntries{ ch -> [ch, 1L]})

def evenDigits = { n ->
    n.length() % 2 == 0
}
final def big2024 = BigInteger.valueOf(2024)

75.times {
    println it
    def next = AtomicLongMap.<String>create()
    stones.asMap().each {string, count ->
        switch (string) {
            case '0':
                next.addAndGet('1', count); break
            case {evenDigits it}:
                def len = string.length()
                int half = len / 2
                next.addAndGet(string.take(half), count)
                next.addAndGet(new BigInteger(string.drop(half)).toString(), count)
                break;
            default:
                next.addAndGet("${string.toBigInteger().multiply(big2024) }", count); break
        }
    }
    stones=next
}

acc = stones.sum()

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
