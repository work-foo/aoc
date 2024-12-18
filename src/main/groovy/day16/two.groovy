package day16

import com.google.common.graph.Traverser
import day06.Position
import org.apache.commons.collections4.IterableUtils
import org.apache.commons.collections4.multimap.HashSetValuedHashMap
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day16/test')  ;def expected = 45
//def resource = manager.getInputStream('day16/test2') ;def expected = 64
//def resource = manager.getInputStream('day16/test3'); def expected = 10
def resource = manager.getInputStream('day16/input'); def expected = 426
def lines = resource.readLines()
char[][] chars = lines*.toCharArray() as char[][]
def depth = chars.length, width = chars[0].length
def onboard = { d, r -> d in 0..<depth && r in 0..<width }
int sd = depth - 2, sr = 1, ed = 1, er = width - 2

def nodes = [] as Set
chars.eachWithIndex { char[] row, int d ->
    row.eachWithIndex { char ch, int r ->
        if (ch != '#') 0.upto(3) { nodes << new Node(d, r, it) }
    }
}

def start = new Node(sd, sr, 0)


Map<Node, MinInteger> dist = nodes.collectEntries { [it, new MinInteger()] }
def prev = new HashSetValuedHashMap<>()

dist[start].strictMin(0)
Comparator<Node> comparator = { Node a, Node b ->
    dist[a].value <=> dist[b].value
}
Queue<Node> q = nodes.collect(new PriorityQueue<>(comparator), Closure.IDENTITY)

int maxCost = Integer.MAX_VALUE
while (q) {
    def min = q.remove()
    if (min.down() == ed && min.right() == er) {
        maxCost = dist[min].value
        println maxCost
    }
    if (dist[min].value > maxCost) break;

    def (vd, vr) = [[0, 1], [-1, 0], [0, -1], [1, 0]][min.orientation()]
    def straight = new Node(min.down() + vd, min.right() + vr, min.orientation())
    def ccw = new Node(min.down(), min.right(), (min.orientation() + 1) % 4)
    def reverse = new Node(min.down(), min.right(), (min.orientation() + 2) % 4)
    def cw = new Node(min.down(), min.right(), (min.orientation() + 3) % 4)

    for (u in [straight, ccw, reverse, cw]) {
        if (u in nodes) { // valid node

            def cost = switch (Math.floorMod(u.orientation() - min.orientation(), 4)) {
                case 0 -> 1
                case [1, 3] -> 1000
                case 2 -> 2000
                default -> assert false
            }


            def ucost = dist[min].value + cost
            if (dist[u].value == ucost) {
                // if cost to the neighbor node matches (because it just changed or matches) then store it in the multimap
                prev.put(u, min)
            }
            if (dist[u].strictMin(ucost)) { // reindex those that changed
                q.remove(u)
                q.add(u)
                prev.remove(u)
                prev.put(u, min)
            }
        }
    }
}

def ends = (0..3).findResults { new Node(ed, er, it) }.grep { dist[it].value == maxCost }
def reachesEnd = Traverser.forGraph { Node p -> prev.get(p) }.depthFirstPreOrder(ends).toList()
def positions = reachesEnd.findResults { Node it -> new Position(it.down(), it.right()) }.toSet()
acc = positions.size()


System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
