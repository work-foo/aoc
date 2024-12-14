package day13

import com.google.common.graph.Traverser
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

BigInteger acc = 0

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day13/test')  ;def expected = 480
//def resource = manager.getInputStream('day13/test2') ;def expected = 772
//def resource = manager.getInputStream('day13/test3') ;def expected = 18*4
def resource = manager.getInputStream('day13/input'); def expected = 36838
def lines = resource.getText().eachMatch(~/(?s)(?:\D*)(\d+)(?:\D*)(\d+)(?:\D*)(\d+)(?:\D*)(\d+)(?:\D*)(\d+)(?:\D*)(\d+)/) { whole, String sax, String say, String sbx, String sby, String spx, String spy ->
    def ax = sax.toBigInteger()
    def ay = say.toBigInteger()
    def bx = sbx.toBigInteger()
    def by = sby.toBigInteger()
    def px = spx.toBigInteger()//+ BigInteger.valueOf(10000000000000L)
    def py = spy.toBigInteger()//+ BigInteger.valueOf(10000000000000L)

    def det = (ax * by - ay * bx)
    def a = by*px - bx*py
    def b = -ay*px + ax*py

    println det
    if (det != 0) {
        if (a.abs().mod(det.abs()) == 0 && b.abs().mod(det.abs()) == 0) {
            def cost = a.divide(det)*3 + b.divide(det)
            println cost
            acc += cost
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
