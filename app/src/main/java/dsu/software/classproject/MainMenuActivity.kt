package dsu.software.classproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        Log.d("Start Notification", "Start MainMenuActivity")

        val mainMenuLogoButton = findViewById<ImageButton>(R.id.mainMenuLogoButton)
        mainMenuLogoButton.setOnClickListener {
            Log.d("Action Notification", "Main menu logo button clicked.")
            val userPref = getSharedPreferences("user_details", MODE_PRIVATE)
            AddVisited(
                applicationContext,
                3,
                userPref.getString("userId", String()).toString(),
                "2021-01-02",
                2,
                "03:00"
            ).start()
            Log.d(
                "Action Notification",
                "Add Visited for user: ${userPref.getString("userId", String()).toString()}"
            )
            Thread.sleep(300)
            GetAllVisited(applicationContext).start()
        }

        val informationInquiryActivityIntent = Intent(this, InformationInquiryActivity::class.java)
        val informationInquiryButton = findViewById<Button>(R.id.informationInquiryButton)
        informationInquiryButton.setOnClickListener {
            Log.d("Action Notification", "Information inquiry button clicked.")
            startActivity(informationInquiryActivityIntent)
        }

        val notificationSettingIntent = Intent(this, NotificationSettingActivity::class.java)
        val notificationSettingButton = findViewById<Button>(R.id.notificationSettingButton)
        notificationSettingButton.setOnClickListener {
            Log.d("Action Notification", "Notification setting button clicked.")
            startActivity(notificationSettingIntent)
        }

        val myInformationIntent = Intent(this, MyInformationActivity::class.java)
        val myInformationButton = findViewById<Button>(R.id.myInformationButton)
        myInformationButton.setOnClickListener {
            Log.d("Action Notification", "My information button clicked.")
            startActivity(myInformationIntent)
        }

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            val pref = getSharedPreferences("user_details", MODE_PRIVATE)
            Log.d("Action Notification", "Logout button clicked.")
            val editor = pref.edit()
            editor.clear()
            editor.apply()

            val loginActivityIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginActivityIntent)
        }
    }
}

class AddVisited(
    val context: Context,
    val idx: Int,
    val visitedUser: String,
    val visitedDate: String,
    val visitedLoc: Int,
    val visitedTime: String
) : Thread() {
    override fun run() {
        Log.d("Start Notification", "Start AddVisited")
        val visited = VisitedEntity(idx, visitedUser, visitedDate, visitedLoc, visitedTime)
        VisitedDatabase.getInstance(context)!!.getVisitedDao().insert(visited)
    }
}
