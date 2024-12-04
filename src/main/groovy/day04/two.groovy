package day04


import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

int acc = 0

def manager = new ClasspathResourceManager()
def resource = manager.getInputStream('day04/input')
def lines = resource.withCloseable { it.readLines() }
char[][] chars = lines*.toCharArray().toArray();
int width = chars[0].length, height = chars.length

acc = chars.toList().withIndex()
        .drop(1).take(height - 2)
        .collectMany { inner, y ->
            inner.toList()
                    .drop(1).take(width - 2)
                    .findIndexValues { it == 'A' }
                    .collect { apos -> [y, apos + 1] }
        }
        .count { ay, ax ->
            new StringBuilder().tap {
                int yd = ay - 1
                int yu = ay + 1
                int xd = ax - 1
                int xu = ax + 1
                append(chars[yd][xd])
                append(chars[yu][xu])
                append(chars[yd][xu])
                append(chars[yu][xd])
            }.toString() ==~ /(MS|SM){2}/
}


System.out.withPrintWriter { pw -> pw.println(acc)
}

acc