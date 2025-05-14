package com.example.diplomv2.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.diplomv2.data.Screens
import com.example.diplomv2.screens.AchievementScreen
import com.example.diplomv2.screens.AiScreen
import com.example.diplomv2.screens.LoginScreen
import com.example.diplomv2.screens.MainScreen
import com.example.diplomv2.screens.ReginScreen
import com.example.diplomv2.screens.SettingsScreen
import com.example.diplomv2.screens.StatisticsScreen
import com.example.diplomv2.screens.WelcomScreen
import com.example.diplomv2.tasks.ExpressChallengeScreen
import com.example.diplomv2.tasks.GameScreen
import com.example.diplomv2.tasks.GeometryStationScreen
import com.example.diplomv2.tasks.MathRailsScreen
import com.example.diplomv2.tutorial.LearnAdditionScreen
import com.example.diplomv2.tutorial.LearnDivisionScreen
import com.example.diplomv2.tutorial.LearnMultiplicationScreen
import com.example.diplomv2.tutorial.LearnShapesScreen
import com.example.diplomv2.tutorial.LearnSubtractionScreen
import com.example.diplomv2.tutorial.LearningScreen
import com.example.diplomv2.view.AiBotViewModel
import com.example.diplomv2.view.ExpressQuizViewModel
import com.example.diplomv2.view.MathQuizViewModel
import com.example.diplomv2.view.ShapeGameViewModel


@Composable
fun NavHostControll(
    navigation: NavHostController,
    paddingValues: PaddingValues,
    mathQuizViewModel: MathQuizViewModel,
    expressQuizViewModel: ExpressQuizViewModel,
    shapeGameViewModel: ShapeGameViewModel,
    aiBotViewModel: AiBotViewModel,
) {


    NavHost(
        navController = navigation,
        startDestination = Screens.Ai.route,

        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(400)
            ) + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 1000 },
                animationSpec = tween(400)
            ) + fadeOut()
        }
    ) {
        composable(Screens.Welcome.route) {
            WelcomScreen(
                navigation,
            )
        }
        composable(Screens.Regin.route) {
            ReginScreen(
                navigation,
            )
        }
        composable(Screens.Login.route) {
            LoginScreen(
                navigation,
            )
        }
        composable(Screens.Main.route) {
            MainScreen(
                navigation,
            )
        }
        composable(Screens.Achievement.route) {
            AchievementScreen(
                navigation,
            )
        }
        composable(Screens.Settings.route) {
            SettingsScreen(
                navigation,
            )
        }


        composable(Screens.MathRails.route) {
            MathRailsScreen(
                navigation, mathQuizViewModel
            )
        }
        composable(Screens.ExpressChallenge.route) {
            ExpressChallengeScreen(
                navigation, expressQuizViewModel
            )
        }
        composable(Screens.GeometryStation.route) {
            GeometryStationScreen(
                navigation, shapeGameViewModel
            )
        }
        composable(Screens.Game.route) {
            GameScreen(
                mathQuizViewModel, onBack = { navigation.popBackStack() })
        }
        composable(Screens.Statistics.route) {
            StatisticsScreen(
                mathQuizViewModel,
                expressQuizViewModel,
                shapeGameViewModel
            )
        }
        composable(Screens.Learning.route) {
            LearningScreen(
                navigation,
            )
        }
        composable(Screens.LearnAddition.route) {
            LearnAdditionScreen(
                navigation,
            )
        }
        composable(Screens.LearnSubtraction.route) {
            LearnSubtractionScreen(
                navigation,
            )
        }
        composable(Screens.LearnMultiplication.route) {
            LearnMultiplicationScreen(
                navigation,
            )
        }
        composable(Screens.LearnDivision.route) {
            LearnDivisionScreen(
                navigation,
            )
        }
        composable(Screens.LearnShapes.route) {
            LearnShapesScreen(
                navigation,
            )
        }
        composable(Screens.Ai.route) {
            AiScreen(
                navigation, aiBotViewModel
            )
        }
    }
}