package com.example.diplomv2

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
            val aiBotViewModel = AiBotViewModel()
            val navigation = rememberNavController()
            val navBackStackEntry by navigation.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {

                            when(currentRoute) {
                                Screens.Main.route -> {

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
                                    Screens.Regin.route -> "Регистрация"
                                    Screens.Login.route -> "Авторизация"
                                    Screens.Main.route -> "Главная"
                                    Screens.MathRails.route -> "Математические рельсы"
                                    Screens.ExpressChallenge.route -> "Скоройстой экспресс"
                                    Screens.GeometryStation.route -> "Геометрическая станция"
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

                                    else -> {
                                        "Настройки"
                                    }
                                },
                                fontFamily = FontFamily(Font(R.raw.kids)),
                                fontSize = 25.sp,

                                )
                        },

                        actions = {
                            when(currentRoute) {
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

                        }

                    )
                },
                
                floatingActionButton = {
                    when(currentRoute) {
                        Screens.Main.route -> {
                            FloatingActionButton(
                                onClick = {
                                    navigation.navigate(Screens.Ai.route)
                                }
                            ) {
                                Icon(Icons.Default.MailOutline, null)
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
                    aiBotViewModel
                )
            }
        }
    }
}
