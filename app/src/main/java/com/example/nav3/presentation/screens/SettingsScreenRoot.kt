package com.example.nav3.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.nav3.navigation.ResultStore


@Composable
fun SettingsScreenRoot(
    resultStore: ResultStore,
    onNavigate:()-> Unit
){

    val settings = resultStore.getResult<String>("main_setting")

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ){

        Text(text = "Saved setting: $settings")

        Button(onClick = {
            onNavigate()
        }) {

            Text("Change Settings")
        }



    }

}