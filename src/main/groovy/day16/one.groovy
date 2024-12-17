package day16


import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day16/test')  ;def expected = 2028
//def resource = manager.getInputStream('day16/test2') ;def expected = 10092
def resource = manager.getInputStream('day16/test3') ;def expected = 14+1000+3
//def resource = manager.getInputStream('day16/input'); def expected = 1294459
def lines = resource.readLines()

char[][] chars = lines*.toCharArray() as char[][]
def depth = chars.length, width = chars[0].length
def onboard = { d, r -> d in 0..<depth && r in 0..<width }


System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc
