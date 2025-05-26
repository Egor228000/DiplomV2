package com.example.diplomv2

import android.R.attr.fontFamily
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.diplomv2.data.Screens
import com.example.diplomv2.navigation.NavHostControll
import com.example.diplomv2.screens.AppDatabase
import com.example.diplomv2.tasks.ShapeRepository
import com.example.diplomv2.view.AiBotViewModel
import com.example.diplomv2.view.ExpressQuizViewModel
import com.example.diplomv2.view.ExpressQuizViewModelFactory
import com.example.diplomv2.view.MathQuizViewModel
import com.example.diplomv2.view.MathQuizViewModelFactory
import com.example.diplomv2.view.ShapeGameViewModel
import com.example.diplomv2.view.ShapeGameViewModelFactory

class MainActivity : ComponentActivity() {
    val aiBotViewModel = AiBotViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color(0x00FFFFFF).toArgb(),
                Color(0x00FFFFFF).toArgb()
            ),
            navigationBarStyle = SystemBarStyle.dark(Color(0xFFC1C1C1).toArgb())
        )
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val context = LocalContext.current.applicationContext as Application
            val db = AppDatabase.getInstance(context)
            val shapeRepository = ShapeRepository(db.shapeStatDao())

            val statisticsDao = remember { db.statisticsDao() }
            val mathViewModel: MathQuizViewModel = viewModel(
                factory = MathQuizViewModelFactory(statisticsDao)
            )
            val expressViewModel: ExpressQuizViewModel = viewModel(
                factory = ExpressQuizViewModelFactory(statisticsDao)
            )
            val shapeViewModel: ShapeGameViewModel = viewModel(
                factory = ShapeGameViewModelFactory(shapeRepository)
            )
            val navigation = rememberNavController()
            val navBackStackEntry by navigation.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
                modifier = Modifier
                    .navigationBarsPadding()
                    .statusBarsPadding()
                    .imePadding(),
                containerColor = Color.White,
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(Color.White),
                        navigationIcon = {

                            when (currentRoute) {
                                Screens.Main.route -> {

                                }

                                Screens.Welcome.route -> {

                                }

                                "Загрузка..." -> {

                                }


                                else -> {
                                    IconButton(
                                        onClick = {
                                            navigation.popBackStack()
                                        }
                                    ) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                                    }
                                }
                            }
                        },
                        title = {
                            Text(
                                when (currentRoute) {
                                    Screens.Welcome.route -> "Првиетсвую тебя"
                                    Screens.Main.route -> "Главная"
                                    Screens.MathRails.route -> "Математическая игра"
                                    Screens.ExpressChallenge.route -> "Быстрые задачки"
                                    Screens.GeometryStation.route -> "Фигурки и формы"
                                    Screens.Game.route -> "Задачки"
                                    Screens.Learning.route -> "Обучайка"
                                    Screens.LearnAddition.route -> "Сложение"
                                    Screens.LearnSubtraction.route -> "Вычитание"
                                    Screens.LearnMultiplication.route -> "Умножение"
                                    Screens.LearnDivision.route -> "Деление"
                                    Screens.LearnShapes.route -> "Фигуры"
                                    Screens.Achievement.route -> "Достижение и награды"
                                    Screens.Statistics.route -> "Статистика"
                                    Screens.Ai.route -> "Бот математики"
                                    Screens.Settings.route -> "Настройки"

                                    else -> {

                                        "Загрузка..."

                                    }
                                },
                                fontFamily = FontFamily(Font(R.raw.kids)),
                                fontSize = 25.sp,

                                )
                        },

                        actions = {
                            when (currentRoute) {
                                Screens.Main.route -> {
                                    IconButton(
                                        onClick = {
                                            navigation.navigate(Screens.Settings.route)
                                        }
                                    ) {
                                        Icon(Icons.Filled.Settings, null)
                                    }
                                }

                                else -> {}
                            }

                        },
                    )
                },

                floatingActionButton = {
                    when (currentRoute) {
                        Screens.Main.route -> {
                            FloatingActionButton(
                                onClick = {
                                    navigation.navigate(Screens.Ai.route)
                                },
                                containerColor = Color(0xF2FF3535)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.bot_q7rhaqsodqit),
                                    null,
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    }
                }
            ) { padding ->
                NavHostControll(
                    navigation,
                    padding,
                    mathViewModel,
                    expressViewModel,
                    shapeViewModel,
                    aiBotViewModel,
                )
            }
        }
    }
}
