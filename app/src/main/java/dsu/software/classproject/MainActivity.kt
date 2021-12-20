package dsu.software.classproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Start Notification", "Start MainActivity")

        InitUser(applicationContext).start()
        InitVisited(applicationContext).start()
        InitBeacon(applicationContext).start()

        Thread.sleep(300)

        GetAllUser(applicationContext).start()
        GetAllVisited(applicationContext).start()
        GetAllBeacon(applicationContext).start()

        val setPref = getSharedPreferences("app_settings", MODE_PRIVATE)
        val userPref = getSharedPreferences("user_details", MODE_PRIVATE)
        if (userPref.contains("userId") && userPref.contains("userPw") && setPref.getBoolean("auto_login", false)) {
            val mainMenuActivityIntent = Intent(this, MainMenuActivity::class.java)
            startActivity(mainMenuActivityIntent)
        } else {
            val loginActivityIntent = Intent(this, LoginActivity::class.java)
            Log.d("Action Notification", "Init page logo button clicked.")
            startActivity(loginActivityIntent)
        }
    }
}

class InitUser(val context: Context) : Thread() {
    override fun run() {
        Log.d("Start Notification", "Start InitUser")
        val user = UserEntity("01011111111", "Pw01", "User01", "000101", "서울특별시")
        UserDatabase.getInstance(context)!!.getUserDao().insert(user)

        val user2 = UserEntity("01022222222", "Pw02", "User02", "000202", "부산광역시")
        UserDatabase.getInstance(context)!!.getUserDao().insert(user2)
    }
}

class InitVisited(val context: Context) : Thread() {
    override fun run() {
        Log.d("Start Notification", "Start InitVisited")
        val visited = VisitedEntity(0, "01011111111", "2021-01-01", 0, "00:00")
        VisitedDatabase.getInstance(context)!!.getVisitedDao().insert(visited)

        val visited2 = VisitedEntity(1, "01011111111", "2021-01-02", 1, "01:00")
        VisitedDatabase.getInstance(context)!!.getVisitedDao().insert(visited2)

        val visited3 = VisitedEntity(2, "01022222222", "2021-01-01", 2, "02:00")
        VisitedDatabase.getInstance(context)!!.getVisitedDao().insert(visited3)
    }
}

class InitBeacon(val context: Context) : Thread() {
    override fun run() {
        Log.d("Start Notification", "Start InitBeacon")
        val beacon = BeaconEntity(0, "장소 0")
        BeaconDatabase.getInstance(context)!!.getBeaconDao().insert(beacon)
        val beacon2 = BeaconEntity(1, "장소 1")
        BeaconDatabase.getInstance(context)!!.getBeaconDao().insert(beacon2)
        val beacon3 = BeaconEntity(2, "장소 2")
        BeaconDatabase.getInstance(context)!!.getBeaconDao().insert(beacon3)
    }
}

class GetAllUser(val context: Context) : Thread() {
    override fun run() {
        Log.d("Start Notification", "Start GetAllUser")

        val userItems = UserDatabase.getInstance(context)!!.getUserDao().getAllValues()
        Log.d("Value", "items: ${userItems}")
    }
}

class GetAllVisited(val context: Context) : Thread() {
    override fun run() {
        Log.d("Start Notification", "Start GetAllVisited")

        val visitedItems = VisitedDatabase.getInstance(context)!!.getVisitedDao().getAllValues()
        Log.d("Value", "items: ${visitedItems}")
    }
}

class GetAllBeacon(val context: Context) : Thread() {
    override fun run() {
        Log.d("Start Notification", "Start GetAllBeacon")

        val beaconItems = BeaconDatabase.getInstance(context)!!.getBeaconDao().getAllValues()
        Log.d("Value", "items: ${beaconItems}")
    }
}
