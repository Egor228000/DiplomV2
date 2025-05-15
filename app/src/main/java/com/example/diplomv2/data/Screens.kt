package com.example.diplomv2.data

enum class Screens(val route: String) {
    Welcome("welcom"),
    Main("main"),
    //Достижение
    Achievement("achievement"),
    Settings("settings"),
    //Игра 1
    MathRails("math_rails_screen"),
    Game("game"),
    // Игра 2
    ExpressChallenge("express_challenge"),
    // Игра 3
    GeometryStation("geometry_station"),
    // Статистика
    Statistics("statistics"),
    // Обучение
    Learning("learning"),

    // Экраны обучения
    LearnAddition("learn_addition"),
    LearnSubtraction("learn_subtraction"),
    LearnMultiplication("learn_multiplication"),
    LearnDivision("learn_division"),
    LearnShapes("learn_shapes"),


    // ИИ
    Ai("ai")
}
