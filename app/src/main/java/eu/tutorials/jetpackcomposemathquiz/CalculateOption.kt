package eu.tutorials.jetpackcomposemathquiz

import java.util.Random

class CalculateOption {
    fun generateOptions(answer: Int): Array<Option?> {
        val random = Random()
        val options = arrayOfNulls<Option>(3)
        val option1 = answer + random.nextInt(16) + 2
        var option2: Int
        do {
            option2 = answer - random.nextInt(16) - 1
        } while (option2 <= 0)

        options[0] = Option(option1, false)
        options[1] = Option(option2, false)
        options[2] = Option(answer, true)

        return options
    }
}