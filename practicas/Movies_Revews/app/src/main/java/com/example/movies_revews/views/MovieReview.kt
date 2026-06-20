package com.example.movies_revews.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviewReviewScreen(navController: NavHostController, modifier: Modifier) {
    Scaffold {it
        TopAppBar(
            title = { Text(text = "Movies") },
            navigationIcon = {
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(
                        painter = painterResource(id = com.example.movies_revews.R.drawable.arrow_back),
                        contentDescription = "Menu Icon"
                    )
                }
            }
        )
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            Text("Daniel is the best")
        }
    }
}

