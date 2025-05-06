package com.example.diplomv2.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(onClick: () -> Unit, textButton: String, modifier: Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20),
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFFC42020)),
    ) {
        Text(
            textButton,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White
            )
        )
    }
}

@Composable
fun CustomText(textText: String, modifier: Modifier, textStyle: TextStyle) {
    Text(
        text = textText,
        style = textStyle,
        modifier = modifier,

    )
}


@Composable
fun CustomOutlinedTextField(
    valueText: String,
    onValueChangeText: (String) -> Unit,
    label: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = valueText,
        onValueChange = onValueChangeText,
        label = label,
        modifier = Modifier.fillMaxWidth(1f),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.Red, focusedLabelColor = Color.Black)
    )
}
