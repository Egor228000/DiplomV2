package com.example.diplomv2.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.diplomv2.R
import com.example.diplomv2.data.Screens
import com.example.diplomv2.ui.theme.CustomButton
import com.example.diplomv2.ui.theme.CustomText
import kotlinx.coroutines.launch

@Composable
fun WelcomScreen(
    navigation: NavHostController,
    paddingValues: PaddingValues,
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(1f)
    ) {

        HorizontalPager(state = pagerState) { page ->


            when (page) {
                0 -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CustomText(
                                "Привет, маленький математик!\n" +
                                        "Давай вместе изучать цифры и решать весёлые задачки!",
                                modifier = Modifier,
                                textStyle = TextStyle(
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                )
                            )
                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(
                                    R.raw.hello
                                )
                            )
                            LottieAnimation(
                                composition,
                                modifier = Modifier.fillMaxWidth(1f),
                                iterations = LottieConstants.IterateForever
                            )
                        }
                        CustomButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage + 1,
                                        animationSpec = tween(300)
                                    )
                                }
                            },
                            textButton = "Далее",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                1 -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CustomText(
                                "Весёлые задания на время!\n" +
                                        "Собирай звёздочки за правильные ответы и становись чемпионом по математике!",
                                modifier = Modifier,
                                textStyle = TextStyle(
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp
                                )
                            )
                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(
                                    R.raw.time
                                )
                            )
                            LottieAnimation(
                                composition,
                                modifier = Modifier.fillMaxWidth(1f),
                                iterations = LottieConstants.IterateForever
                            )
                        }
                        CustomButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage + 1,
                                        animationSpec = tween(300)
                                    )
                                }
                            },
                            textButton = "Далее",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                2 -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CustomText(
                                "Шаг за шагом к успеху!\n" +
                                        "Каждое правильно решённое задание - это новый шаг в мир математики!",
                                modifier = Modifier,
                                textStyle = TextStyle(
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(
                                    R.raw.toptop
                                )
                            )
                            LottieAnimation(
                                composition = composition,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                iterations = LottieConstants.IterateForever
                            )
                        }

                        CustomButton(
                            onClick = { navigation.navigate(Screens.Regin.route) },
                            textButton = "Начать учиться",
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(16.dp)
                        )

                    }
                }
            }
        }
    }
}