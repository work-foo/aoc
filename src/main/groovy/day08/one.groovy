package day08

import day06.Position
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

import java.util.regex.Pattern

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day08/test')
def resource = manager.getInputStream('day08/input')
// right left
def lines = resource.readLines()
def chars = lines*.toCharArray() as char[][]
int width = chars[0].length, depth = chars.length
def onboard = { d, r -> d in 0..<depth && r in 0..<width }
//Pattern pattern = ~/(\d+): (\d+(?: \d+)*)/

record State(Position p, char c) {}
List<State> s = []
chars.eachWithIndex { char[] row, int d ->
    row.eachWithIndex { char item, int r ->
        if (item != '.') s << new State(new Position(d,r), item)
    }
}

Position antinode(State i, State j) {
    def ip = i.p()
    def jp = j.p()

    (jp - ip) + jp
}

acc = GQ {
    from an in (    from i in s
                    join j in s on i.c() == j.c() && i.p() != j.p()
                    select antinode(i,j))
    where onboard(an.down(), an.right())
    select distinct(an)
}.stream().count()

/* how many antinodes onboard
* an antinode is a position*/

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == 14)
}


acc