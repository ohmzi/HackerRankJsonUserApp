package com.ohmz.hackerrankjsonuserapp.openAI

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class ChatMessage(val role: String, val content: String)
data class ChatCompletionRequest(
    val model: String, val messages: List<ChatMessage>, val temperature: Double = 0.0
)

data class ChatChoice(val index: Int, val message: ChatMessage, val finish_reason: String)
data class ChatCompletionResponse(
    val id: String, val `object`: String, val created: Long, val choices: List<ChatChoice>
)

interface OpenAiApi {
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(@Body request: ChatCompletionRequest): Response<ChatCompletionResponse>
}

fun createOpenAiRetrofit(apiKey: String): Retrofit {
    val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder().addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json").build()
        chain.proceed(request)
    }.build()
    return Retrofit.Builder().baseUrl("https://api.openai.com/").client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

suspend fun getGenderForName(name: String, apiKey: String): String? {
    val retrofit = createOpenAiRetrofit(apiKey)
    val openAiApi = retrofit.create(OpenAiApi::class.java)
    val prompt =
        "Determine whether the following name is typically male or female: \"$name\". Respond with either 'male' or 'female' only."
    val messages = listOf(ChatMessage(role = "user", content = prompt))
    val request = ChatCompletionRequest(model = "gpt-3.5-turbo", messages = messages)
    return try {
        val response = openAiApi.getChatCompletion(request)
        if (response.isSuccessful) {
            val answer =
                response.body()?.choices?.firstOrNull()?.message?.content?.trim()?.toLowerCase()
            when {
                answer?.contains("female") == true -> "female"
                answer?.contains("male") == true -> "male"
                else -> null
            }
        } else null
    } catch (e: Exception) {
        null
    }
}
