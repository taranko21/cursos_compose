package com.example.calculator.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class IvaCalculatorViewModel : ViewModel() {
    var price by mutableStateOf("")
        private  set

    var iva by mutableStateOf("")
        private set

    var result by mutableStateOf("")
    var total by mutableStateOf("")

    fun onValue(field: String, value: String) {
        when (field) {
            "price" -> price = value
            "iva" -> iva = value
        }
    }
    fun calculateIva(): String {
        val price = price.toFloat()
        val iva = iva.toFloat()
        result = (price * iva / 100).toString()
        total = (price + result.toFloat()).toString()
        return Pair(result, total).toString()
    }
    fun clear() {
        price = ""
        iva = ""
        result = ""
        total = ""
    }
}