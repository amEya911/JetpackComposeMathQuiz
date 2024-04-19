package eu.tutorials.jetpackcomposemathquiz

import java.util.Random

class CalculateOperands {
    fun generateOperands(): Array<Operand?> {
        val operands = arrayOfNulls<Operand>(2)
        val random = Random()
        val randomOperator1 = random.nextInt(15) + 1
        val randomOperator2 = random.nextInt(15) + 2

        operands[0] = Operand(randomOperator1 * randomOperator2)
        operands[1] = Operand(randomOperator1)

        return operands
    }
}