import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import java.math.BigDecimal



@Composable
@Preview
fun App() {

    var displayText by remember { mutableStateOf("") }


    val labels = listOf(
        "Please enter the income",
        "Please enter the expenses",
        "Please enter the debt/loan",
        "Please enter the interest",
        "Please enter the type of loan"
    )
    val filePath = "/Users/ivanstepanov/Desktop/git/CSV_Editor_KotlinVersion/src/Data.csv"
    val calculation = CalculationCsv()
    val writer = CsvWriter()
    var errorText by remember { mutableStateOf("") }

    var income by remember { mutableStateOf("") }
    var expenses by remember { mutableStateOf("") }
    var debt by remember { mutableStateOf("") }
    var interest by remember { mutableStateOf("") }
    var loanType by remember { mutableStateOf("") }
    var totalIncome by remember { mutableStateOf(BigDecimal.ZERO) }
    var totalExpenses by remember { mutableStateOf(BigDecimal.ZERO) }
    var netIncome by remember { mutableStateOf(BigDecimal.ZERO) }
    var totalDebt by remember { mutableStateOf(BigDecimal.ZERO)}
    var totalApr by remember { mutableStateOf(BigDecimal.ZERO)}


    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.padding(16.dp)
            ) {

              OutlinedTextField(
                  value = income,
                  onValueChange = {income = it},
                  label = { Text("Income", style = TextStyle(fontSize = 20.sp)) },
                  modifier = Modifier.fillMaxWidth(),
                  colors = TextFieldDefaults.outlinedTextFieldColors(
                      focusedBorderColor = Color(0xFF2274A5),
                      focusedLabelColor = Color(0xFF2274A5),
                      cursorColor = Color(0xFF2274A5))
              )
                OutlinedTextField(
                    value = expenses,
                    onValueChange = {expenses = it},
                    label = { Text("Expenses", style = TextStyle(fontSize = 20.sp)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF2274A5),
                        focusedLabelColor = Color(0xFF2274A5),
                        cursorColor = Color(0xFF2274A5))
                )
                OutlinedTextField(
                    value = debt,
                    onValueChange = {debt = it},
                    label = { Text("Debt/ Loan", style = TextStyle(fontSize = 20.sp)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF2274A5),
                        focusedLabelColor = Color(0xFF2274A5),
                        cursorColor = Color(0xFF2274A5))
                )
                OutlinedTextField(
                    value = interest,
                    onValueChange = {interest = it},
                    label = { Text("Interest", style = TextStyle(fontSize = 20.sp)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF2274A5),
                        focusedLabelColor = Color(0xFF2274A5),
                        cursorColor = Color(0xFF2274A5))
                )
                OutlinedTextField(
                    value = loanType,
                    onValueChange = {loanType = it},
                    label = { Text("Loan Type", style = TextStyle(fontSize = 20.sp)) },
                    modifier = Modifier.fillMaxWidth(),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF2274A5),
                        focusedLabelColor = Color(0xFF2274A5),
                        cursorColor = Color(0xFF2274A5))
                )
                Button(
                    onClick = {
                        if (income.isBlank() || expenses.isBlank() || debt.isBlank() || interest.isBlank() || loanType.isBlank()) {
                            displayText = "Please fill in all the fields!"
                        } else {
                        val collectedData = listOf(income, expenses, debt, interest, loanType)
                        writer.writeToCsv(filePath, collectedData)
                        income = ""
                        expenses = ""
                        debt = ""
                        interest = ""
                        loanType = ""
                        displayText = "Data successfully added to $filePath"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF2274A5),
                        contentColor = Color.White
                ),
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text("Submit Data")
                }

                Spacer(modifier = Modifier.height(20.dp))


                Button(
                    onClick = {
                        totalIncome = calculation.sumOfIncome(filePath)
                        displayText = "Total Income: $$totalIncome"

                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF2274A5),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Total of Income")
                }

                Button(
                    onClick = {
                        totalExpenses = calculation.sumOfExpense(filePath)
                        displayText = "Total Expenses: $$totalExpenses"

                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF2274A5),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Total of Expenses")
                }

                Button(
                    onClick = {
                        netIncome = calculation.netIncome(filePath)
                        displayText = "Net Income: $$netIncome"
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF2274A5),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Net Income")
                }

                Button(
                    onClick = {
                        totalDebt = calculation.sumOfDebt(filePath)
                        displayText = "Total Debt/Loan: $$totalDebt"
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF2274A5),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Total of Debt/Loan")
                }

                Button(
                    onClick = {
                        totalApr = calculation.averageInterestRate(filePath)
                        displayText = "APR: $totalApr%"
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF2274A5),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("APR")
                }
                Text(
                    text = displayText,
                    style = TextStyle(fontSize = 28.sp),
                    modifier = Modifier.fillMaxWidth(),
                    color = if((displayText.startsWith("Total Expenses") || displayText.startsWith("Please fill in all the fields!")) && totalExpenses >= BigDecimal(10000)) Color.Red else Color.Black,

                )

            }
        }
    }
}


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Financial App"
    ) {
        App()
    }
}
