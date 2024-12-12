package day07

import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

import java.util.regex.Matcher
import java.util.regex.Pattern

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day07/test')
def resource = manager.getInputStream('day07/input')
// right left
def lines = resource.readLines()
Pattern pattern = ~/(\d+): (\d+(?: \d+)*)/
acc =
lines.findResults {
    def matcher = it =~ pattern
    long testValue = matcher[0][1].toLong()
    List<Long> numbers = matcher[0][2].split()*.toLong()

    def canSum = numbers.tail().inject([numbers[0]]) { list, val ->

        def added = list*.plus(val)
        added.addAll(list*.multiply(val))

        added
    }.any { it == testValue }
    canSum ? testValue : 0L
}.sum()

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == 3749)
}


acc