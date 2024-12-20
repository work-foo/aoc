package day17


import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager


def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day17/test')  ;def expected = '4,6,3,5,6,3,5,2,1,0'
//def resource = manager.getInputStream('day17/test2') ;def expected = 11048
//def resource = manager.getInputStream('day17/test3') ;def expected = 14+1000+4
def resource = manager.getInputStream('day17/input'); def expected = '2,4,1,3,7,5,4,0,1,3,0,3,5,5,3,0'
def lines = resource.readLines()
int blank = lines.findIndexOf { it.empty }
def registers = lines.take(blank).findResults { (it =~ /\d+/)[0].toLong() }
String fullprogram = (lines.get(blank + 1) =~ /\d(?:,\d)+$/)[0]
def program = fullprogram.split(',')*.toInteger()
long awas = 0

def q = new ArrayDeque<Integer>()
q.addLast(awas)

while (q) {
    long astart = q.removeFirst() * 8
    for (a in (astart..(astart + 7))) {
        if (a ==0) continue
        StringJoiner acc = new StringJoiner(',')
        registers[0] = a

        int ip = 0

        def combo = {
            if (it <= 3) it
            else if (it <= 6) registers[it - 4]
            else 0
        }
        while (ip < program.size()) {
            long opcode = program[ip]
            long operand = program[ip + 1]
            long comboOperand = combo(operand)
            long shiftA = registers[0] >> Math.min(31, comboOperand)
            long xorB = registers[1] ^ comboOperand
            long opMod = comboOperand % 8
            boolean anz = registers[0] != 0
            long xorBC = registers[1] ^ registers[2]

            switch (opcode) {
                case 0:
                    registers[0] = shiftA
                    break
                case 1:
                    registers[1] = xorB
                    break
                case 2:
                    registers[1] = opMod
                    break
                case 4:
                    registers[1] = xorBC
                    break
                case 5:
                    acc.add(opMod.toString())
                    break
                case 6:
                    registers[1] = shiftA
                    break
                case 7:
                    registers[2] = shiftA
                    break
                default: break
            }
            if (opcode == 3 && anz) ip = comboOperand
            else ip += 2

//    println registers
//    println acc.toString()

        }

        if (fullprogram.endsWith(acc.toString())) {
            println "$a $acc"
            q.addLast(a)
        }
        if (fullprogram == acc.toString()) {
            println "found a $a"
            q.clear()
            break;
        }
    }
}
/*
* 3 bit ops
* 3 registers unbounded
* instruction is 0..7 followed by operand
* ip += 2
* read beyond halt
* operand parsed by opcode
* literal op is value
* combo op 01234567
* combo op 0123ABC.
*
* 0adv : A = shift A op
* 1bxl : B = xor B op
* 2bst : B = op % 8
* 3jnz : A<>0 then ip = op
* 4bxc : B = xor B C
* 5out : print op % 8
* 6bdv : B = shift A op
* 7cdv : C = shift A op */
//    pw.println(acc.toString() == expected)

void impl() {
/*
            * 2bst : B = A % 8
            * 1bxl : B1 = xor B 3
            * 7cdv : C = shift A B1
            * 4bxc : B2 = xor B1 C
            * 1bxl : B3 = xor B2 3
            * 0adv : A2 = shift A 3
            * 5out : print B3 % 8
            * 3jnz : A<>0 then ip = 0
*/
    int[] a = new int[1 + 16];
    int i = 0;
    StringJoiner sj = new StringJoiner(',')
    do {
        int b = a[i] % 8,
            b1 = b ^ 3,
            c = a[i] >> b1,
            b2 = b1 ^ c,
            b3 = b2 ^ 3,
            b4 = b3 % 8;
        a[i + 1] = a[i] >> 3;
        sj.add(b4.toString())
    } while (a[++i] != 0);
}

void inverse() {
    /* given an output, compute the minimum of its preimage
    * the domain is register a. output is string
    * the interpreter takes a spec and produces output
    * allocating registers and program
    * accumulate to string for result
    *
    * the inverse function is set valued
    * so an implementation must construct a set
    * the set should efficiently implement .min()
    * and impl(.min())=out
    *
    * the inverse impl seem like a reversed program which outputs sets? because a constraint is a set?
    * the inverse input fixes the results the print and termination constrains a16
    * produce a0 with .min() */
    Object[] a = new Object[1 + 16];
    a[16] = 0;
    int i = 16
    Deque<Integer> out = new ArrayDeque<>([2, 4, 1, 3, 7, 5, 4, 0, 1, 3, 0, 3, 5, 5, 3, 0])
    while (out) {
        int b3 = out.removeLast()
        a[i - 1] = (a[i] << 3)

        int b2 = b3 ^ 3,
            b1 = b2 ^ c


    }


    StringJoiner sj = new StringJoiner(',')
    do {
        int b = a[i] % 8,
            b1 = b ^ 3,
            c = a[i] >> b1,
            b2 = b1 ^ c,
            b3 = b2 ^ 3;
        a[i + 1] = a[i] >> 3;
        sj.add(b3.toString())
    } while (a[++i] != 0);
}
