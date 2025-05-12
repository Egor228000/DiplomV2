package com.example.diplomv2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diplomv2.data.Screens
import com.example.diplomv2.ui.theme.CustomButton
import com.example.diplomv2.ui.theme.CustomOutlinedTextField

@Composable
fun ReginScreen(
    navigation: NavHostController,
) {

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }



    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 50.dp)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        items(1) {


            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomOutlinedTextField(
                    valueText = login,
                    onValueChangeText = { login = it },
                    label = { Text("Логин") },
                )
                CustomOutlinedTextField(
                    valueText = password,
                    onValueChangeText = { password = it },
                    label = { Text("Пароль") },
                )
                CustomButton(
                    onClick = {
                        navigation.navigate(Screens.Login.route)
                    },
                    "Зарегистрироваться",
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
        }
    }
}