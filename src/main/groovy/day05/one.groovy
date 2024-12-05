package day05

import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

import java.util.regex.Pattern

long acc = 0

def manager = new ClasspathResourceManager()
def resource = manager.getInputStream('day05/input')
//def resource = manager.getInputStream('day05/input')
// right left
String text = resource.getText()
Pattern rule = ~/\d\d\|\d\d/, book = ~/\d\d(?:,\d\d)+/

def rules = text =~ rule
def edges = rules.iterator().collect { String it ->
    it.split(/\|/)*.toInteger()
}.toSet()

def books = text =~ book
acc = books.iterator().collect { String it ->
    it.split(',')*.toInteger()
}.findAll {
    it.inits().init().init().every {
        def last = it.last()
        !it.init().any {
            [last, it] in edges
        }
    }
}.sum {
    it[it.size() / 2]
}

System.out.withPrintWriter { pw ->
    pw.println(acc)
}


acc