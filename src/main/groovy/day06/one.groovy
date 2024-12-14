package day06

import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

int acc

def manager = new ClasspathResourceManager()
def resource = manager.getInputStream('day06/input')
//def resource = manager.getInputStream('day06/input')
// right left
def lines = resource.readLines()

/* path count
* find start ^
* until off board =  while on
* move forward unless obstructed else right
* count visited nodes
* the start node is visited
* moving visits
* off board no
*  */
record Position(int down, int right) {
    static Position of(State state) {
        new Position(state.down, state.right)
    }

    Position plus(Position a) {
        new Position(down + a.down(), right + a.right())
    }
    Position minus(Position a) {
        new Position(down - a.down(), right - a.right())
    }
    Position scale(int s) {
        new Position(down * s, right * s)
    }
}

class State {
    int down
    int right
    int pdown = -1
    int pright
}

State state = new State()
state.down = lines.findIndexOf { line ->
    line.findIndexOf { it == '^' }.with { state.right = it } > -1
}
def positions = [] as Set

def depth = lines.size()
def width = lines[0].size()
def onboard = { d, r -> d in 0..<depth && r in 0..<width }
def obstructed = { d, r -> onboard(d,r) &&  lines[d][r] == '#' }
while (onboard(state.down, state.right)) {
    positions << Position.of(state)
    def next_down = state.down + state.pdown
    def next_right = state.right + state.pright

    state.tap (obstructed(next_down, next_right) ? { (pdown, pright) = [pright, -pdown] } : {down += pdown; right += pright })
}

acc = positions.size()

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == 41)
}


acc