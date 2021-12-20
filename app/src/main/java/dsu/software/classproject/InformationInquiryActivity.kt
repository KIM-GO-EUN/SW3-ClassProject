package dsu.software.classproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class InformationInquiryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_inquiry)
        Log.d("Start Notification", "Start InformationInquiryActivity")

        val mainMenuActivityIntent = Intent(this, MainMenuActivity::class.java)

        val tableLayout1 = findViewById<TableLayout>(R.id.informationInquiryTable)

        val userPref = getSharedPreferences("user_details", MODE_PRIVATE)
        GetVisited(
            applicationContext,
            userPref.getString("userId", String()).toString(),
            tableLayout1
        ).start()

        val confirmButton = findViewById<Button>(R.id.informationInquiryConfirmButton)
        confirmButton.setOnClickListener {
            Log.d("Action Notification", "Information inquiry button clicked.")
            startActivity(mainMenuActivityIntent)
        }
    }
}

class GetVisited(val context: Context, val visitedUser: String, val tableLayout1: TableLayout) :
    Thread() {
    override fun run() {
        Log.d("Start Notification", "Start GetVisited")

        val items = VisitedDatabase.getInstance(context)!!.getVisitedDao().getValues(visitedUser)
        Log.d("Value", "items.size: ${items.size}")

        for (row_count in 1 until items.size + 1) {
            val row = tableLayout1.getChildAt(row_count) as TableRow
            val item = items[row_count - 1]
            Log.d("Value", "item: ${item}")
            val textViewDate = row.getChildAt(0) as TextView
            textViewDate.text = item.visitedDate

            val textViewLoc = row.getChildAt(1) as TextView
            val beacon =
                BeaconDatabase.getInstance(context)!!.getBeaconDao().getValues(item.visitedLoc)
            Log.d("Value", "beacon: ${beacon}")
            textViewLoc.text = beacon[0].location

            val textViewTime = row.getChildAt(2) as TextView
            textViewTime.text = item.visitedTime
        }
    }
}
