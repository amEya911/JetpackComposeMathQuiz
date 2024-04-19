package eu.tutorials.jetpackcomposemathquiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Arrays
import java.util.Random

fun question(operand: Array<Operand?>, randomNumber: Int): String {
    return when (randomNumber) {
        0 -> "${operand[0]!!.operand}  +  ${operand[1]!!.operand}"
        1 -> "${operand[0]!!.operand}  -  ${operand[1]!!.operand}"
        2 -> "${operand[0]!!.operand}  x  ${operand[1]!!.operand}"
        3 -> "${operand[0]!!.operand}  ÷  ${operand[1]!!.operand}"
        else-> ""
    }
}


@Composable
fun GameLogic() {

    val green = ButtonDefaults.buttonColors(Color(0xFF74db90))
    val gray = ButtonDefaults.buttonColors(Color(0xFF5c5e5c))
    val lightRed = ButtonDefaults.buttonColors(Color(0xFFe69f9a))
    val lightBlue = ButtonDefaults.buttonColors(Color(0xFF9aa1e6))

    val player1 = Player("player1", eu.tutorials.jetpackcomposemathquiz.Color.RED)
    val player2 = Player("player2", eu.tutorials.jetpackcomposemathquiz.Color.BLUE)
    var player1Score by remember { mutableIntStateOf(0) }
    var player2Score by remember { mutableIntStateOf(0) }
    var rightAnswer by remember { mutableStateOf(false) }
    var wrongAnswer by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val calculateOperands = CalculateOperands()
    val calculateAnswer = CalculateAnswer()
    val random = Random()

    val operand = calculateOperands.generateOperands()
    val randomNumber = random.nextInt(4)

    val answer: Int = calculateAnswer.calculateAnswer(operand, randomNumber).answer

    val calculateOption = CalculateOption()
    val option = calculateOption.generateOptions(answer)

    val options = Arrays.asList(*option)
    options.shuffle()


    @Composable
    fun DisplayQuestion(rotation: Float) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .rotate(rotation),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = question(operand, randomNumber),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 56.sp
            )
        }
    }

    @Composable
    fun DisplayLine(color: Color) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = color,
            thickness = 12.dp
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .rotate(180F)
                .height(64.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (options[0]!!.option == answer) {
                        player2Score++
                        rightAnswer = true
                        scope.launch {
                            delay(1500)
                            rightAnswer = false
                        }

                    } else {
                        player1Score++
                        wrongAnswer = true
                        scope.launch {
                            delay(1500)
                            wrongAnswer = false
                        }
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(200.dp)
                    .width(100.dp),
                colors = if (rightAnswer) green else if (wrongAnswer) gray else lightBlue,
                border = BorderStroke(4.dp, Color.Blue)
            )
            {
                Text(text = "${options[0]!!.option}", color = Color.Blue, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = {
                    if (options[1]!!.option == answer) {
                        player2Score++
                    } else {
                        player1Score++
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(200.dp)
                    .width(100.dp),
                colors = lightBlue,
                border = BorderStroke(4.dp, Color.Blue)
            ) {
                Text(text = "${options[1]!!.option}", color = Color.Blue, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (options[2]!!.option == answer) {
                        player2Score++
                    } else {
                        player1Score++
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(200.dp)
                    .width(100.dp),
                colors = lightBlue,
                border = BorderStroke(4.dp, Color.Blue)
            ) {
                Text(text = "${options[2]!!.option}", color = Color.Blue, fontSize = 24.sp)
            }
        }

        DisplayLine(color = Color.Blue)

        DisplayQuestion(rotation = 180F)

        Spacer(modifier = Modifier.height(75.dp))


        Row(
            modifier = Modifier
                .rotate(-90F)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "$player1Score", color = Color.Red, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "$player2Score", color = Color.Blue, fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(75.dp))

        DisplayQuestion(rotation = 0F)

        DisplayLine(color = Color.Red)

        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(64.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (options[0]!!.option == answer) player1Score++ else player2Score++
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(200.dp)
                    .width(100.dp),
                colors = lightRed,
                border = BorderStroke(4.dp, Color.Red)
            ) {
                Text(text = "${options[0]!!.option}", color = Color.Red, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (options[1]!!.option == answer) player1Score++ else player2Score++
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(200.dp)
                    .width(100.dp),
                colors = lightRed,
                border = BorderStroke(4.dp, Color.Red)
            ) {
                Text(text = "${options[1]!!.option}", color = Color.Red, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (options[2]!!.option == answer) player1Score++ else player2Score++
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(200.dp)
                    .width(100.dp),
                colors = lightRed,
                border = BorderStroke(4.dp, Color.Red)
            ) {
                Text(text = "${options[2]!!.option}", color = Color.Red, fontSize = 24.sp)
            }
        }

//            try {
//                Thread.sleep(1500)
//
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//
//            }
//
//            rightAnswer = false
//            wrongAnswer = false
    }
}