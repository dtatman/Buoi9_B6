package com.example.buoi9_b6

data class Message(val role: String, val content: String)
data class ChatRequest(val messages: List<Message>, val model: String, val stream: Boolean, val temperature: Int)
data class ChatResponse(val choices: List<Choice>)
data class Choice(val message: Message)
