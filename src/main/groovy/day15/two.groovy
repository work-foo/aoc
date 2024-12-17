package day15


import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day15/test3')  ;def expected = 6
//def resource = manager.getInputStream('day15/test2') ;def expected = 9021
def resource = manager.getInputStream('day15/input'); def expected = 1319212

def repl=['#': '##', 'O': '[]', '.':'..', '@':'@.']
def lines = resource.readLines()*.replace(repl)
int blank = lines.findIndexOf {it.empty}

def maze = lines.take(blank)
def input = lines.subList(blank+1, lines.size())

char[][] chars = maze*.toCharArray() as char[][]
input = input.join('')
def depth = chars.length, width = chars[0].length
def onboard = { d, r -> d in 0..<depth && r in 0..<width }

// robot position
int rr, rd =
        chars.findIndexOf {
            rr = it.findIndexOf { it == '@'}
            rr > -1
        }

// recursive call requires definition
def push, push2up;
push = { wd, wr, fd, fr ->
    /* precond
    * where we are
    * force on us*/
//    intended to go
    def (id, ir) = [wd + fd,wr + fr]
    // whats there already
    def what = chars[id][ir]
    def wall = what == '#'
    def empty = what == '.'
    def left  = what == '['
    def right = what == ']'

    // am i the robot
    def robot = chars[wd][wr] == '@'
    if (wall) {
        return false
    } else if (empty || fd == 0 && push(id, ir, fd, fr) || fr==0 && ( left && push2up(id, ir, ir+1, fd, fr) || right && push2up(id, ir-1, ir, fd, fr))) { // if empty, or can push next in line (make empty)
        chars[id][ir] = chars[wd][wr] // move where to intended
        chars[wd][wr] = '.' // clear where
        if (robot) { // if was robot, note the new position
            rd = id
            rr = ir
        }
        return true
    }

    /* postcond
    * if you couldn't push nothing changes
    * if you push left/right then if there is space everything shifts in row
    * if you push up/down then the full width must be available*/

}
push2up = { wd, wr1, wr2, fd, fr ->
    /* precond
    * where we are
    * force on us*/
//    intended to go
    def (id, ir1, ir2) = [wd + fd,wr1 + fr, wr2 + fr]
    // whats there already
    def what1 = chars[id][ir1]
    def what2 = chars[id][ir2]
    def wall = what1 == '#' || what2 == '#'
    def lempty = what1 == '.'
    def lleft = what1 == '['
    def lright = what1 == ']'
    def rempty = what2 == '.'
    def rleft = what2 == '['
    def rright = what2 == ']'

    if (wall) {
        return false
    } else {
        def e = lempty && rempty, m = lleft && rright
        boolean ret = true;

        if (m) ret &= push2up(id, ir1, ir2, fd, fr)
        if (lright) ret &= push2up(id, ir1-1, ir1, fd, fr)
        if (rleft) ret &= push2up(id, ir2, ir2+1, fd, fr)

        chars[id][ir1] = chars[wd][wr1] // move where to intended
        chars[wd][wr1] = '.' // clear where
        chars[id][ir2] = chars[wd][wr2] // move where to intended
        chars[wd][wr2] = '.' // clear where
        return ret
    }
}

for (m in input) {
//    force
    def (fd, fr) = switch (m) {
        case '^' -> [-1,0]
        case 'v' -> [1,0]
        case '<' -> [0,-1]
        case '>' -> [0,1]
        default -> assert false
    }
    char[][] copy = new char[chars.length][]
    int i=0
    for (a in chars) {
        copy[i++] = Arrays.copyOf(a, a.length)
    }

    if (!push(rd, rr, fd, fr)) { // revert when failed to push
        chars = copy
    }
}

chars.eachWithIndex { char[] row, int d ->
    row.eachWithIndex { char ch, int r ->
        if (ch == '[') acc += 100*d + r
    }
}

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
