package day03

import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc = 0;

new ClasspathResourceManager().getInputStream('day03/input')
        .getText()
        .eachMatch(~/mul\((\d+),(\d+)\)/) { whole, a, b ->
            acc += (a as int) * (b as int)
        }


System.out.withPrintWriter { pw -> 
    pw.println(acc)
}
