package day04

import groovy.transform.Field
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

long acc = 0

@Field String xmas = 'XMAS', smax = xmas.reverse();

def manager = new ClasspathResourceManager()
def resource = manager.getInputStream('day04/input')
// right left
def lines = resource.withCloseable { it.readLines() }

// down up
def chars = lines*.toCharArray() as char[][]
List<String> transposedLines = chars*.toList().transpose()*.join('')

// slash
int width = chars[0].length, height = chars.length
def top   = [[0]*width, (0..<width)].transpose() as Set
def left  = [(0..<height), [0]*height].transpose() as Set
def right = [(0..<height), [width-1]*height].transpose() as Set

List<String> slash =
(top + right).collect { sy, sx ->
    new StringBuilder().tap {
        [(sy..<height), (sx..0)]*.toList().transpose().each { y, x ->
            append(chars[y][x])
        }
    }.toString()
}

// backslash
List<String> backslash =
(left + top).collect { sy, sx ->
    new StringBuilder().tap {
        [(sy..<height), (sx..<width)]*.toList().transpose().each { y, x ->
            append(chars[y][x])
        }
    }.toString()
}

acc = [lines, transposedLines, backslash, slash].sum { count_xmas_smax(it)}


System.out.withPrintWriter { pw ->
    pw.println(acc)
}

def count_xmas_smax(List<String> lines) {
    lines.sum {
        (it =~ xmas).getCount() + (it =~ smax).getCount()
    } as int
}

acc