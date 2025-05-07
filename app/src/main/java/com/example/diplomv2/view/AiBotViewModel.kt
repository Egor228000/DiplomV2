package com.example.diplomv2.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

data class ChatBubble(val role: String, var message: String)
@Serializable
data class ChatMessage(val role: String, val content: String)

@Serializable
data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val temperature: Double,
    val max_tokens: Int
)

@Serializable
data class ChatResponse(
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val message: Message
)

@Serializable
data class Message(
    val role: String,
    val content: String
)
class AiBotViewModel : ViewModel() {

    private val _listMessages = MutableStateFlow<List<ChatBubble>>(emptyList())
    val listMessages: StateFlow<List<ChatBubble>> = _listMessages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val json = Json { ignoreUnknownKeys = true }

    fun sendText(inputText: String) {
        _isLoading.value = true

        val userMessage = ChatBubble("Вы", inputText)
        _listMessages.value = _listMessages.value + userMessage

        val currentText = inputText

        viewModelScope.launch {
            val botResponse = getFullChatResponse(currentText)
            val botMessage = ChatBubble("Бот", botResponse)

            _listMessages.value = _listMessages.value + botMessage

            _isLoading.value = false
        }
    }

    private suspend fun getFullChatResponse(text: String): String {
        val client = HttpClient(CIO) {
            engine {
                requestTimeout = 80000
                endpoint {
                    connectTimeout = 80000
                    socketTimeout = 80000
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }

        val response: HttpResponse = client.request("http://00000000000/v1/chat/completions") {
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            setBody(
                ChatRequest(
                    model = "gemma-3-4b-it",
                    messages = listOf(ChatMessage("user", text)),
                    temperature = 0.7,
                    max_tokens = 1000
                )
            )
        }

        val responseBody = response.bodyAsText()
        val chatResponse = json.decodeFromString<ChatResponse>(responseBody)

        return chatResponse.choices.firstOrNull()?.message?.content ?: "Ответ не получен"
    }
}

