package day13

import com.google.common.graph.Traverser
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc

def manager = new ClasspathResourceManager()
def resource = manager.getInputStream('day13/test')  ;def expected = 480
//def resource = manager.getInputStream('day13/test2') ;def expected = 772
//def resource = manager.getInputStream('day13/test3') ;def expected = 18*4
//def resource = manager.getInputStream('day13/input'); def expected = 193899
def lines = resource.readLines()



System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
