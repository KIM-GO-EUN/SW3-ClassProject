package dsu.software.classproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

var loginStatus: Int = 0

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d("Start Notification", "Start LoginActivity")

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registerBtn = findViewById<Button>(R.id.registerBtn)
        val userPref = getSharedPreferences("user_details", MODE_PRIVATE)
        val setPref = getSharedPreferences("app_settings", MODE_PRIVATE)

        val mainMenuActivityIntent = Intent(this, MainMenuActivity::class.java)

        loginBtn.setOnClickListener {
            val userId = findViewById<EditText>(R.id.userId).text.toString()
            val userPw = findViewById<EditText>(R.id.userPw).text.toString()

            val autoLoginStatus = findViewById<CheckBox>(R.id.autoLoginCheckBox).isChecked
            val setEditor = setPref.edit()
            if (autoLoginStatus) {
                setEditor.putBoolean("auto_login", true)
                setEditor.apply()
            } else {
                setEditor.putBoolean("auto_login", false)
                setEditor.apply()
            }

            Log.d("Value", "userId: ${userId} userPw: ${userPw}")
            Log.d("Action Notification", "Login button clicked.")
            GetAccount(applicationContext, userId, userPw).start()
            Thread.sleep(300)
            if (loginStatus == 1) {
                val userEditor = userPref.edit()
                userEditor.putString("userId", userId)
                userEditor.putString("userPw", userPw)
                userEditor.apply()
                startActivity(mainMenuActivityIntent)
            } else {
                Toast.makeText(this, "로그인 실패! 전화번호 혹은 비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show()
            }
        }

        registerBtn.setOnClickListener {
            Log.d("Action Notification", "Register button clicked.")
            val registerActivityIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerActivityIntent)
        }
    }
}

class GetAccount(val context: Context, val userId: String, val userPw: String) : Thread() {
    override fun run() {
        Log.d("Start Notification", "Start GetAccount")

        val correctPw = UserDatabase.getInstance(context)!!.getUserDao().loginQuerying(userId)
        Log.d("Value", "userId: ${userId} userPw: ${userPw} correctPw: ${correctPw}")
        loginStatus = if (userPw == correctPw) {
            Log.d("Action Notification", "Login success!")
            1
        } else {
            Log.d("Action Notification", "Login failed!")
            0
        }
    }
}
