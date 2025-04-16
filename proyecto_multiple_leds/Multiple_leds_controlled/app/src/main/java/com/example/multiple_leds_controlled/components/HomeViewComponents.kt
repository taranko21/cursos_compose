package com.example.multiple_leds_controlled.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.multiple_leds_controlled.R.drawable.red_light
import com.example.multiple_leds_controlled.R.drawable.green_light

@Composable
fun HomeViewComponents(
    onClick: (String) -> Unit,
    text: String,
    contentDescription: String,
    color: Color,
    ledPath:String
    ) {
    var showFirstImage by remember { mutableStateOf(true) }
    var isOn by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically){
        Button(onClick = {
            isOn = !isOn
            val fullPath = "$ledPath/${if (isOn) "on" else "off"}"
            onClick(fullPath)
            showFirstImage = !showFirstImage
            },
            modifier = Modifier
            .padding(25.dp)
            .width(140.dp)
            .height(35.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = Color.Black
            ),
        ) {
            Text(text = text)
        }
        Spacer(modifier = Modifier.size(5.dp))
        Image(
            painter = painterResource(id = if (showFirstImage) red_light else green_light),
            modifier = Modifier.size(100.dp),
            contentDescription = contentDescription.toString()
        )
    }
}