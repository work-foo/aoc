package day05

import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

import java.util.regex.Matcher
import java.util.regex.Pattern

boolean[] expected = [true, true, true, false, false, false]
long acc = 0

def manager = new ClasspathResourceManager()
def resource = manager.getInputStream('day05/test')
//def resource = manager.getInputStream('day05/input')
// right left
String text = resource.getText()
Pattern rule = ~/\d\d\|\d\d/, book = ~/\d\d(?:,\d\d)+/

def rules = text =~ rule
def edges = rules.iterator().collect { String it ->
    it.split(/\|/)*.toInteger()
}

def books = text =~ book
def bookpages = books.iterator().collect { String it ->
    it.split(',')*.toInteger()
}



while(false);


System.out.withPrintWriter { pw ->
    pw.println(acc)
}


acc