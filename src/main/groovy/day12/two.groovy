package day12

import com.google.common.graph.Traverser
import org.apache.commons.collections4.multimap.HashSetValuedHashMap
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager
import static java.lang.Math.*
long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day12/test')  ;def expected = 80
//def resource = manager.getInputStream('day12/test2') ;def expected = 436
//def resource = manager.getInputStream('day12/test4') ;def expected = 236
//def resource = manager.getInputStream('day12/test3') ;def expected = 368
def resource = manager.getInputStream('day12/input'); def expected = 193899
def lines = resource.readLines()
char[][] chars = lines*.toCharArray() as char[][]
def depth = lines.size(), width = lines[0].length()
def onboard = { d, r -> d in 0..<depth && r in 0..<width }

def nodes = [] as HashSet
lines.eachWithIndex { String line, int d ->
    line.toCharArray().eachWithIndex { char ch, int r ->
        nodes << new Node(d,r,ch)
    }
}

HashSet<Node> q = new HashSet<Node>(nodes)
def adjacentIndexes = { Node n ->
    [-1, 1]*.plus(n.down()).collect { [it, n.right()] } + [-1, 1]*.plus(n.right()).collect { [n.down(), it] }
}
def validIndexes = { Node n ->
    def adja = adjacentIndexes(n)

    adja.grep(onboard)
}
def around = { Node n ->
    validIndexes(n).collect { d, r ->
        new Node(d, r, chars[d][r])
    }
}
def nextTo = { Node n ->
    around(n).grep { Node it ->
        n.ch() == it.ch()
    }
}
def traverser = Traverser.<Node>forGraph(nextTo)

record Boundary(Node a, Node b) {
    boolean taxicab(Boundary other) {
        int adiff =
        abs(a.down() - other.a.down())+
        abs(a.right() - other.a.right());

        adiff ==
        abs(b.down() - other.b.down())+
        abs(b.right() - other.b.right())
        &&
                adiff == 1

    }
}

def sides = { inside ->
    /* sides is different than perimeter because of adjacency
    * so because of that i want to reuse the traverser
    * so i need a node class for edges so that i can graph it
    * */

    def outside = { Node i ->
        adjacentIndexes(i).findResults { d, r ->
            if (!(onboard d, r)) {
                new Node(d, r, '~' as char)
            } else if (i.ch() != chars[d][r] /*not in region*/) {
                new Node(d, r, chars[d][r])
            }
        }
    }
    def fences = { Node n ->
        outside(n).findResults {new Boundary(n, it) }
    }

    Set<Boundary> boundary = inside.collectMany(new HashSet<Boundary>()) {fences it}

    def edgeNeighbors = new HashSetValuedHashMap<>()

    GQ {
        from b in boundary
        join b2 in boundary on b.taxicab(b2)
        select ([b,b2])
    }.stream().forEach { b, b2  ->

        edgeNeighbors.put(b, b2)
        edgeNeighbors.put(b2, b)
    }

    def sideTraverser = Traverser.forGraph { it ->
        edgeNeighbors.get(it)
    }

    int sides=0
    do {
        def item = boundary.first()
        sides++
        def sideConnected = sideTraverser.depthFirstPreOrder(item).toSet()
        boundary.removeAll(sideConnected)
    } while (boundary)

    sides
}
while (q) {
    def item = q.first()
    def connected = traverser.depthFirstPreOrder(item).toSet()
    q.removeAll(connected)

    def area = connected.size()
    def perimeter = sides(connected)
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
