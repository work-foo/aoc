package day14

import com.google.common.collect.HashMultimap
import day06.Position
import org.apache.commons.collections4.keyvalue.MultiKey
import org.apache.commons.collections4.multimap.HashSetValuedHashMap
import org.apache.commons.collections4.multiset.HashMultiSet
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

int acc = 0

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day14/test')  ;def expected = 12; int width=11, depth=7
//def resource = manager.getInputStream('day14/test2')  ;def expected = 0; int width=11, depth=7
def resource = manager.getInputStream('day14/input') ;def expected = 225810288; int width=101, depth=103

def collect = resource.getText().replaceAll(~/[^-\d]/, ' ').trim().split()*.toInteger().collate(4).findResults { int px, int py, int vx, int vy ->
    int fx = Math.floorMod(px + 100 * vx, width)
    int fy = Math.floorMod(py + 100 * vy, depth)


    def cmpx = fx <=> width.intdiv(2)
    def cmpy = fy <=> depth.intdiv(2)
    if (cmpx && cmpy) {
        new MultiKey<Integer>(cmpx, cmpy)
    }
}.collect(new HashMultiSet<>(), Closure.IDENTITY)


acc = collect.entrySet().inject(1) {accum, val -> accum * val.getCount()}

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
