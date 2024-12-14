package day14

import com.google.common.graph.Traverser
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

BigInteger acc = 0

def manager = new ClasspathResourceManager()
def resource = manager.getInputStream('day14/test')  ;def expected = 12
//def resource = manager.getInputStream('day14/test2') ;def expected = 772
//def resource = manager.getInputStream('day14/test3') ;def expected = 18*4
def lines = resource.getText()


System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
