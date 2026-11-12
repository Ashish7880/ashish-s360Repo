package com.ashish.voicemute

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(28, 36, 28, 36)
        }
        val title = TextView(this).apply {
            text = "Voice Mute Controller"
            textSize = 24f
        }
        val info = TextView(this).apply {
            text = "This prototype listens for the wired headset hook button. When detected, it searches the foreground screen for a clearly labelled Mute/Unmute control and attempts to tap it.\n\nIt does not access or record microphone audio. Compatibility with ChatGPT Voice depends on Android delivering the headset key to the accessibility service and on the mute button being exposed to accessibility."
            textSize = 16f
            setPadding(0, 24, 0, 24)
        }
        val enable = Button(this).apply {
            text = "Open Accessibility Settings"
            setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        }
        val note = TextView(this).apply {
            text = "Enable: Downloaded apps / Installed services → Voice Mute Controller. You can disable it from the same screen."
            textSize = 14f
            setPadding(0, 18, 0, 0)
        }
        layout.addView(title)
        layout.addView(info)
        layout.addView(enable)
        layout.addView(note)
        setContentView(layout)
    }
}
