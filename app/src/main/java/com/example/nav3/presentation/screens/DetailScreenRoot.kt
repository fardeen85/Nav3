package com.example.nav3.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DetailScreenRoot(
    id: String,
){

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

        Text(text = "Details Screen ${id}")



    }
}