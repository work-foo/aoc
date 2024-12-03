package day03

import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc = 0;

String _do = /do\(\)/, dont = /don't\(\)/

def text = 'do()' + new ClasspathResourceManager().getInputStream('day03/input').getText() + 'don\'t()'
text.eachMatch(~/(?s)${_do}(.*?)${dont}/) { dodont, inside ->
    inside.eachMatch(~/mul\((\d+),(\d+)\)/) { mul, a, b ->
        acc += (a as int) * (b as int)
    }
}

System.out.withPrintWriter { pw -> 
    pw.println(acc)
}
