package com.sweetsour.disinfectantspinner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn: TextView = findViewById(R.id.textView)

        startBtn.setOnClickListener{
            startBtnClick()
        }
    }

    private fun startBtnClick(){
        val launchSpinActivityIntent = Intent(this, SpinActivity::class.java)
        startActivity(launchSpinActivityIntent)
    }

}