package dsu.software.classproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

var duplicateIdStatus = 0

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Log.d("Start Notification", "Start RegisterActivity")

        var userHome = ""

        val spinner: Spinner = findViewById(R.id.selectRegionSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.region,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    userHome = spinner.selectedItem.toString()
                }
            }

        val registerBtn = findViewById<Button>(R.id.registerBtn)
        registerBtn.setOnClickListener {
            Log.d("Action Notification", "Register button clicked.")

            val userName = findViewById<EditText>(R.id.userName)
            val userBirthday = findViewById<EditText>(R.id.userBirthday)
            val userId = findViewById<EditText>(R.id.userId)
            val userPw = findViewById<EditText>(R.id.userPw)

            val termsOfServiceCheckBox = findViewById<CheckBox>(R.id.termsOfServiceCheckBox)
            val privacyStatementCheckBox = findViewById<CheckBox>(R.id.privacyStatementCheckBox)
            val pushNotificationCheckBox = findViewById<CheckBox>(R.id.pushNotificationCheckBox)

            val userPref = getSharedPreferences("user_details", MODE_PRIVATE)
            val userEditor = userPref.edit()
            val setPref = getSharedPreferences("app_settings", MODE_PRIVATE)
            val setEditor = setPref.edit()
            val mainMenuActivityIntent = Intent(this, MainMenuActivity::class.java)

            if (userName.text.toString().isEmpty()) {
                Toast.makeText(this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show()
                userName.requestFocus()
                return@setOnClickListener
            }
            if (userBirthday.text.toString().isEmpty()) {
                Toast.makeText(this, "주민등록번호 앞자리를 입력하세요.", Toast.LENGTH_SHORT).show()
                userBirthday.requestFocus()
                return@setOnClickListener
            }
            if (userId.text.toString().isEmpty()) {
                Toast.makeText(this, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                userId.requestFocus()
                return@setOnClickListener
            }
            if (userPw.text.toString().isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                userPw.requestFocus()
                return@setOnClickListener
            }
            if (!termsOfServiceCheckBox.isChecked) {
                Toast.makeText(this, "이용 약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!privacyStatementCheckBox.isChecked) {
                Toast.makeText(this, "개인정보 취급방침에 동의해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AddUser(
                applicationContext,
                userName.text.toString(),
                userBirthday.text.toString(),
                userHome,
                userId.text.toString(),
                userPw.text.toString()
            ).start()

            Thread.sleep(300)
            if (duplicateIdStatus == 1) {
                Toast.makeText(this, "아이디(전화번호)가 중복됩니다.", Toast.LENGTH_SHORT).show()
            } else {
                setEditor.putBoolean("push_notification", pushNotificationCheckBox.isChecked)
                if (pushNotificationCheckBox.isChecked) {
                    Toast.makeText(this, "푸시 알림에 동의하셨습니다.", Toast.LENGTH_SHORT).show()
                }
                setEditor.putBoolean("auto_login", true)
                setEditor.apply()
                userEditor.putString("userId", userId.text.toString())
                userEditor.putString("userPw", userPw.text.toString())
                userEditor.apply()
                startActivity(mainMenuActivityIntent)
            }
        }
    }
}

class AddUser(
    val context: Context,
    val userName: String,
    val userBirthday: String,
    val userHome: String,
    val userId: String,
    val userPw: String
) : Thread() {
    override fun run() {
        val alreadyRegisteredIdList =
            UserDatabase.getInstance(context)!!.getUserDao().getAllValues()
        for (alreadyRegisteredId in alreadyRegisteredIdList) {
            if (alreadyRegisteredId.userId == userId) {
                duplicateIdStatus = 1
                Log.d("Value", "ID duplicated.")
                return
            }
        }
        val user = UserEntity(userId, userPw, userName, userBirthday, userHome)
        UserDatabase.getInstance(context)!!.getUserDao().insert(user)
        duplicateIdStatus = 0
        Log.d("Value", "ID doesn't duplicate.")
    }
}