package com.example.diplomv2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.diplomv2.R
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

data class Achievement(
    val title: String,
    val iconResId: Int // Используй в будущем реальные ресурсы
)

@Composable
fun AchievementScreen(
    navigation: NavHostController
) {
    val achievements = remember { mutableListOf(
        Achievement("🎯 Первый выстрел! – правильно реши 1 задачу", R.drawable.target_eb5ri3p9c064),
        Achievement("🏁 Старт дан! – пройди первый уровень", R.drawable.finish_5j328p4dk4bw),
        Achievement("⚡ Молниеносный! – сыграй в экспресс игру", R.drawable.speed_32ztm5vueg9y),
        Achievement("🔵 Глазастик! – угадай 3 фигуры подряд", R.drawable.vision_5ku362ma38cf),
        Achievement("💡 Умник! – 10 правильных ответов подряд", R.drawable.nerd_13ehf25vcxhb),
        Achievement("🚀 Ракета знаний! – завершено 5 уровней", R.drawable.speed_akdd6wxxs6jx),
        Achievement("🌈 Цветной чемпион – собери все виды фигур", R.drawable.rainbow_9c6c7aqz0bof),
        Achievement("🎓 Маленький профессор – набери 100 очков", R.drawable.professor_edq6f8z0qt61)
    ) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
            .padding(16.dp)
    ) {
        Text(
            text = "🏆 Достижения",
            fontSize = 26.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(achievements) { achievement ->
                AchievementItem(achievement)
            }
        }
    }
}

@Composable
fun AchievementItem(achievement: Achievement) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(160.dp)
            .background(Color(0xFFF3F3F3))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = achievement.iconResId),
            contentDescription = achievement.title,
            modifier = Modifier
                .size(64.dp),
        )
        Text(
            text = achievement.title,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}