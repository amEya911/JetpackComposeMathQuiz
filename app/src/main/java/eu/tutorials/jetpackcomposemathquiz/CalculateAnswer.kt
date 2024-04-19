package eu.tutorials.jetpackcomposemathquiz

class CalculateAnswer {
    fun calculateAnswer(operand: Array<Operand?>, randomNumber: Int): Answer {
        val answer = when (randomNumber) {
            0 -> operand[0]!!.operand + operand[1]!!.operand
            1 -> operand[0]!!.operand - operand[1]!!.operand
            2 -> operand[0]!!.operand * operand[1]!!.operand
            3 -> operand[0]!!.operand / operand[1]!!.operand
            else -> 0
        }

        return Answer(answer)
    }
}