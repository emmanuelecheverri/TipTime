package com.emmanuelecheverri.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emmanuelecheverri.tiptime.ui.theme.TipTimeTheme
import kotlin.math.ceil

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TipTimeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    TipTimeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TipTimeScreen(
    modifier: Modifier = Modifier
) {

    var amountInput by rememberSaveable {
        mutableStateOf("")
    }

    var tipInput by rememberSaveable {
        mutableStateOf("")
    }

    var roundUp by rememberSaveable {
        mutableStateOf(false)
    }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(
        amount = amount,
        tipPercent = tipPercent,
        roundUp = roundUp
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.title_tip_calculator)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = amountInput,
            onValueChange = {
                amountInput = it
            },
            label = {
                Text(
                    text = stringResource(R.string.bill_amount)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = tipInput,
            onValueChange = {
                tipInput = it
            },
            label = {
                Text(
                    text = stringResource(R.string.tip_percentage)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(R.string.round_tip)
            )

            Switch(
                checked = roundUp,
                onCheckedChange = {
                    roundUp = it
                }
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                // El resultado se actualiza automáticamente
                // cuando cambia el estado de los campos.
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.calculate_tip)
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = stringResource(
                R.string.tip_result,
                "%.2f".format(tip)
            )
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = stringResource(R.string.student_name)
        )
    }
}

fun calculateTip(
    amount: Double,
    tipPercent: Double,
    roundUp: Boolean
): Double {

    val tip = amount * tipPercent / 100

    return if (roundUp) {
        ceil(tip)
    } else {
        tip
    }
}

@Preview(showBackground = true)
@Composable
fun TipTimePreview() {
    TipTimeTheme {
        TipTimeScreen()
    }
}