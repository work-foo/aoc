package day16


import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day16/test')  ;def expected = 7036
//def resource = manager.getInputStream('day16/test2') ;def expected = 11048
//def resource = manager.getInputStream('day16/test3') ;def expected = 14+1000+4
def resource = manager.getInputStream('day16/input'); def expected = 74392
def lines = resource.readLines()
char[][] chars = lines*.toCharArray() as char[][]
def depth = chars.length, width = chars[0].length
def onboard = { d, r -> d in 0..<depth && r in 0..<width }
int sd=depth-2, sr=1, ed=1, er=width-2

record Node(int down, int right, int orientation /* 0 1 2 3 = right up left down */) {}
def nodes = [] as Set
chars.eachWithIndex { char[] row, int d ->
    row.eachWithIndex { char ch, int r ->
        if (ch != '#') 0.upto(3) {nodes << new Node(d,r,it)}
    }
}

def start = new Node(sd, sr, 0)

class MinInteger {
    int value = Integer.MAX_VALUE
    boolean min(int other) {
        boolean ret
        if (ret = other < value) value = other
        ret
    }

}

Map<Node, MinInteger> dist = nodes.collectEntries { [it, new MinInteger()] }
Map<Node, Node> prev = nodes.collectEntries { [it, null] }

dist[start].min(0)
Comparator<Node> comparator = { Node a, Node b ->
    dist[a].value <=> dist[b].value
}
Queue<Node> q = nodes.collect(new PriorityQueue<>(comparator), Closure.IDENTITY)

while (q) {
    def min = q.remove()
    if (min.down() == ed && min.right() == er) {
        acc = dist[min].value
        break
    }

    def (vd, vr) = [[0, 1], [-1, 0], [0, -1], [1, 0]][min.orientation()]
    def straight = new Node(min.down() + vd, min.right() + vr, min.orientation())
    def ccw = new Node(min.down(), min.right(), (min.orientation() + 1) % 4)
    def reverse = new Node(min.down(), min.right(), (min.orientation() + 2) % 4)
    def cw = new Node(min.down(), min.right(), (min.orientation() + 3) % 4)

    for (u in [straight, ccw, reverse, cw]) {
        if (u in nodes) {

            def cost = switch (Math.abs(Math.floorMod(u.orientation() - min.orientation(), 4))) {
                case 0 -> 1
                case [1, 3] -> 1000
                case 2 -> 2000
                default -> assert false
            }
            if (dist[u].min(dist[min].value + cost)) {
                q.remove(u)
                q.add(u)
                prev[u] = min
            }
        }
    }
}


System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
