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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.diplomv2.data.Screens

@Composable
fun SettingsScreen(
    navigation: NavHostController,
) {

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 50.dp)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        items(1) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(1f),
                    onClick = {navigation.navigate(Screens.Achievement.route)}
                ) {
                    Column(
                        Modifier.padding(16.dp)
                    ) {
                        Text("Достижение")

                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(1f),
                    onClick = { navigation.navigate(Screens.Statistics.route)}
                ) {
                    Column(
                        Modifier.padding(16.dp)
                    ) {
                        Text("Статистика")

                    }

                }
            }
        }



    }
}
