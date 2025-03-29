package com.example.botones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.botones.ui.theme.BotonesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BotonesTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BotonNormal()
        Space()
        BotonNormal2()
        Space()
        BotonTexto()
    }
}

@Composable
fun BotonTexto(){
    TextButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
        containerColor = Color.Red
    )) {
        Text(text = "El boton", fontSize = 30.sp)
    }
}

@Composable
fun BotonNormal(){
    Button(onClick = { /*TODO*/ }, enabled = false) {
        Text(text = "Mi boton", fontSize = 30.sp)
    }
}
@Composable
fun BotonNormal2(){
    Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
        containerColor = Color.Blue
    )) {
        Text(text = "Mi boton", fontSize = 20.sp)
    }
}

@Composable
 fun Space(){
    Spacer(modifier = Modifier.height(10.dp))
}