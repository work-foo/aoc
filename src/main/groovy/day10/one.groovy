package day10

import day06.Position
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

import java.util.function.Supplier
import java.util.regex.Pattern

long acc

def manager = new ClasspathResourceManager()
def resource = manager.getInputStream('day10/test') /* 0099811188827773336446555566 */ ;def expected = 1928
//def resource = manager.getInputStream('day10/test2'); /* 022111222 */ def expected = 60
//def resource = manager.getInputStream('day10/input'); def expected = 6395800119709
def text = resource.getText()



System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc