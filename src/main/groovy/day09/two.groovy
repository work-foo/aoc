package day09

import day06.Position
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

import java.util.regex.Pattern
import java.util.stream.Stream

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day09/test') //34
//def resource = manager.getInputStream('day09/test2') //9
def resource = manager.getInputStream('day09/input')
// right left
def lines = resource.readLines()
def chars = lines*.toCharArray() as char[][]
int width = chars[0].length, depth = chars.length
def onboard = { d, r -> d in 0..<depth && r in 0..<width }
//Pattern pattern = ~/(\d+): (\d+(?: \d+)*)/


System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == 34)
}


acc