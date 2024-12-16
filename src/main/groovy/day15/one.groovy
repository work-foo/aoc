package day15

import com.google.common.graph.Traverser
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day15/test')  ;def expected = 2028
//def resource = manager.getInputStream('day15/test2') ;def expected = 10092
def resource = manager.getInputStream('day15/input'); def expected = 193899
def lines = resource.readLines()
char[][] chars = lines*.toCharArray() as char[][]
def depth = lines.size(), width = lines[0].length()
def onboard = { d, r -> d in 0..<depth && r in 0..<width }

record Node(int down, int right, char ch) {}

def nodes = [] as HashSet
lines.eachWithIndex { String line, int d ->
    line.toCharArray().eachWithIndex { char ch, int r ->
        nodes << new Node(d,r,ch)
    }
}

HashSet<Node> q = new HashSet<Node>(nodes)
def successor = { Node n ->
    def adja = [-1, 1]*.plus(n.down()).collect { [it, n.right()] } + [-1, 1]*.plus(n.right()).collect { [n.down(), it] }

    adja.grep(onboard).collect { d, r ->
        new Node(d, r, chars[d][r])
    }.grep { Node it ->
        n.ch() == it.ch()
    }
}
def traverser = Traverser.<Node>forGraph(successor)
while (q) {
    def item = q.first()
    def connected = traverser.depthFirstPreOrder(item).toSet()
    q.removeAll(connected)

    def area = connected.size()
    def perimeter = connected.sum { Node outer ->
        4 - successor(outer).size()
    }
    acc += area*perimeter
}

/* compute cost of fencing around regions
* same character and neighbor then same region which determines area
* cost for puzzle is
* sum of cost for regions
* cost region is area*perimeter
* perimeter is sum over block neighbor different its a local property which sums over the group
*
* so all nodes to set
* pull one, get reachable, subtract
* loop
*
* have area, compute perimeter  */



System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
