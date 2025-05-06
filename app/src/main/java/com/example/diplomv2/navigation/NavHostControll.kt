package com.example.diplomv2.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.diplomv2.data.Screens
import com.example.diplomv2.screens.AchievementScreen
import com.example.diplomv2.tasks.ExpressChallengeScreen
import com.example.diplomv2.tasks.GameScreen
import com.example.diplomv2.tasks.GeometryStationScreen
import com.example.diplomv2.tutorial.LearningScreen
import com.example.diplomv2.screens.LoginScreen
import com.example.diplomv2.screens.MainScreen
import com.example.diplomv2.tasks.MathRailsScreen
import com.example.diplomv2.screens.ReginScreen
import com.example.diplomv2.screens.SettingsScreen
import com.example.diplomv2.screens.StatisticsScreen
import com.example.diplomv2.screens.WelcomScreen
import com.example.diplomv2.tutorial.LearnAdditionScreen
import com.example.diplomv2.tutorial.LearnDivisionScreen
import com.example.diplomv2.tutorial.LearnMultiplicationScreen
import com.example.diplomv2.tutorial.LearnShapesScreen
import com.example.diplomv2.tutorial.LearnSubtractionScreen
import com.example.diplomv2.view.ExpressQuizViewModel
import com.example.diplomv2.view.MathQuizViewModel
import com.example.diplomv2.view.ShapeGameViewModel

@Composable
fun NavHostControll(
    navigation: NavHostController,
    paddingValues: PaddingValues,
    mathQuizViewModel: MathQuizViewModel,
    expressQuizViewModel: ExpressQuizViewModel,
    shapeGameViewModel: ShapeGameViewModel
) {
    NavHost(
        navController = navigation,
        startDestination = Screens.Main.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screens.Welcome.route) {
            WelcomScreen(
                navigation,
                paddingValues,
            )
        }
        composable(Screens.Regin.route) {
            ReginScreen(
                navigation,
                paddingValues,
            )
        }
        composable(Screens.Login.route) {
            LoginScreen(
                navigation,
                paddingValues,
            )
        }
        composable(Screens.Main.route) {
            MainScreen(
                navigation,
                paddingValues,
            )
        }
        composable(Screens.Achievement.route) {
            AchievementScreen(
                navigation,
                paddingValues,
            )
        }
        composable(Screens.Settings.route) {
            SettingsScreen(
                navigation,
                paddingValues,
            )
        }


        composable(Screens.MathRails.route) {
            MathRailsScreen(
                navigation,
                paddingValues,
                mathQuizViewModel
            )
        }
        composable(Screens.ExpressChallenge.route) {
            ExpressChallengeScreen(
                navigation,
                paddingValues,
                expressQuizViewModel
            )
        }
        composable(Screens.GeometryStation.route) {
            GeometryStationScreen(
                navigation,
                paddingValues,
                shapeGameViewModel
            )
        }
        composable(Screens.Game.route) {
            GameScreen(
                mathQuizViewModel,
                onBack = { navigation.popBackStack() }
            )
        }
        composable(Screens.Statistics.route) {
            StatisticsScreen(mathQuizViewModel, expressQuizViewModel, shapeGameViewModel)
        }
        composable(Screens.Learning.route) {
            LearningScreen(
                navigation,
                paddingValues,
            )
        }
        composable(Screens.LearnAddition.route) {
            LearnAdditionScreen(
                navigation,
                paddingValues,
            )
        }
        composable(Screens.LearnSubtraction.route) {
            LearnSubtractionScreen(
                navigation,
                paddingValues,
            )
        }
        composable(Screens.LearnMultiplication.route) {
            LearnMultiplicationScreen(
                navigation,
                paddingValues,
            )
        }
        composable(Screens.LearnDivision.route) {
            LearnDivisionScreen(
                navigation,
                paddingValues,
            )
        }
        composable(Screens.LearnShapes.route) {
            LearnShapesScreen(
                navigation,
                paddingValues,
            )
        }
    }
}