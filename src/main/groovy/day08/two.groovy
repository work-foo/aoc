package day08

import day06.Position
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

import java.util.regex.Pattern
import java.util.stream.Stream

long acc

def manager = new ClasspathResourceManager()
def resource = manager.getInputStream('day08/test') //34
//def resource = manager.getInputStream('day08/test2') //9
//def resource = manager.getInputStream('day08/input')
// right left
def lines = resource.readLines()
def chars = lines*.toCharArray() as char[][]
int width = chars[0].length, depth = chars.length
def onboard = { d, r -> d in 0..<depth && r in 0..<width }
//Pattern pattern = ~/(\d+): (\d+(?: \d+)*)/

List<State> s = []
chars.eachWithIndex { char[] row, int d ->
    row.eachWithIndex { char item, int r ->
        if (item != '.') s << new State(new Position(d,r), item)
    }
}

def antinode = { State i, State j ->
    def ip = i.p()
    def jp = j.p()

    def vector = jp - ip
    Stream.iterate(ip, { onboard(it.down(), it.right()) }, {vector + it}).toList()
}

def nodes = GQ {
    from i in s
    join j in s on i.c() == j.c() && i.p() != j.p()
    select antinode(i,j)
}.stream().reduce([] as Set, {accset, l -> accset.addAll(l); accset}, {sa, sb -> sa + sb} )

acc = nodes.size()

/* how many antinodes onboard
* an antinode is a position*/

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == 34)
}


acc