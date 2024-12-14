package day13

import com.google.common.graph.Traverser
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day13/test')  ;def expected = 480
//def resource = manager.getInputStream('day13/test2') ;def expected = 772
//def resource = manager.getInputStream('day13/test3') ;def expected = 18*4
def resource = manager.getInputStream('day13/input'); def expected = 193899
def lines = resource.getText().eachMatch(~/(?s)(?:\D*)(\d+)(?:\D*)(\d+)(?:\D*)(\d+)(?:\D*)(\d+)(?:\D*)(\d+)(?:\D*)(\d+)/) { whole, sax, say, sbx, sby, spx, spy ->
    def ax = sax as int
    def ay = say as int
    def bx = sbx as int
    def by = sby as int
    def px = spx as int
    def py = spy as int

    def det = ax * by - ay * bx
    def a = by*px - bx*py
    def b = -ay*px + ax*py

    if (det != 0) {
        if (a % det == 0 && b % det == 0) {
            acc += a / det * 3 + b / det
        }
    } else { //if (px % ax == 0 && py % ay == 0 && px*ay == ax*py) {
        // singular
        assert det != 0
//        def l1 = px/ax, l2 = py/ay
//        def p1 = px/bx, p2 = py/by
//        acc += Math.min(3*l1, p1)
    }
}

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
