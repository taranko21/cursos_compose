package com.example.base_datos_room.components

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun TextFieldComponent(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    TextField(value=value, onValueChange=onValueChange, label={ androidx.compose.material3.Text(label) })
}
