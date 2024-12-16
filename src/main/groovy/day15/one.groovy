package day15


import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day15/test')  ;def expected = 2028
//def resource = manager.getInputStream('day15/test2') ;def expected = 10092
def resource = manager.getInputStream('day15/input'); def expected = 1294459
def lines = resource.readLines()
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
def push;
push = { wd, wr, fd, fr ->
//    intended
    def (id, ir) = [wd + fd,wr + fr]
    def what = chars[id][ir]
    def wall = what == '#'
    def empty = what == '.'

    def robot = chars[wd][wr] == '@'
    if (wall) {
        return false
    } else if (empty || push(id, ir, fd, fr)) { // if empty, or can push next in line (make empty)
        chars[id][ir] = chars[wd][wr] // move where to intended
        chars[wd][wr] = '.' // clear where
        if (robot) { // if was robot, note the new position
            rd = id
            rr = ir
        }
        return true
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
    push(rd, rr, fd, fr)
}

chars.eachWithIndex { char[] row, int d ->
    row.eachWithIndex { char ch, int r ->
        if (ch == 'O') acc += 100*d + r
    }
}

System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
