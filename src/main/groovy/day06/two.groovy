package day06

import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

int acc

def manager = new ClasspathResourceManager()
def resource = manager.getInputStream('day06/input')
//def resource = manager.getInputStream('day06/input')
// right left
def lines = resource.readLines()
def depth = lines.size()
def width = lines[0].size()
def onboard = { d, r -> d in 0..<depth && r in 0..<width }

record FullState(int down, int right, int pdown, int pright) {
    static FullState of(State s) {
        s.with {new FullState(down, right, pdown, pright)}
    }
}
State state = new State()
state.down = lines.findIndexOf { line ->
    line.findIndexOf { it == '^' }.with { state.right = it } > -1
}

def notAllowed = Position.of(state)
//def notAllowed = new Position(state.down + state.pdown, state.right + state.pright)
def initial = FullState.of(state)

def trajectory = []
def obstructed = { d, r -> onboard(d,r) && lines[d][r] == '#' }
while (onboard(state.down, state.right)) {
    trajectory << Position.of(state)
    def next_down = state.down + state.pdown
    def next_right = state.right + state.pright

    state.tap (obstructed(next_down, next_right) ? { (pdown, pright) = [pright, -pdown] } : {down += pdown; right += pright })
}

def obs = [] as Set
def loops = { Position newob ->
    def mut = initial.toMap() as State // always start at the start

    if (newob == notAllowed) return false // cant block first spot
    if (newob in obs) return false // dont double count

    def visited = [] as Set // loop state
    def newObstructed = { d, r -> onboard(d,r) && (newob == new Position(d,r) ||lines[d][r] == '#') } // when onboard and new or old obstruction
    boolean result // did i finish on the board (which means i looped)
    while (result = onboard(mut.down, mut.right)) {
        if (!visited.add(FullState.of(mut))) break // when ive been here it will loop

        def next_down = mut.down + mut.pdown
        def next_right = mut.right + mut.pright

        mut.tap (newObstructed(next_down, next_right) ? { (pdown, pright) = [pright, -pdown] } : {down += pdown; right += pright })
    }
    if (result) obs << newob // store loop causers

    result
}

acc =
trajectory.count(loops)


System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == 6)
}


acc