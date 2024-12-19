package day17


import org.codehaus.groovy.tools.groovydoc.ClasspathResourceManager

StringJoiner acc = new StringJoiner(',')

def manager = new ClasspathResourceManager()
//def resource = manager.getInputStream('day17/test')  ;def expected = '4,6,3,5,6,3,5,2,1,0'
//def resource = manager.getInputStream('day17/test2') ;def expected = 11048
//def resource = manager.getInputStream('day17/test3') ;def expected = 14+1000+4
def resource = manager.getInputStream('day17/input'); def expected = '1,5,7,4,1,6,0,3,0'
def lines = resource.readLines()
int blank = lines.findIndexOf {it.empty}
def registers = lines.take(blank).findResults { (it =~ /\d+/)[0].toInteger()}
def program = (lines.get(blank+1) =~ /\d(?:,\d)+$/)[0].split(',')*.toInteger()

int ip = 0

def combo = {
    if (it <= 3) it
    else if (it <= 6) registers[it-4]
    else 0
}
while (ip < program.size()) {
    def opcode = program[ip]
    def operand = program[ip+1]
    def comboOperand = combo(operand)
    def shiftA = registers[0] >> Math.min(31, comboOperand )
    def xorB = registers[1] ^ comboOperand
    def opMod = comboOperand % 8
    def anz = registers[0] != 0
    def xorBC = registers[1] ^ registers[2]

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

    println registers
    println acc.toString()

}

/*
* 3 bit ops
* 3 registers unbounded
* instruction is 0..7 followed by operand
* ip += 2
* read beyond halt
* operand parsed by opcode
* literal op is value
* combo op 0123ABC.
*
* adv : A = shift A op
* bxl : B = xor B op
* bst : B = op % 8
* jnz : !A && ip = op
* bxc : B = xor B C
* out : print op % 8
* bdv : B = shift A op
* cdv : C = shift A op
*
*
*
* */


System.out.withPrintWriter { pw ->
    pw.println(acc)
    pw.println(acc.toString() == expected)
}


acc
