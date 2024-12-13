package day10


import com.google.common.graph.Traverser
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day10/test') /* 0099811188827773336446555566 */ ;def expected = 1
//def resource = manager.getInputStream('day10/test3') /* 0099811188827773336446555566 */ ;def expected = 9
//def resource = manager.getInputStream('day10/test2'); /* 022111222 */ def expected = 81
def resource = manager.getInputStream('day10/input'); def expected = 786
def text = resource.withCloseable {it.readLines()}
def depth = text.size(), width = text[0].length()
def onboard = { d, r -> d in 0..<depth && r in 0..<width }

List<Node> zeroes = []
text.eachWithIndex { String row, int d ->
    row.toCharArray().eachWithIndex { char height, int r ->
        def node = new Node(d, r, Character.getNumericValue(height))
        if (node.value()==0) zeroes << node
    }
}


def traverser = Traverser.forTree { Node n ->
    def adja = [-1, 1]*.plus(n.down()).collect { [it, n.right()] } + [-1, 1]*.plus(n.right()).collect { [n.down(), it] }

    adja.grep(onboard).collect { d, r ->
        new Node(d, r, Character.getNumericValue(text[d].charAt(r)))
    }.grep {
        n.value()+1 == it.value()
    }
}

acc=
zeroes.sum {traverser.depthFirstPreOrder(it).count { it.value() == 9 } }




/* sum of score of all trailheads
* sum by score
*   score is number of distinct reachable 9s
*   reachable if path diff const 1
* a trailhead is a 0 */

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc