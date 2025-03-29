package com.example.calculator.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.calculator.components.Buttons
import com.example.calculator.components.TextfieldsComponent
import com.example.calculator.viewModels.IvaCalculatorViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IvaView(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Iva Calculator", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() } ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Localized description")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0559E2)),
            )
        }
    ) {
        ContentIvaView(it)
    }
}

@Composable
fun ContentIvaView(paddingValues: PaddingValues, viewModel: IvaCalculatorViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(10.dp)
            .fillMaxSize(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        TextfieldsComponent(newValue = viewModel.price, newlabel = "Price", onValueChange = { viewModel.onValue("price",it) })
        TextfieldsComponent(newValue = viewModel.iva, newlabel = "IVA", onValueChange = { viewModel.onValue("iva",it) })
        Buttons(txt = "Calculate", MaterialTheme.colorScheme.secondary, onClick = { viewModel.calculateIva() })
        Buttons(txt = "Erase", color = Color.Red, onClick = { viewModel.clear() })
        if(viewModel.result.isNotEmpty()) {
            Text("Result: ${viewModel.result}")
            Text("Total: ${viewModel.total}")
        }
    }
}