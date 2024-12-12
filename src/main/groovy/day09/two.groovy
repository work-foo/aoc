package day09

import day06.Position
import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

import java.util.function.Supplier
import java.util.regex.Pattern

long acc

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day09/test') /* 0099811188827773336446555566 */ ;def expected = 2858
//def resource = manager.getInputStream('day09/test2'); /* 022111222 */ def expected = 132
def resource = manager.getInputStream('day09/input'); def expected = 6418529470362
def text = resource.getText()


def alternate = new Supplier<Boolean>() {
    boolean next = false;

    @Override
    Boolean get() {
        return next = !next
    }
}

def filenamer = new Supplier<Integer>() {
    int fileid = 0;

    @Override
    Integer get() {
        return this.fileid++
    }
}

def blocks = text.toCharArray().collect(new LinkedList<>()) {

    def file = alternate.get()
    def fileid = file ? Optional.of(filenamer.get()) : Optional.empty()
    new Block(fileid, Character.getNumericValue(it));
}


int position=0;

while (blocks) {
    /* block processing
    * use head block
    * while head free pull from rear */
    def first = blocks.removeFirst()

    if (first.fileid()) {
        for(l in 1..first.length()) {
            acc += first.fileid().get() * position++
        }
    } else {
        def want = first.length()

        def back = blocks.listIterator(blocks.size())

        while (want) {
            if (back.hasPrevious()) {
                def previous = back.previous()
                def has = previous.length()

                if (previous.fileid() && has <= want) {
                    back.set(new Block(first.fileid(), has)) // clear the tail
                    if (has < want) {
                        blocks.addFirst(new Block(first.fileid(), want - has)) // new blank
                    }
                    blocks.addFirst(previous)
                    want = 0
                }
            } else {
                position += first.length()
                break
            }
        }
    }
}


System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc == expected)
}


acc