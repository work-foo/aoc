package day12


import com.google.common.graph.Traverser
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

import static java.lang.Math.*

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day12/test')  ;def expected = 140
//def resource = manager.getInputStream('day12/test2') ;def expected = 772
//def resource = manager.getInputStream('day12/test3') ;def expected = 1
def resource = manager.getInputStream('day12/input'); def expected = 193899
def stones = resource.getText().split()


System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
