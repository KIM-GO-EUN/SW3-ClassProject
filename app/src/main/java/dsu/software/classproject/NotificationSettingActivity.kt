package dsu.software.classproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class NotificationSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_setting)

        val setPref = getSharedPreferences("app_settings", MODE_PRIVATE)
        val setEditor = setPref.edit()

        val voiceNotificationSwitch = findViewById<SwitchCompat>(R.id.voiceNotificationSwitch)
        voiceNotificationSwitch.isChecked = setPref.getBoolean("voice_notification", false)
        val soundNotificationSwitch = findViewById<SwitchCompat>(R.id.soundNotificationSwitch)
        soundNotificationSwitch.isChecked = setPref.getBoolean("sound_notification", false)
        val vibrationNotificationSwitch = findViewById<SwitchCompat>(R.id.vibrationNotificationSwitch)
        vibrationNotificationSwitch.isChecked = setPref.getBoolean("vibration_notification", false)
        val pushNotificationSwitch = findViewById<SwitchCompat>(R.id.pushNotificationSwitch)
        pushNotificationSwitch.isChecked = setPref.getBoolean("push_notification", false)

        val confirmButton = findViewById<Button>(R.id.confirmButton)

        confirmButton.setOnClickListener {
            setEditor.putBoolean("voice_notification", voiceNotificationSwitch.isChecked)
            setEditor.putBoolean("sound_notification", soundNotificationSwitch.isChecked)
            setEditor.putBoolean("vibration_notification", vibrationNotificationSwitch.isChecked)
            setEditor.putBoolean("push_notification", pushNotificationSwitch.isChecked)
            setEditor.apply()
            val mainMenuActivityIntent = Intent(this, MainMenuActivity::class.java)
            startActivity(mainMenuActivityIntent)
        }
    }
}