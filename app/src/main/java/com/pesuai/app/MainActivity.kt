package com.pesuai.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject

class MainActivity : Activity() {

    private val bg = Color.rgb(248, 247, 252)

    private fun base() =
    LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(28, 64, 28, 24)
        setBackgroundColor(bg)
    }

    private fun title(s: String) = TextView(this).apply {
        text = s
        textSize = 28f
        setTypeface(null, 1)
    }

    private fun btn(s: String, action: () -> Unit) =
    TextView(this).apply {
        text = s
        textSize = 18f
        setTextColor(Color.rgb(30, 30, 30))
        gravity = Gravity.CENTER_VERTICAL
        setPadding(20, 0, 20, 0)
        setBackgroundColor(Color.rgb(225, 220, 240))
        setOnClickListener { action() }
    }

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        home()
    }

    private fun home() {
        val l = base()

        l.addView(title("வணக்கம்! 👋"))

        l.addView(
            TextView(this).apply {
                text = "PesuAI • Learn any language through Tamil"
                textSize = 16f
            }
        )

        l.addView(
            TextView(this).apply {
                text = "🔥 0 Day Streak ⭐ Beginner"
                textSize = 16f
                setPadding(0, 24, 0, 12)
            }
        )

        listOf(
            "📚 Continue Learning",
            "⌨️ Type with AI",
            "🎤 Speak with AI",
            "🌍 Choose Language",
            "📊 My Progress"
        ).forEach { s ->

            val button = btn(s) {
                if (s == "⌨️ Type with AI") {
                    typing()
                } else {
                    toast("இந்த module அடுத்த build-ல்.")
                }
            }

            l.addView(
                button,
                LinearLayout.LayoutParams(-1, 60).apply {
                    setMargins(0, 8, 0, 0)
                }
            )
        }

        setContentView(l)
    }

    private fun typing() {

        val l = base()

        l.addView(title("⌨️ Type with AI"))

        l.addView(
            TextView(this).apply {
                text = "Tamil → English • AI Grammar Coach"
                textSize = 17f
                setPadding(0, 8, 0, 16)
            }
        )

        val input = EditText(this).apply {
            hint = "Example: I going market yesterday"
            minLines = 4
            gravity = Gravity.TOP
            textSize = 17f
        }

        l.addView(
            input,
            LinearLayout.LayoutParams(-1, 160)
        )

        val result = TextView(this).apply {
            textSize = 17f
            setPadding(0, 20, 0, 10)
        }

        l.addView(result)

            lateinit var go: textview

              go = btn("✨ Correct with AI") {

            val text = input.text.toString().trim()

            if(text.isEmpty()){
    toast("Sentence type செய்யுங்கள்")
    return@btn
            }

            go.isEnabled = false
            result.text = "AI thinking…"

            Thread {

                try {

                    val c = URL(
                        "http://10.0.2.2:3000/correct"
                    ).openConnection() as HttpURLConnection

                    c.requestMethod = "POST"
                    c.doOutput = true
                    c.setRequestProperty(
                        "Content-Type",
                        "application/json"
                    )

                    c.outputStream.use {
                        it.write(
                            JSONObject()
                                .put("text", text)
                                .put("from", "Tamil")
                                .put("to", "English")
                                .toString()
                                .toByteArray()
                        )
                    }

                    val body =
                        c.inputStream.bufferedReader().readText()

                    val answer =
                        JSONObject(body).optString("answer")

                    runOnUiThread {
                        result.text = answer
                        go.isEnabled = true
                    }

                } catch (e: Exception) {

                    runOnUiThread {
                        result.text =
                            "Backend connect ஆகவில்லை.\n\n${e.message}"
                        go.isEnabled = true
                    }
                }
            }.start()
        }

        l.addView(
            go,
            LinearLayout.LayoutParams(-1, 60).apply {
                setMargins(0, 10, 0, 0)
            }
        )

        l.addView(
            btn("← Back") {
                home()
            },
            LinearLayout.LayoutParams(-1, 60).apply {
                setMargins(0, 8, 0, 0)
            }
        )

        setContentView(l)
    }

    private fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}
