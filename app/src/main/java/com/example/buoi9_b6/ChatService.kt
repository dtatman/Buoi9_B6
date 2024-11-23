package com.example.buoi9_b6
import okhttp3.* // OkHttp library for HTTP requests
import com.google.gson.Gson // Gson library for JSON serialization/deserialization
import java.io.IOException // Exception handling for HTTP operations
import okhttp3.MediaType.Companion.toMediaType // Import extension function for MediaType
import okhttp3.RequestBody.Companion.toRequestBody // Import extension function for RequestBody

class ChatService {

    private val client = OkHttpClient()
    private val gson = Gson()
    private val apiKey = "xai-xUsQEiVsn3IiQNNkLCtHP1MgQ1hVOwXZTQ0qtzK6tiiv0SDottXhB1I0u4P1llYc1C4tW5XghYMKQM6H"
    private val apiUrl = "https://api.x.ai/v1/chat/completions"

    fun sendMessage(messages: List<Message>, callback: (String?) -> Unit) {
        // Tạo body từ JSON
        val requestBody = gson.toJson(
            ChatRequest(messages, "grok-beta", stream = false, temperature = 0)
        ).toRequestBody("application/json".toMediaType())

        // Tạo request với header và body
        val request = Request.Builder()
            .url(apiUrl)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody)
            .build()

        // Gửi request và xử lý phản hồi
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null) // Gọi lại với giá trị null khi xảy ra lỗi
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val chatResponse = gson.fromJson(responseBody, ChatResponse::class.java)
                    val reply = chatResponse.choices.firstOrNull()?.message?.content
                    callback(reply) // Gọi lại với câu trả lời từ API
                } else {
                    callback(null) // Gọi lại với giá trị null nếu phản hồi không thành công
                }
            }
        })
    }
}

