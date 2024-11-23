package com.example.buoi9_b6
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val chatService = ChatService()
    private val messages = mutableListOf<Message>()
    private lateinit var networkReceiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chatLayout = findViewById<LinearLayout>(R.id.chatLayout)
        val inputMessage = findViewById<EditText>(R.id.inputMessage)
        val sendButton = findViewById<Button>(R.id.sendButton)
        val chatScrollView = findViewById<ScrollView>(R.id.chatScrollView)
        networkReceiver = NetworkReceiver()
        sendButton.setOnClickListener {
            val userMessage = inputMessage.text.toString()
            if (userMessage.isNotBlank()) {
                addMessageToChat(chatLayout, "User", userMessage)
                inputMessage.text.clear()

                messages.add(Message("user", userMessage))
                chatService.sendMessage(messages) { reply ->
                    runOnUiThread {
                        if (reply != null) {
                            addMessageToChat(chatLayout, "Assistant", reply)
                            messages.add(Message("assistant", reply))
                        } else {
                            addMessageToChat(chatLayout, "System", "Error: Unable to fetch response.")
                        }
                        chatScrollView.post { chatScrollView.fullScroll(ScrollView.FOCUS_DOWN) }
                    }
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkReceiver)
    }
    private fun addMessageToChat(layout: LinearLayout, sender: String, message: String) {
        val textView = TextView(this)
        textView.text = "$sender: $message"
        if (sender == "Assistant") {
            textView.setBackgroundColor(Color.DKGRAY)
            textView.setTextColor(Color.WHITE)
        }
        else {
            textView.setBackgroundColor(Color.LTGRAY)
            textView.setTextColor(Color.BLACK)
        }
        layout.addView(textView)
    }
}
