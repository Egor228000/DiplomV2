package com.example.diplomv2.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.diplomv2.view.AiBotViewModel
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.DefaultMarkdownTypography


@Composable
fun AiScreen(navController: NavHostController, aiBotViewModel: AiBotViewModel) {
    val listMessages by aiBotViewModel.listMessages.collectAsState()
    val isLoading by aiBotViewModel.isLoading.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    LaunchedEffect(listMessages.size) {
        if (listMessages.isNotEmpty()) {
            listState.animateScrollToItem(listMessages.lastIndex)
        }
    }


    Column(modifier = Modifier
        .fillMaxSize()
    ){
        LazyColumn(
            modifier = Modifier
                .weight(1f)

                .fillMaxWidth(),
            state = listState,
        ) {
            items(listMessages) { msg ->
                val isUser = msg.role == "Вы"

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isUser) Color(0xFFD1E8FF) else Color(0xFFF0F0F0)
                        ),
                        modifier = Modifier
                            .widthIn(max = 300.dp)
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {

                            Markdown(
                                content = msg.message,
                                colors = DefaultMarkdownColors(
                                    text = Color(0xFF212121),
                                    codeText = Color(0xFFd32f2f),
                                    inlineCodeText = Color(0xFF388E3C),
                                    linkText = Color(0xFF1976D2),
                                    codeBackground = Color(0xFFF5F5F5),
                                    inlineCodeBackground = Color(0xFFE0F2F1),
                                    dividerColor = Color(0xFFBDBDBD),
                                    tableText = Color(0xFF212121),
                                    tableBackground = Color(0xFFF0F0F0)
                                ),
                                typography = DefaultMarkdownTypography(
                                    h1 = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 30.sp,
                                        color = Color(0xFF212121)
                                    ),
                                    h2 = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 26.sp,
                                        color = Color(0xFF212121)
                                    ),
                                    h3 = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp,
                                        color = Color(0xFF212121)
                                    ),
                                    h4 = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Color(0xFF212121)
                                    ),
                                    h5 = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xFF212121)
                                    ),
                                    h6 = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color(0xFF212121)
                                    ),
                                    text = TextStyle(fontSize = 16.sp, color = Color(0xFF212121)),
                                    code = TextStyle(fontSize = 14.sp, color = Color(0xFF388E3C)),
                                    inlineCode = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color(0xFF388E3C)
                                    ),
                                    quote = TextStyle(
                                        fontSize = 16.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = Color(0xFF616161)
                                    ),
                                    paragraph = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color(0xFF212121)
                                    ),
                                    ordered = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color(0xFF212121)
                                    ),
                                    bullet = TextStyle(fontSize = 16.sp, color = Color(0xFF212121)),
                                    list = TextStyle(fontSize = 16.sp, color = Color(0xFF212121)),
                                    link = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color(0xFF1976D2),
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    textLink = TextLinkStyles(
                                        style = SpanStyle(
                                            fontSize = 16.sp,
                                            color = Color(0xFF1976D2)
                                        ),
                                        focusedStyle = SpanStyle(),
                                        hoveredStyle = SpanStyle(),
                                        pressedStyle = SpanStyle(),
                                    ),
                                    table = TextStyle(fontSize = 14.sp, color = Color(0xFF212121))
                                )
                            )
                        }
                    }
                }
            }
        }

        TextField(
            modifier = Modifier.fillMaxWidth(1f),
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = { Text("Введите сообщение") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            aiBotViewModel.sendText(inputText)
                            inputText = ""
                        }
                    },
                    enabled = !isLoading,

                ) {
                    if (isLoading) CircularProgressIndicator(Modifier.size(24.dp))
                    else Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (inputText.isNotBlank()) {
                        aiBotViewModel.sendText(inputText)
                        inputText = ""
                    }
                }
            ),

        )

    }
}



